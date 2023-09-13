package com.platon.browser.bean;

import com.platon.browser.bean.appchain.RootChainTx;
import com.platon.protocol.core.methods.response.Log;
import com.platon.rlp.solidity.RlpDecoder;
import com.platon.rlp.solidity.RlpList;
import com.platon.rlp.solidity.RlpString;
import com.platon.rlp.solidity.RlpType;
import com.platon.utils.Numeric;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.math.BigInteger;
import java.util.List;

@Data
public class Receipt {
    public static final int SUCCESS = 1;
    public static final int FAILURE = 2;

    private Long blockNumber;
    private String gasUsed;
    private List<Log> logs;
    private String transactionHash;
    private String transactionIndex;
    private String status;
    private String contractAddress;              //原始交易to为空时，新建的合约地址
    private List<ContractInfo> contractCreated;  //原始交易执行后，新建的合约地址（包括to为空时创建的合约地址）；为空传0长度列表
    private List<ContractInfo> contractSuicided; //原始交易执行后，自杀的合约地址；为空传0长度列表
    private List<ProxyPattern> proxyPatterns;    //原始交易执行后，发现的代理合约；为空传0长度列表
    private List<EmbedTransfer> embedTransfers;  //原始交易执行后，引起的内置LAT转账交易
    private List<ImplicitPPOSTx> implicitPPOSTxs;  //原始交易执行后，引起的内置LAT转账交易
    private List<String> topics;
    private List<RootChainTx> rootChainTxs;

    private int logStatus;

    private String failReason;

    public int getStatus() {
        if (null == this.status)
            return SUCCESS;
        BigInteger statusQuantity = Numeric.decodeQuantity(this.status);
        return BigInteger.ONE.equals(statusQuantity) ? SUCCESS : FAILURE;
    }

    public BigInteger getGasUsed() {
        return Numeric.decodeQuantity(this.gasUsed);
    }

    /**
     * 解码log
     * 2023-08-31，不需要在这里解码log，因为根本不着调如何解码。
     * @deprecated
     */
    public void decodeLogs() {
        if (this.logs == null || this.logs.isEmpty()) {
            this.logStatus = FAILURE;
            return;
        }
        Log log = this.logs.get(0);
        String data = log.getData();
        if (StringUtils.isBlank(data)) {
            this.logStatus = FAILURE;
            return;
        }
        RlpList rlp = RlpDecoder.decode(Numeric.hexStringToByteArray(data));
        List<RlpType> rlpList = ((RlpList)(rlp.getValues().get(0))).getValues();
        // 2023-08-31，这个至是内置合约交易才是返回这个数据结构
        // 如果datas为空, log data =  rlp([errCodeString]),
        // 如果datas不为空, log data =  rlp([errCodeString,rlp(data1),rlp(data2)...]),
        String decodedStatus = new String(((RlpString)rlpList.get(0)).getBytes());
        int statusCode = Integer.parseInt(decodedStatus);
        if (statusCode == 0) {
            this.logStatus = SUCCESS;
        } else {
            this.failReason = decodedStatus;
            this.logStatus = FAILURE;
        }
    }
}
