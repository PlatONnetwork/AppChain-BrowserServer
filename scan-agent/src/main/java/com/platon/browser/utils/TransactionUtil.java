package com.platon.browser.utils;

import com.alibaba.fastjson.JSON;
import com.platon.bech32.Bech32;
import com.platon.browser.bean.*;
import com.platon.browser.cache.AddressCache;
import com.platon.browser.cache.PPosInvokeContractInputCache;
import com.platon.browser.client.PlatOnClient;
import com.platon.browser.client.SpecialApi;
import com.platon.browser.decoder.PPOSTxDecodeResult;
import com.platon.browser.decoder.PPOSTxDecodeUtil;
import com.platon.browser.elasticsearch.dto.Block;
import com.platon.browser.elasticsearch.dto.Transaction;
import com.platon.browser.enums.ContractDescEnum;
import com.platon.browser.enums.ContractTypeEnum;
import com.platon.browser.enums.InnerContractAddrEnum;
import com.platon.browser.exception.BeanCreateOrUpdateException;
import com.platon.browser.exception.BlankResponseException;
import com.platon.browser.exception.ContractInvokeException;
import com.platon.browser.param.DelegateExitParam;
import com.platon.browser.param.DelegateRewardClaimParam;
import com.platon.browser.param.TxParam;
import com.platon.contracts.ppos.dto.common.ErrorCode;
import com.platon.protocol.core.DefaultBlockParameter;
import com.platon.protocol.core.methods.response.Log;
import com.platon.protocol.core.methods.response.PlatonGetCode;
import com.platon.rlp.solidity.RlpDecoder;
import com.platon.rlp.solidity.RlpList;
import com.platon.rlp.solidity.RlpString;
import com.platon.rlp.solidity.RlpType;
import com.platon.utils.Numeric;
import org.slf4j.Logger;
import org.springframework.beans.BeanUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 虚拟交易工具
 */
public class TransactionUtil {

    /**
     * vrf内置合约
     */
    private static final String VRF_ADDRESS = "0x3000000000000000000000000000000000000001";

    /**
     * 根据合约内部调用PPOS的输入信息生成虚拟PPOS交易列表
     *
     * @param block               合约调用交易所在区块信息
     * @param parentTx            合约调用交易本身
     * @param pposTxes 合约内部调用PPOS操作的输入信息
     * @return
     */
    public static List<Transaction> getVirtualTxList(Block block, Transaction parentTx, List<PPOSTx> pposTxes) {
        List<Transaction> transactionList = new ArrayList<>();
        if (pposTxes.size() == 0) {
            return transactionList;
        }
        for (int i = 0; i < pposTxes.size(); i++) {
            // 如果是vrf内置合约则跳过
            PPOSTx tran = pposTxes.get(i);
            if (Bech32.addressDecodeHex(tran.getTo()).equalsIgnoreCase(VRF_ADDRESS)) {
                return transactionList;
            }
            PPOSTxDecodeResult result = PPOSTxDecodeUtil.decode(new String(tran.getInput()), Collections.emptyList());
            if (result.getTypeEnum() == null) {
                continue;
            }
            Transaction tx = new Transaction();
            BeanUtils.copyProperties(parentTx, tx);
            tx.setStatus(parentTx.getStatus());
            tx.setFrom(tran.getFrom());
            tx.setTo(tran.getTo());
            tx.setToType(Transaction.ToTypeEnum.INNER_CONTRACT.getCode());
            tx.setHash(parentTx.getHash() + "-" + i);
            tx.setType(result.getTypeEnum().getCode());
            tx.setIndex(i);
            tx.setInput(new String(tran.getInput()));
            tx.setInfo(result.getParam().toJSONString());
            tx.setSeq((long) i);
            transactionList.add(tx);

            if (Integer.parseInt(new String(tran.getResult())) > 0) {
                // 虚拟交易失败,交易状态码设置为失败
                tx.setStatus(Transaction.StatusEnum.FAILURE.getCode());
            }
        }
        return transactionList;
    }

