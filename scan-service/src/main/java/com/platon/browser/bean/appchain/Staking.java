package com.platon.browser.bean.appchain;

import com.platon.browser.utils.HexUtil;
import lombok.Data;

import java.math.BigInteger;

@Data
public class Staking implements RootChainTxParam{
    private String stakingAddress;
    private BigInteger validatorId;
    private String nodeId;
    private BigInteger amount;
    //private BigInteger programVersion;

    public String getNodeId(){
        return HexUtil.prefix(nodeId);
    }
    public String getNodeName(){
        return "NodeName_" + validatorId;
    }
}
