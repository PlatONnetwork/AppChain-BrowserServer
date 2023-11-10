package com.platon.browser.analyzer.ppos.appchain;

import com.alibaba.fastjson.JSON;
import com.platon.browser.analyzer.ppos.PPOSAnalyzer;
import com.platon.browser.bean.CollectionEvent;
import com.platon.browser.bean.ComplementNodeOpt;
import com.platon.browser.bean.appchain.RootChainTx;
import com.platon.browser.bean.appchain.Staking;
import com.platon.browser.bean.appchain.UnStaking;
import com.platon.browser.dao.custommapper.CustomInternalAddressMapper;
import com.platon.browser.dao.custommapper.CustomNodeMapper;
import com.platon.browser.dao.custommapper.StakeBusinessMapper;
import com.platon.browser.dao.entity.Node;
import com.platon.browser.dao.param.ppos.StakeCreate;
import com.platon.browser.elasticsearch.dto.NodeOpt;
import com.platon.browser.elasticsearch.dto.Transaction;
import com.platon.browser.enums.ModifiableGovernParamEnum;
import com.platon.browser.exception.BlockNumberException;
import com.platon.browser.exception.BusinessException;
import com.platon.browser.exception.NoSuchBeanException;
import com.platon.browser.service.govern.ParameterService;
import com.platon.browser.service.ppos.StakeEpochService;
import com.platon.browser.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

@Slf4j
@Service
public class RootChainTxAnalyzer extends PPOSAnalyzer<NodeOpt> {
    /**
     * 主链质押
     *
     * @param event
     * @param tx
     * @param rootChainTx
     * @return com.platon.browser.elasticsearch.dto.NodeOpt
     * @date 2021/6/15
     */

    @Resource
    private StakeBusinessMapper stakeBusinessMapper;

    @Resource
    private CustomNodeMapper customNodeMapper;

    @Resource
    private ParameterService parameterService;

    @Resource
    private StakeEpochService stakeEpochService;

    @Resource
    private CustomInternalAddressMapper customInternalAddressMapper;

    public NodeOpt stake(CollectionEvent event, Transaction tx, RootChainTx rootChainTx) {
        Staking stakingParam = (Staking)rootChainTx.parseTxParamByTxType();

        log.debug("current tx is a syncRootChainState tx, blockNumber:{}, txHash:{}, rootChainTxParam:{}", tx.getNum(), tx.getHash(), JSON.toJSONString(rootChainTx.getTxParam()));

        //BigInteger bigVersion = ChainVersionUtil.toBigVersion(stakingParam.getProgramVersion());
        BigInteger bigVersion = BigInteger.ZERO;
        BigInteger stakingBlockNum = BigInteger.valueOf(tx.getNum());

        String configVal = parameterService.getValueInBlockChainConfig(ModifiableGovernParamEnum.UN_STAKE_FREEZE_DURATION.getName());
        if (StringUtils.isBlank(configVal)) {
            throw new BusinessException("参数表参数缺失：" + ModifiableGovernParamEnum.UN_STAKE_FREEZE_DURATION.getName());
        }
        Date txTime = DateUtil.covertTime(tx.getTime());
        // 更新解质押到账需要经过的结算周期数
        BigInteger unStakeFreezeDuration = stakeEpochService.getUnStakeFreeDuration();
        // 理论上的退出区块号
        BigInteger unStakeEndBlock = stakeEpochService.getUnStakeEndBlock(stakingParam.getNodeId(), event.getEpochMessage().getSettleEpochRound(), false);
        StakeCreate businessParam = StakeCreate.builder()
                .nodeId(stakingParam.getNodeId())
                .validatorId(stakingParam.getValidatorId())
                .stakingHes(new BigDecimal(stakingParam.getAmount()))
                .nodeName(stakingParam.getNodeName())
                .externalId("")
                .benefitAddr("")
                .programVersion("0")
                .bigVersion(bigVersion.toString())
                .webSite("")
                .details("")
                .isInit(isInit(""))
                .stakingBlockNum(stakingBlockNum)
                .stakingTxIndex(tx.getIndex())
                .stakingAddr(tx.getFrom())
                .joinTime(txTime)
                .txHash(tx.getHash())
                .delegateRewardPer(0)
                .unStakeFreezeDuration(unStakeFreezeDuration.intValue())
                .unStakeEndBlock(unStakeEndBlock)
                .settleEpoch(event.getEpochMessage().getSettleEpochRound().intValue())
                //.rootChainTxHash(rootChainTx.getRootChainTxHash())
                .build();

        //增加质押信息，节点信息
        log.debug("处理root chain质押交易，增加质押节点和质押记录，块高：{}", event.getBlock().getNum());
        stakeBusinessMapper.create(businessParam);

        //更新internal_address的质押合约表余额
        if(stakingParam.getAmount()!=null && stakingParam.getAmount().compareTo(BigInteger.ZERO)>0){
            customInternalAddressMapper.updateStakingContractBalance(stakingParam.getAmount());
        }

        updateNodeCache(stakingParam.getNodeId(), stakingParam.getNodeName(), BigInteger.valueOf(event.getBlock().getNum()));

        NodeOpt nodeOpt = ComplementNodeOpt.newInstance();
        nodeOpt.setNodeId(stakingParam.getNodeId());
        nodeOpt.setType(Integer.valueOf(NodeOpt.TypeEnum.CREATE.getCode()));
        nodeOpt.setTxHash(tx.getHash());
        nodeOpt.setBNum(tx.getNum());
        nodeOpt.setTime(tx.getTime());
        return nodeOpt;
    }
    public NodeOpt unstake(CollectionEvent event, Transaction tx, RootChainTx rootChainTx) {
        UnStaking unstaking = (UnStaking)rootChainTx.parseTxParamByTxType();

        // updateNodeCache(HexUtil.prefix(staking.getNodeId()), staking.getNodeId(), BigInteger.valueOf(event.getBlock().getNum()));

        Node node = customNodeMapper.findByValidatorId(unstaking.getValidatorId());
        if (node != null){
            NodeOpt nodeOpt = ComplementNodeOpt.newInstance();
            nodeOpt.setNodeId(node.getNodeId());
            nodeOpt.setType(Integer.valueOf(NodeOpt.TypeEnum.QUIT.getCode()));
            nodeOpt.setTxHash(tx.getHash());
            nodeOpt.setBNum(tx.getNum());
            nodeOpt.setTime(tx.getTime());
            return nodeOpt;
        }
        return null;
    }

    public void delegate(CollectionEvent event, Transaction tx, RootChainTx rootChainTx) {
    }

    public void undelegate(CollectionEvent event, Transaction tx, RootChainTx rootChainTx) {
    }

    @Override
    public NodeOpt analyze(CollectionEvent event, Transaction tx) throws NoSuchBeanException, BlockNumberException {
        return null;
    }
}