    /**
     * 获得真实交易解除委托时所提取的委托收益
     */
    public static BigInteger getDelegateReward(List<Log> logs) {
        if (logs == null || logs.isEmpty()) {
            return BigInteger.ZERO;
        }
        return getDelegateReward(logs.get(0));
    }

    /**
     * 通用解委托奖励日志数据解析
     *
     * @param log
     * @return
     */
    private static BigInteger getDelegateReward(Log log) {
        if (log == null) {
            return BigInteger.ZERO;
        }
        String logData = log.getData();
        if (null == logData || "".equals(logData)) {
            return BigInteger.ZERO;
        }

        RlpList rlp = RlpDecoder.decode(Numeric.hexStringToByteArray(logData));
        List<RlpType> rlpList = ((RlpList) (rlp.getValues().get(0))).getValues();
        String decodedStatus = new String(((RlpString) rlpList.get(0)).getBytes());
        int statusCode = Integer.parseInt(decodedStatus);

        if (statusCode != ErrorCode.SUCCESS) {
            return BigInteger.ZERO;
        }

        return ((RlpString) (RlpDecoder.decode(((RlpString) rlpList.get(1)).getBytes())).getValues().get(0)).asPositiveBigInteger();
    }

    /**
     * 处理虚拟交易
     *
     * @param block
     * @param specialApi
     * @param platOnClient
     * @param contractInvokeTx
     * @param contractInvokeTxReceipt
     * @param logger
     * @return
     * @throws ContractInvokeException
     * @throws BlankResponseException
     */
    public static List<Transaction> processVirtualTx(Block block, SpecialApi specialApi, PlatOnClient platOnClient, CollectionTransaction contractInvokeTx, Receipt contractInvokeTxReceipt, Logger logger) throws ContractInvokeException, BlankResponseException , IOException {
        if (!PPosInvokeContractInputCache.hasCache(block.getNum())) {
            ImplicitPPOSTxResult pPosInvokeInfo = specialApi.getPPosInvokeInfo(platOnClient.getWeb3jWrapper(), BigInteger.valueOf(block.getNum()));
            ConcurrentHashMap<String, List<PPOSTx>> result = pPosInvokeInfo.getResult();

            result.forEach((key,value) ->{
                value.removeIf(pposTx -> pposTx.getResult().length >= 6);
                if (value.size() == 0){
                    result.remove(key);
                }
            });
            if (result.size() > 0){
                PPosInvokeContractInputCache.update(block.getNum(), result);
            }
        }

        // 取出当前普通合约调用交易内部调用PPOS操作的输入参数
        List<PPOSTx> pposTxes = PPosInvokeContractInputCache.getPPosInvokeContractInput(contractInvokeTx.getHash());
        // 使用普通合约内部调用的输入数据构造虚拟PPOS交易列表(包括成功和失败的PPOS调用)
        List<Transaction> virtualTxList = getVirtualTxList(block, contractInvokeTx, pposTxes);
        if (!virtualTxList.isEmpty()) {
            for (int i = 0; i < virtualTxList.size(); i++) {
                Transaction vt = virtualTxList.get(i);
                // 交易失败，跳过
                if (vt.getStatus() != Transaction.StatusEnum.SUCCESS.getCode()) {
                    continue;
                }
                /**
                 * 合约代理PPOS时，回执中的logs的内部结构： List- - 虚拟交易1的log - ... - 虚拟交易n的log - 合约调用的log
                 */
                Log log = contractInvokeTxReceipt.getLogs().get(i);
                if (vt.getTypeEnum() == Transaction.TypeEnum.DELEGATE_EXIT) {
                    // 解委托
                    BigInteger reward;
                    try {
                        reward = getDelegateReward(log);
                    } catch (Exception e) {
                        reward = BigInteger.ZERO;
                    }
                    // 对vt的info字段进行解析，添加reward字段后重新序列化后回填到vt中
                    DelegateExitParam delegateExitParam = vt.getTxParam(DelegateExitParam.class);
                    BigDecimal rewardAmount = new BigDecimal(reward);
                    delegateExitParam.setReward(rewardAmount);
                    vt.setInfo(delegateExitParam.toJSONString());
                }

                if (vt.getTypeEnum() == Transaction.TypeEnum.CLAIM_REWARDS) {
                    // 领取委托奖励
                    DelegateRewardClaimParam delegateRewardClaimParam = DelegateRewardClaimParam.builder().rewardList(new ArrayList<>()).build();
                    List<Log> logs = Arrays.asList(log);
                    PPOSTxDecodeResult result = PPOSTxDecodeUtil.decode(vt.getInput(), logs);
                    TxParam param = result.getParam();
                    if (param != null) {
                        delegateRewardClaimParam = (DelegateRewardClaimParam) param;
                    }
                    vt.setInfo(delegateRewardClaimParam.toJSONString());
                }
            }
        }
        // 把成功的虚拟交易过滤出来
        List<Transaction> successVirtualTransactions = new ArrayList<>();
        virtualTxList.forEach(vt -> {
            if (vt.getStatus() == Transaction.StatusEnum.SUCCESS.getCode()) {
                successVirtualTransactions.add(vt);
            }
        });
        return successVirtualTransactions;
    }

