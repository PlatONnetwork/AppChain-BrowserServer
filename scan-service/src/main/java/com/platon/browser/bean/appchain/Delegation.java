package com.platon.browser.bean.appchain;


import lombok.Data;

import java.math.BigInteger;

@Data
public class Delegation implements RootChainTxParam{
    private String user;
    private BigInteger validatorId;
    private BigInteger amount;
    private BigInteger totalDelegationAmountOfValidator;
}
