package com.platon.browser.bean.appchain;

import lombok.Data;

import java.math.BigInteger;

@Data
public class UnStaking implements RootChainTxParam{
    private BigInteger validatorId;
}