    private static Transaction.TypeEnum convertMethodIdToTxType(long methodId ){
        if(methodId == 3137535113L){
            return Transaction.TypeEnum.ROOT_CHAIN_STATE_SYNC;
        } else if(methodId == 1474851303L){
            return Transaction.TypeEnum.ROOT_CHAIN_BLOCK_NUMBER_SYNC;
        }else{
            return Transaction.TypeEnum.OTHERS;
        }
    }
    /**
     * 内置合约调用交易,解析补充信息
     */
    public static void resolveInnerContractInvokeTxComplementInfo(CollectionTransaction tx, Receipt receipt, ComplementInfo ci) throws BeanCreateOrUpdateException {
        if (InnerContractAddrEnum.NODE_CONTRACT.getAddress().equalsIgnoreCase(tx.getTo()) ){
            //内置质押合约，现在底层有两个方法，一个是StakeStateSync，一个是BlockNumber, 有input的前4个字节表示方法id（这个和PlatON的input构成不同，PlatON是需要对methodId再次rlp编码的）

            byte[] inputBytes = HexUtil.decode(tx.getInput());
            byte[] methodIdBytes = Arrays.copyOfRange(inputBytes, 0, 4);
            long methodId = HexUtil.convertToLong(methodIdBytes);

            //这里没有解析input中的method，因为底层StakeStateSync方法的method ID是：3137535113，是个long类型的。这里相当于对这个方法的ID做了转换
            ci.setType(convertMethodIdToTxType(methodId).getCode());
            ci.setInfo(JSON.toJSONString(receipt.getRootChainTxs()));
            ci.setToType(Transaction.ToTypeEnum.INNER_CONTRACT.getCode());
            ci.setContractType(ContractTypeEnum.INNER.getCode());
            ci.setMethod(null);
            ci.setBinCode(null);
            return;
        }


        PPOSTxDecodeResult decodedResult;
        try {
            // 解析交易的输入及交易回执log信息
            decodedResult = PPOSTxDecodeUtil.decode(tx.getInput(), receipt.getLogs());
            ci.setType(decodedResult.getTypeEnum().getCode());
            ci.setInfo(decodedResult.getParam().toJSONString());
            ci.setToType(Transaction.ToTypeEnum.INNER_CONTRACT.getCode());
            ci.setContractType(ContractTypeEnum.INNER.getCode());
            ci.setMethod(null);
            ci.setBinCode(null);
        } catch (Exception e) {
            throw new BeanCreateOrUpdateException("交易[hash:" + tx.getHash() + "]的参数解析出错:" + e.getMessage());
        }
    }

