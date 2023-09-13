package com.platon.browser.bean;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.math.BigDecimal;

/**
 * 结算周期信息
 *
 * @date: 2021/12/22
 */
public class EpochInfo {

    /**
     * 出块奖励--废弃
     */
    //private BigDecimal packageReward;

    /**
     * 结算周期质押奖励--废弃
     */
   // private BigDecimal stakingReward;

    /**
     * 当前增发周期
     */
    private BigDecimal chainAge;

    /**
     * 当前增发周期开始区块号
     */
    private BigDecimal yearStartBlockNum;

    /**
     * 当前增发周期结束区块号
     */
    private BigDecimal yearEndBlockNum;

    /**
     * 当前增发周期剩下的结算周期数
     */
    private BigDecimal remainEpoch;

    /**
     * 平均出块时间
     */
    private BigDecimal avgPackTime;

    /**
     * 当前结算周期的出块奖励
     */
    private BigDecimal curPackageReward;

    /**
     * 当前结算周期的质押奖励
     */
    private BigDecimal curStakingReward;

    /**
     * 下一个结算周期的出块奖励
     */
    private BigDecimal nextPackageReward;

    /**
     * 下一个结算周期的质押奖励
     */
    private BigDecimal nextStakingReward;

/*    public BigDecimal getPackageReward() {
        return packageReward;
    }

    public void setPackageReward(BigDecimal packageReward) {
        this.packageReward = packageReward;
    }

    public BigDecimal getStakingReward() {
        return stakingReward;
    }

    public void setStakingReward(BigDecimal stakingReward) {
        this.stakingReward = stakingReward;
    }*/

    public BigDecimal getChainAge() {
        return chainAge;
    }

    public void setChainAge(BigDecimal chainAge) {
        this.chainAge = chainAge;
    }

    public BigDecimal getYearStartBlockNum() {
        return yearStartBlockNum;
    }

    public void setYearStartBlockNum(BigDecimal yearStartBlockNum) {
        this.yearStartBlockNum = yearStartBlockNum;
    }

    public BigDecimal getYearEndBlockNum() {
        return yearEndBlockNum;
    }

    public void setYearEndBlockNum(BigDecimal yearEndBlockNum) {
        this.yearEndBlockNum = yearEndBlockNum;
    }

    public BigDecimal getRemainEpoch() {
        return remainEpoch;
    }

    public void setRemainEpoch(BigDecimal remainEpoch) {
        this.remainEpoch = remainEpoch;
    }

    public BigDecimal getAvgPackTime() {
        return avgPackTime;
    }

    public void setAvgPackTime(BigDecimal avgPackTime) {
        this.avgPackTime = avgPackTime;
    }

    public BigDecimal getCurPackageReward() {
        return curPackageReward;
    }

    public void setCurPackageReward(BigDecimal curPackageReward) {
        this.curPackageReward = curPackageReward;
    }

    public BigDecimal getCurStakingReward() {
        return curStakingReward;
    }

    public void setCurStakingReward(BigDecimal curStakingReward) {
        this.curStakingReward = curStakingReward;
    }

    public BigDecimal getNextPackageReward() {
        return nextPackageReward;
    }

    public void setNextPackageReward(BigDecimal nextPackageReward) {
        this.nextPackageReward = nextPackageReward;
    }

    public BigDecimal getNextStakingReward() {
        return nextStakingReward;
    }

    public void setNextStakingReward(BigDecimal nextStakingReward) {
        this.nextStakingReward = nextStakingReward;
    }


    public static void main(String[] args) throws JsonProcessingException {
        String json = "{\n" +
                "\t\"nextPackageReward\": 4580147713699926138,\n" +
                "\t\"nextStakingReward\": 49236587922274205984720,\n" +
                "\t\"curPackageReward\": 4580147713699926138,\n" +
                "\t\"curStakingReward\": 49236587922274205984720,\n" +
                "\t\"chainAge\": 1,\n" +
                "\t\"yearStartBlockNum\": 1,\n" +
                "\t\"yearEndBlockNum\": 28605751,\n" +
                "\t\"remainEpoch\": 2661,\n" +
                "\t\"avgPackTime\": 1103\n" +
                "}";
        ObjectMapper mapper = new ObjectMapper();
        EpochInfo epochInfo = mapper.readValue(json, EpochInfo.class);
        System.out.println("echoInfo:" + JSON.toJSONString(epochInfo));


            System.out.println("-1L % 1000L == 0" + (-1L % 1000L == 0));

    }
}
