package com.platon.browser.bean.rootchain;

import com.platon.browser.utils.CommonUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Data
@Slf4j
public class RootChainTx implements RootChainTxParam {
    private RootChainTxType txType;
    private String txHash;
    private Map<String, Object> txParam;
    private Long rootChainBlockNumber;
    private String rootChainTxHash;
    private Integer rootChainTxIndex;


/*
    enum TxType {
        Stake,
        UnStake,
        Delegate,
        UnDelegate
    }
*/

    @SuppressWarnings("unchecked")
    public RootChainTxParam parseTxParamByTxType(){
        switch (txType){
            case Stake:
                return CommonUtil.map2Bean(txParam, Staking.class);
            case UnStake:
                return CommonUtil.map2Bean(txParam, UnStaking.class);
            case Delegate:
                return CommonUtil.map2Bean(txParam, Delegation.class);
            case UnDelegate:
                return CommonUtil.map2Bean(txParam, UnDelegation.class);
            default:
                return null;
        }
    }

/*

    @Data
    class Staking  {
        String stakingAddress;
        BigInteger validatorId;
        String nodeId;
        BigInteger amount;
    }

    @Data
    class UnStaking  {
        BigInteger validatorId;
    }

    @Data
    class Delegation  {
        String user;
        BigInteger validatorId;
        BigInteger amount;
        BigInteger totalDelegationAmountOfValidator;
    }

    @Data
    class UnDelegation  {
        String user;
        BigInteger validatorId;
        BigInteger amount;
        BigInteger totalDelegationAmountOfValidator;
    }
*/
}