    /**
     * 获取合约的Bin代码
     *
     * @param platOnClient
     * @param contractAddress
     * @return
     * @throws BeanCreateOrUpdateException
     */
    public static String getContractBinCode(CollectionTransaction tx, PlatOnClient platOnClient, String contractAddress, Logger logger) throws BeanCreateOrUpdateException {
        try {
            PlatonGetCode platonGetCode = platOnClient.getWeb3jWrapper().getWeb3j().platonGetCode(contractAddress, DefaultBlockParameter.valueOf(BigInteger.valueOf(tx.getNum()))).send();
            return platonGetCode.getCode();
        } catch (Exception e) {
            platOnClient.updateCurrentWeb3jWrapper();
            String error = "获取合约代码出错[" + contractAddress + "]:" + e.getMessage();
            logger.error("{}", error);
            throw new BeanCreateOrUpdateException(error);
        }
    }

    /**
     * 创建普通合约,解析补充信息
     *
     * @param result
     * @param contractAddress
     * @param platOnClient
     * @param ci
     * @param log
     * @param contractTypeEnum
     * @return void
     * @date 2021/4/20
     */
    public static void resolveGeneralContractCreateTxComplementInfo(CollectionTransaction result, String contractAddress, PlatOnClient platOnClient, ComplementInfo ci, Logger log, ContractTypeEnum contractTypeEnum) throws BeanCreateOrUpdateException {
        ci.setInfo("");
        ci.setBinCode(TransactionUtil.getContractBinCode(result, platOnClient, result.getContractAddress(), log));

        if (contractTypeEnum == ContractTypeEnum.ERC20_EVM) {
            ci.setType(com.platon.browser.elasticsearch.dto.Transaction.TypeEnum.ERC20_CONTRACT_CREATE.getCode());
            ci.setToType(com.platon.browser.elasticsearch.dto.Transaction.ToTypeEnum.ERC20_CONTRACT.getCode());
        } else if (contractTypeEnum == ContractTypeEnum.ERC721_EVM) {
            ci.setType(com.platon.browser.elasticsearch.dto.Transaction.TypeEnum.ERC721_CONTRACT_CREATE.getCode());
            ci.setToType(com.platon.browser.elasticsearch.dto.Transaction.ToTypeEnum.ERC721_CONTRACT.getCode());
        } else if (contractTypeEnum == ContractTypeEnum.ERC1155_EVM) {
            ci.setType(com.platon.browser.elasticsearch.dto.Transaction.TypeEnum.ERC1155_CONTRACT_CREATE.getCode());
            ci.setToType(com.platon.browser.elasticsearch.dto.Transaction.ToTypeEnum.ERC1155_CONTRACT.getCode());
        } else if (contractTypeEnum == ContractTypeEnum.WASM) {
            ci.setType(com.platon.browser.elasticsearch.dto.Transaction.TypeEnum.WASM_CONTRACT_CREATE.getCode());
            ci.setToType(com.platon.browser.elasticsearch.dto.Transaction.ToTypeEnum.WASM_CONTRACT.getCode());
        } else {
            ci.setType(com.platon.browser.elasticsearch.dto.Transaction.TypeEnum.EVM_CONTRACT_CREATE.getCode());
            ci.setToType(com.platon.browser.elasticsearch.dto.Transaction.ToTypeEnum.EVM_CONTRACT.getCode());
        }
        ci.setContractType(contractTypeEnum.getCode());

    }

