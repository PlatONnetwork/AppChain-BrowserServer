package com.platon.browser.bean.rootchain;

import lombok.Data;

import java.math.BigInteger;

@Data
public class UnStaking implements RootChainTxParam{
    private BigInteger validatorId;
}

