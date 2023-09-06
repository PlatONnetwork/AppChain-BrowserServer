package com.platon.browser.bean;

import java.math.BigInteger;

/**
 * 委托锁定中锁定的列表
 *
 * @date: 2022/8/26
 */
public class DlLock {

    /**
     * 处于锁定期的委托金，资金来源是锁仓计划
     */
    private BigInteger restrictingPlanAmount;

    /**
     * 处于锁定期的委托金，资金来源是用户账户余额
     */
    private BigInteger freeBalance;

    /**
     * 解锁结算周期,锁定截止周期
     */
    private BigInteger expiredEpoch;

    public BigInteger getRestrictingPlanAmount() {
        return restrictingPlanAmount;
    }

    public void setRestrictingPlanAmount(BigInteger restrictingPlanAmount) {
        this.restrictingPlanAmount = restrictingPlanAmount;
    }

    public BigInteger getFreeBalance() {
        return freeBalance;
    }

    public void setFreeBalance(BigInteger freeBalance) {
        this.freeBalance = freeBalance;
    }

    public BigInteger getExpiredEpoch() {
        return expiredEpoch;
    }

    public void setExpiredEpoch(BigInteger expiredEpoch) {
        this.expiredEpoch = expiredEpoch;
    }

}