    /**
     * 调用普通合约,解析补充信息
     *
     * @param tx
     * @param platOnClient
     * @param ci
     * @param contractTypeEnum
     * @param logger
     * @return void
     * @date 2021/4/20
     */
    public static void resolveGeneralContractInvokeTxComplementInfo(CollectionTransaction tx, PlatOnClient platOnClient, ComplementInfo ci, ContractTypeEnum contractTypeEnum, Logger logger) throws BeanCreateOrUpdateException {
        ci.setInfo("");
        String binCode = getContractBinCode(tx, platOnClient, tx.getTo(), logger);
        ci.setBinCode(binCode);
        // TODO: 解析出调用合约方法名
        String txInput = tx.getInput();
        // ci.method = getGeneralContractMethod();

        ci.setContractType(contractTypeEnum.getCode());
        if (contractTypeEnum == ContractTypeEnum.EVM) {
            ci.setToType(Transaction.ToTypeEnum.EVM_CONTRACT.getCode());
        }
        if (contractTypeEnum == ContractTypeEnum.WASM) {
            ci.setToType(Transaction.ToTypeEnum.WASM_CONTRACT.getCode());
        }
        ci.setType(Transaction.TypeEnum.CONTRACT_EXEC.getCode());
        if (contractTypeEnum == ContractTypeEnum.ERC20_EVM) {
            ci.setToType(Transaction.ToTypeEnum.ERC20_CONTRACT.getCode());
            ci.setType(Transaction.TypeEnum.ERC20_CONTRACT_EXEC.getCode());
        }
        if (contractTypeEnum == ContractTypeEnum.ERC721_EVM) {
            ci.setToType(Transaction.ToTypeEnum.ERC721_CONTRACT.getCode());
            ci.setType(Transaction.TypeEnum.ERC721_CONTRACT_EXEC.getCode());
        }
        if (contractTypeEnum == ContractTypeEnum.ERC1155_EVM) {
            ci.setToType(Transaction.ToTypeEnum.ERC1155_CONTRACT.getCode());
            ci.setType(Transaction.TypeEnum.ERC1155_CONTRACT_EXEC.getCode());
        }

        if ("0x".equals(binCode)) {
            // 如果交易的binCode属性为0x,则表明掉用了合约自毁方法, 交易类型设置为 合约销毁
            ci.setType(Transaction.TypeEnum.CONTRACT_EXEC_DESTROY.getCode());
        }
    }

    /**
     * 发起普通交易,解析补充信息
     *
     * @param ci
     */
    public static void resolveGeneralTransferTxComplementInfo(CollectionTransaction tx, ComplementInfo ci, AddressCache addressCache) {
        ci.setType(Transaction.TypeEnum.TRANSFER.getCode());
        ci.setContractType(null);
        ci.setMethod(null);
        ci.setInfo("{}");
        ci.setBinCode(null);
        // 需要根据交易的to地址是否是什么类型的地址
        String toAddress = tx.getTo();
        if (InnerContractAddrEnum.getAddresses().contains(toAddress)) {
            ci.setToType(Transaction.ToTypeEnum.INNER_CONTRACT.getCode());
            ci.setContractType(ContractTypeEnum.INNER.getCode());
            ci.setMethod(ContractDescEnum.getMap().get(toAddress).getContractName());
            return;
        }
        if (addressCache.isEvmContractAddress(toAddress)) {
            ci.setToType(Transaction.ToTypeEnum.EVM_CONTRACT.getCode());
            ci.setContractType(ContractTypeEnum.EVM.getCode());
            return;
        }
        if (addressCache.isWasmContractAddress(toAddress)) {
            ci.setToType(Transaction.ToTypeEnum.WASM_CONTRACT.getCode());
            ci.setContractType(ContractTypeEnum.WASM.getCode());
            return;
        }
        if (addressCache.isErc20ContractAddress(toAddress)) {
            ci.setToType(Transaction.ToTypeEnum.ERC20_CONTRACT.getCode());
            ci.setContractType(ContractTypeEnum.ERC20_EVM.getCode());
            return;
        }
        if (addressCache.isErc721ContractAddress(toAddress)) {
            ci.setToType(Transaction.ToTypeEnum.ERC721_CONTRACT.getCode());
            ci.setContractType(ContractTypeEnum.ERC721_EVM.getCode());
            return;
        }
        if (addressCache.isErc1155ContractAddress(toAddress)) {
            ci.setToType(Transaction.ToTypeEnum.ERC1155_CONTRACT.getCode());
            ci.setContractType(ContractTypeEnum.ERC1155_EVM.getCode());
            return;
        }
        ci.setToType(Transaction.ToTypeEnum.ACCOUNT.getCode());
    }

}
