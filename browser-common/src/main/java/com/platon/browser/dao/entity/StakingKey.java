package com.platon.browser.dao.entity;

import java.util.ArrayList;
import java.util.Arrays;

public class StakingKey {
    private String nodeId;

    private Long stakingBlockNum;

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId == null ? null : nodeId.trim();
    }

    public Long getStakingBlockNum() {
        return stakingBlockNum;
    }

    public void setStakingBlockNum(Long stakingBlockNum) {
        this.stakingBlockNum = stakingBlockNum;
    }

    /**
     * This enum was generated by MyBatis Generator.
     * This enum corresponds to the database table staking
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    public enum Column {
        nodeId("node_id", "nodeId", "VARCHAR", false),
        stakingBlockNum("staking_block_num", "stakingBlockNum", "BIGINT", false),
        stakingTxIndex("staking_tx_index", "stakingTxIndex", "INTEGER", false),
        stakingHes("staking_hes", "stakingHes", "DECIMAL", false),
        stakingLocked("staking_locked", "stakingLocked", "DECIMAL", false),
        stakingReduction("staking_reduction", "stakingReduction", "DECIMAL", false),
        stakingReductionEpoch("staking_reduction_epoch", "stakingReductionEpoch", "INTEGER", false),
        nodeName("node_name", "nodeName", "VARCHAR", false),
        nodeIcon("node_icon", "nodeIcon", "VARCHAR", false),
        externalId("external_id", "externalId", "VARCHAR", false),
        externalName("external_name", "externalName", "VARCHAR", false),
        stakingAddr("staking_addr", "stakingAddr", "VARCHAR", false),
        benefitAddr("benefit_addr", "benefitAddr", "VARCHAR", false),
        annualizedRate("annualized_rate", "annualizedRate", "DOUBLE", false),
        programVersion("program_version", "programVersion", "VARCHAR", false),
        bigVersion("big_version", "bigVersion", "VARCHAR", false),
        webSite("web_site", "webSite", "VARCHAR", false),
        details("details", "details", "VARCHAR", false),
        joinTime("join_time", "joinTime", "TIMESTAMP", false),
        leaveTime("leave_time", "leaveTime", "TIMESTAMP", false),
        status("status", "status", "INTEGER", true),
        isConsensus("is_consensus", "isConsensus", "INTEGER", false),
        isSettle("is_settle", "isSettle", "INTEGER", false),
        isInit("is_init", "isInit", "INTEGER", false),
        statDelegateHes("stat_delegate_hes", "statDelegateHes", "DECIMAL", false),
        statDelegateLocked("stat_delegate_locked", "statDelegateLocked", "DECIMAL", false),
        statDelegateReleased("stat_delegate_released", "statDelegateReleased", "DECIMAL", false),
        blockRewardValue("block_reward_value", "blockRewardValue", "DECIMAL", false),
        predictStakingReward("predict_staking_reward", "predictStakingReward", "DECIMAL", false),
        stakingRewardValue("staking_reward_value", "stakingRewardValue", "DECIMAL", false),
        feeRewardValue("fee_reward_value", "feeRewardValue", "DECIMAL", false),
        curConsBlockQty("cur_cons_block_qty", "curConsBlockQty", "BIGINT", false),
        preConsBlockQty("pre_cons_block_qty", "preConsBlockQty", "BIGINT", false),
        createTime("create_time", "createTime", "TIMESTAMP", false),
        updateTime("update_time", "updateTime", "TIMESTAMP", false),
        rewardPer("reward_per", "rewardPer", "INTEGER", false),
        haveDeleReward("have_dele_reward", "haveDeleReward", "DECIMAL", false),
        preDeleAnnualizedRate("pre_dele_annualized_rate", "preDeleAnnualizedRate", "DOUBLE", false),
        deleAnnualizedRate("dele_annualized_rate", "deleAnnualizedRate", "DOUBLE", false),
        totalDeleReward("total_dele_reward", "totalDeleReward", "DECIMAL", false),
        exceptionStatus("exception_status", "exceptionStatus", "INTEGER", false),
        annualizedRateInfo("annualized_rate_info", "annualizedRateInfo", "LONGVARCHAR", false);

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String BEGINNING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private static final String ENDING_DELIMITER = "`";

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String column;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final boolean isColumnNameDelimited;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String javaProperty;

        /**
         * This field was generated by MyBatis Generator.
         * This field corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        private final String jdbcType;

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String value() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getValue() {
            return this.column;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJavaProperty() {
            return this.javaProperty;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getJdbcType() {
            return this.jdbcType;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        Column(String column, String javaProperty, String jdbcType, boolean isColumnNameDelimited) {
            this.column = column;
            this.javaProperty = javaProperty;
            this.jdbcType = jdbcType;
            this.isColumnNameDelimited = isColumnNameDelimited;
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String desc() {
            return this.getEscapedColumnName() + " DESC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String asc() {
            return this.getEscapedColumnName() + " ASC";
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public static Column[] excludes(Column ... excludes) {
            ArrayList<Column> columns = new ArrayList<>(Arrays.asList(Column.values()));
            if (excludes != null && excludes.length > 0) {
                columns.removeAll(new ArrayList<>(Arrays.asList(excludes)));
            }
            return columns.toArray(new Column[]{});
        }

        /**
         * This method was generated by MyBatis Generator.
         * This method corresponds to the database table staking
         *
         * @mbg.generated
         * @project https://github.com/itfsw/mybatis-generator-plugin
         */
        public String getEscapedColumnName() {
            if (this.isColumnNameDelimited) {
                return new StringBuilder().append(BEGINNING_DELIMITER).append(this.column).append(ENDING_DELIMITER).toString();
            } else {
                return this.column;
            }
        }
    }
}