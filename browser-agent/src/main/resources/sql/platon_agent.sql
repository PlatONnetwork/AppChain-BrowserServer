#创建数据库脚本
CREATE DATABASE IF NOT EXISTS `platon_agent`;

-- ----------------------------
-- Table structure for address
-- ----------------------------
DROP TABLE IF EXISTS `address`;
CREATE TABLE `address` (
  `address` varchar(42) NOT NULL COMMENT '地址',
  `type` int(2) NOT NULL COMMENT '地址类型 :1账号,2内置合约 ,3EVM合约,4WASM合约',
  `balance` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '余额(von)',
  `restricting_balance` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '锁仓余额(von)',
  `staking_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '质押的金额(von)',
  `delegate_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '委托的金额(von)',
  `redeemed_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '赎回中的质押金额(von)',
  `tx_qty` int(11) NOT NULL DEFAULT '0' COMMENT '交易总数',
  `transfer_qty` int(11) NOT NULL DEFAULT '0' COMMENT '转账交易总数',
  `delegate_qty` int(11) NOT NULL DEFAULT '0' COMMENT '委托交易总数',
  `staking_qty` int(11) NOT NULL DEFAULT '0' COMMENT '质押交易总数',
  `proposal_qty` int(11) NOT NULL DEFAULT '0' COMMENT '治理交易总数',
  `candidate_count` int(11) NOT NULL DEFAULT '0' COMMENT '已委托的验证人数',
  `delegate_hes` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '未锁定委托(von)',
  `delegate_locked` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '已锁定委托(von)',
  `delegate_released` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '待赎回委托金额(von,需要用户主动发起赎回交易)',
  `contract_name` varchar(125) NOT NULL DEFAULT '' COMMENT '合约名称',
  `contract_create` varchar(125) NOT NULL DEFAULT '' COMMENT '合约创建者地址',
  `contract_createHash` varchar(66) NOT NULL DEFAULT '0' COMMENT '创建合约的交易Hash',
  `contract_destroy_hash` varchar(66) DEFAULT '' COMMENT '销毁合约的交易Hash',
  `contract_bin` longtext COMMENT '合约bincode数据(通过web3j查询出来的合约代码)',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `have_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '已领取委托奖励',
  PRIMARY KEY (`address`),
  KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for config
-- ----------------------------
DROP TABLE IF EXISTS `config`;
CREATE TABLE `config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `module` varchar(64) NOT NULL COMMENT '参数模块名',
  `name` varchar(128) NOT NULL COMMENT '参数名',
  `init_value` varchar(255) NOT NULL COMMENT '系统初始值',
  `stale_value` varchar(255) NOT NULL COMMENT '旧值',
  `value` varchar(255) NOT NULL COMMENT '新值',
  `range_desc` varchar(255) NOT NULL COMMENT '参数取值范围描述',
  `active_block` bigint(20) NOT NULL COMMENT '生效块高',
  `create_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for delegation
-- ----------------------------
DROP TABLE IF EXISTS `delegation`;
CREATE TABLE `delegation` (
  `delegate_addr` varchar(42) NOT NULL COMMENT '委托交易地址',
  `staking_block_num` bigint(20) NOT NULL COMMENT '最新的质押交易块高',
  `node_id` varchar(130) NOT NULL COMMENT '节点id',
  `delegate_hes` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '未锁定委托金额(von)',
  `delegate_locked` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '已锁定委托金额(von)',
  `delegate_released` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '待提取的金额(von)',
  `is_history` int(2) NOT NULL DEFAULT '2' COMMENT '是否为历史:\r\n1是,\r\n2否',
  `sequence` bigint(20) NOT NULL COMMENT '首次委托时的序号：blockNum*100000+tx_index',
  `cur_delegation_block_num` bigint(20) NOT NULL COMMENT '最新委托交易块号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`delegate_addr`,`staking_block_num`,`node_id`),
  KEY `node_id` (`node_id`) USING BTREE,
  KEY `staking_block_num` (`staking_block_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gas_estimate
-- ----------------------------
DROP TABLE IF EXISTS `gas_estimate`;
CREATE TABLE `gas_estimate` (
  `addr` varchar(42) NOT NULL COMMENT '委托交易地址',
  `node_id` varchar(130) NOT NULL COMMENT '节点id',
  `sbn` bigint(20) NOT NULL COMMENT '最新的质押交易块高',
  `epoch` bigint(20) NOT NULL DEFAULT '0' COMMENT '委托未计算周期',
  PRIMARY KEY (`addr`,`node_id`,`sbn`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for gas_estimate_log
-- ----------------------------
DROP TABLE IF EXISTS `gas_estimate_log`;
CREATE TABLE `gas_estimate_log` (
  `seq` bigint(20) NOT NULL COMMENT '序号',
  `json` longtext NOT NULL,
  PRIMARY KEY (`seq`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for n_opt_bak
-- ----------------------------
DROP TABLE IF EXISTS `n_opt_bak`;
CREATE TABLE `n_opt_bak` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '自增id',
  `node_id` varchar(130) NOT NULL COMMENT '节点id',
  `type` int(2) NOT NULL COMMENT '操作类型:\r\n1创建,\r\n2修改\r\n,3退出,\r\n4提案,\r\n5投票,\r\n6双签\r\n,7出块率低',
  `tx_hash` varchar(66) DEFAULT NULL COMMENT '交易hash',
  `b_num` bigint(20) DEFAULT NULL COMMENT '交易所在区块号',
  `time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '时间',
  `desc` varchar(2500) DEFAULT NULL COMMENT '操作描述',
  `cre_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `upd_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`),
  KEY `node_id` (`node_id`) USING BTREE,
  KEY `tx_hash` (`tx_hash`) USING BTREE,
  KEY `block_number` (`b_num`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for network_stat
-- ----------------------------
DROP TABLE IF EXISTS `network_stat`;
CREATE TABLE `network_stat` (
  `id` int(2) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `cur_number` bigint(20) NOT NULL COMMENT '当前块号',
  `cur_block_hash` varchar(66) NOT NULL DEFAULT '' COMMENT '当前区块Hash',
  `node_id` varchar(130) NOT NULL COMMENT '节点ID',
  `node_name` varchar(125) NOT NULL DEFAULT '' COMMENT '节点名称',
  `tx_qty` int(11) NOT NULL DEFAULT '0' COMMENT '交易总数',
  `cur_tps` int(11) NOT NULL DEFAULT '0' COMMENT '当前交易TPS',
  `max_tps` int(11) NOT NULL DEFAULT '0' COMMENT '最大交易TPS',
  `issue_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '当前发行量(von)',
  `turn_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '当前流通量(von)',
  `available_staking` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '可用总质押量(von)',
  `staking_delegation_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '实时质押委托总数(von)',
  `staking_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '实时质押总数(von)',
  `doing_proposal_qty` int(11) NOT NULL DEFAULT '0' COMMENT '进行中提案总数',
  `proposal_qty` int(11) NOT NULL DEFAULT '0' COMMENT '提案总数',
  `address_qty` int(10) unsigned NOT NULL DEFAULT '0' COMMENT '地址数',
  `block_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '当前出块奖励(von)',
  `staking_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '当前质押奖励(von)',
  `settle_staking_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '当前结算周期总质押奖励',
  `add_issue_begin` bigint(20) NOT NULL COMMENT '当前增发周期的开始块号',
  `add_issue_end` bigint(20) NOT NULL COMMENT '当前增发周期的结束块号',
  `next_settle` bigint(20) NOT NULL COMMENT '离下个结算周期的剩余块数',
  `node_opt_seq` bigint(20) NOT NULL COMMENT '节点操作记录最新序号',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `avg_pack_time` bigint(20) NOT NULL DEFAULT '0' COMMENT '平均区块打包时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for node
-- ----------------------------
DROP TABLE IF EXISTS `node`;
CREATE TABLE `node` (
  `node_id` varchar(130) NOT NULL COMMENT '节点id',
  `stat_slash_multi_qty` int(11) NOT NULL DEFAULT '0' COMMENT '多签举报次数',
  `stat_slash_low_qty` int(11) NOT NULL DEFAULT '0' COMMENT '出块率低举报次数',
  `stat_block_qty` bigint(20) NOT NULL DEFAULT '0' COMMENT '节点处块数统计',
  `stat_expect_block_qty` bigint(20) NOT NULL DEFAULT '0' COMMENT '节点期望出块数',
  `stat_verifier_time` int(11) NOT NULL DEFAULT '0' COMMENT '进入共识验证轮次数',
  `is_recommend` int(2) NOT NULL DEFAULT '2' COMMENT '官方推荐 :1是\r\n2:否',
  `total_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '有效的质押委托总数(von)',
  `staking_block_num` bigint(20) NOT NULL COMMENT '质押时的区块号',
  `staking_tx_index` int(11) NOT NULL COMMENT '发起质押交易的索引',
  `staking_hes` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '犹豫期的质押金(von)',
  `staking_locked` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '锁定期的质押金(von)',
  `staking_reduction` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '退回中的质押金(von)',
  `staking_reduction_epoch` int(11) NOT NULL DEFAULT '0' COMMENT '结算周期标识',
  `node_name` varchar(64) NOT NULL DEFAULT '' COMMENT '节点名称(质押节点名称)',
  `node_icon` varchar(255) DEFAULT '' COMMENT '节点头像(关联external_id,第三方软件获取)',
  `external_id` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方社交软件关联id',
  `external_name` varchar(128) DEFAULT NULL COMMENT '第三方社交软件关联用户名',
  `staking_addr` varchar(42) NOT NULL COMMENT '发起质押的账户地址',
  `benefit_addr` varchar(42) NOT NULL DEFAULT '' COMMENT '收益地址',
  `annualized_rate` double(16,2) NOT NULL DEFAULT '0.00' COMMENT '预计年化率',
  `program_version` int(11) NOT NULL DEFAULT '0' COMMENT '程序版本',
  `big_version` int(11) NOT NULL DEFAULT '0' COMMENT '大程序版本',
  `web_site` varchar(255) NOT NULL DEFAULT '' COMMENT '节点的第三方主页',
  `details` varchar(255) NOT NULL DEFAULT '' COMMENT '节点的描述',
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  `leave_time` timestamp NULL DEFAULT NULL COMMENT '离开时间',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '节点状态:\r\n1候选中\r\n,2退出中\r\n,3已退出',
  `is_consensus` int(2) NOT NULL DEFAULT '2' COMMENT '是否共识周期验证人\r\n:1是\r\n,2否',
  `is_settle` int(2) NOT NULL DEFAULT '2' COMMENT '是否结算周期验证人:\r\n1是\r\n,2否',
  `is_init` int(2) NOT NULL DEFAULT '2' COMMENT '是否为链初始化时内置的候选人\r\n:1是\r\n,2否',
  `stat_delegate_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '有效的委托金额(von)',
  `stat_delegate_released` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '待提取的委托金额(von)',
  `stat_valid_addrs` int(11) NOT NULL DEFAULT '0' COMMENT '有效委托地址数',
  `stat_invalid_addrs` int(11) NOT NULL DEFAULT '0' COMMENT '待提取委托地址数',
  `stat_block_reward_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '出块奖励统计(激励池)(von)',
  `stat_staking_reward_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '质押奖励统计(激励池)(von)',
  `stat_fee_reward_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '出块奖励统计(手续费)(von)',
  `predict_staking_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '当前结算周期预计可获得的质押奖励',
  `annualized_rate_info` longtext COMMENT '最近几个结算周期收益和质押信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `reward_per` int(11) NOT NULL DEFAULT '0' COMMENT '委托奖励比例',
  `have_dele_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '所有质押已领取委托奖励',
  `pre_dele_annualized_rate` double(16,2) NOT NULL DEFAULT '0.00' COMMENT '前一参与周期预计委托收益率',
  `dele_annualized_rate` double(16,2) NOT NULL DEFAULT '0.00' COMMENT '预计委托收益率',
  `total_dele_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '当前质押总的委托奖励',
  `pre_total_dele_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '所有历史质押记录总的委托奖励累计字段(在质押退出时会把total_dele_reward累加到此字段)',
  `exception_status` int(2) NOT NULL DEFAULT '1' COMMENT '1正常,2低出块异常,3被双签,4因低出块被惩罚(例如在连续两个周期当选验证人，但在第一个周期出块率低),5因双签被惩罚',
  `un_stake_freeze_duration` int(11) NOT NULL COMMENT '解质押理论上锁定的结算周期数',
  `un_stake_end_block` bigint(20) DEFAULT NULL COMMENT '解质押冻结的最后一个区块：理论结束块与投票结束块中的最大者',
  PRIMARY KEY (`node_id`),
  KEY `node_id` (`node_id`) USING BTREE,
  KEY `status` (`status`),
  KEY `staking_addr` (`staking_addr`),
  KEY `benefit_addr` (`benefit_addr`),
  KEY `list` (`status`,`is_settle`,`big_version`,`total_value`,`staking_block_num`,`staking_tx_index`),
  KEY `list2` (`big_version`,`total_value`,`staking_block_num`,`staking_tx_index`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for proposal
-- ----------------------------
DROP TABLE IF EXISTS `proposal`;
CREATE TABLE `proposal` (
  `hash` varchar(66) NOT NULL COMMENT '提案交易hash',
  `type` int(2) NOT NULL COMMENT '提案类型:1文本提案,2升级提案,3参数提案,4取消提案',
  `node_id` varchar(130) NOT NULL COMMENT '提交提案验证人(节点ID)',
  `node_name` varchar(128) NOT NULL COMMENT '提交提案验证人名称(节点名称)',
  `url` varchar(255) NOT NULL COMMENT '提案URL',
  `new_version` varchar(64) DEFAULT NULL COMMENT '新提案版本',
  `end_voting_block` bigint(20) NOT NULL COMMENT '提案结束区块',
  `active_block` bigint(20) DEFAULT NULL COMMENT '提案生效区块',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提案时间',
  `yeas` bigint(20) NOT NULL DEFAULT '0' COMMENT '赞成票',
  `nays` bigint(20) NOT NULL DEFAULT '0' COMMENT '反对票',
  `abstentions` bigint(20) NOT NULL DEFAULT '0' COMMENT '弃权票',
  `accu_verifiers` bigint(20) NOT NULL DEFAULT '0' COMMENT '在整个投票期内有投票资格的验证人总数',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '提案状态:\r\n1投票中\r\n,2通过,\r\n3失败,\r\n4预升级,5生效\r\n,6被取消',
  `pip_num` varchar(128) NOT NULL COMMENT 'pip编号(需要组装 PIP-编号)',
  `pip_id` varchar(128) NOT NULL COMMENT '提案id',
  `topic` varchar(255) DEFAULT NULL COMMENT '提案主题',
  `description` varchar(255) DEFAULT NULL COMMENT '提案描述',
  `canceled_pip_id` varchar(255) DEFAULT NULL COMMENT '被取消提案id',
  `canceled_topic` varchar(255) DEFAULT NULL COMMENT '被取消的提案的主题',
  `block_number` bigint(20) NOT NULL COMMENT '议案交易所在区块',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `completion_flag` int(2) NOT NULL DEFAULT '2' COMMENT '提案相关数据是否补充完成标识:1是,2否',
  `module` varchar(64) DEFAULT NULL COMMENT '参数模块(参数提案专有属性)',
  `name` varchar(128) DEFAULT NULL COMMENT '参数名称(参数提案专有属性)',
  `stale_value` varchar(255) DEFAULT NULL COMMENT '原参数值',
  `new_value` varchar(255) DEFAULT NULL COMMENT '参数值(参数提案专有属性)',
  PRIMARY KEY (`hash`),
  KEY `type` (`type`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for rp_plan
-- ----------------------------
DROP TABLE IF EXISTS `rp_plan`;
CREATE TABLE `rp_plan` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `address` varchar(42) NOT NULL DEFAULT '0' COMMENT '发布锁仓计划地址',
  `epoch` bigint(20) NOT NULL COMMENT '锁仓计划周期',
  `amount` decimal(65,0) NOT NULL COMMENT '区块上待释放的金额(von)',
  `number` bigint(20) NOT NULL COMMENT '锁仓计划所在区块',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for slash
-- ----------------------------
DROP TABLE IF EXISTS `slash`;
CREATE TABLE `slash` (
  `hash` varchar(66) NOT NULL COMMENT '举报交易hash',
  `node_id` varchar(130) NOT NULL COMMENT '节点id',
  `slash_rate` varchar(64) NOT NULL COMMENT '扣除比例',
  `slash_value` decimal(65,0) NOT NULL COMMENT '惩罚的金额',
  `reward` decimal(65,0) NOT NULL COMMENT '举报成功的奖励',
  `benefit_addr` varchar(42) NOT NULL COMMENT '收取举报奖励地址',
  `data` text NOT NULL COMMENT '举报证据',
  `is_quit` int(2) NOT NULL DEFAULT '1' COMMENT '是否退出:\r\n1是,\r\n2否',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`hash`),
  KEY `node_id` (`node_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for staking
-- ----------------------------
DROP TABLE IF EXISTS `staking`;
CREATE TABLE `staking` (
  `node_id` varchar(130) NOT NULL COMMENT '质押节点地址',
  `staking_block_num` bigint(20) NOT NULL COMMENT '质押区块高度',
  `staking_tx_index` int(11) NOT NULL COMMENT '发起质押交易的索引',
  `staking_hes` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '犹豫期的质押金(von)',
  `staking_locked` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '锁定期的质押金(von)',
  `staking_reduction` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '退回中的质押金(von)',
  `staking_reduction_epoch` int(11) NOT NULL DEFAULT '0' COMMENT '撤销质押时的结算周期轮数',
  `node_name` varchar(64) NOT NULL DEFAULT '' COMMENT '节点名称(质押节点名称)',
  `node_icon` varchar(255) DEFAULT '' COMMENT '节点头像(关联external_id，第三方软件获取)',
  `external_id` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方社交软件关联id',
  `external_name` varchar(128) DEFAULT NULL COMMENT '第三方社交软件关联用户名',
  `staking_addr` varchar(42) NOT NULL COMMENT '发起质押的账户地址',
  `benefit_addr` varchar(42) NOT NULL DEFAULT '' COMMENT '收益地址',
  `annualized_rate` double(16,2) NOT NULL DEFAULT '0.00' COMMENT '预计年化率',
  `program_version` varchar(10) NOT NULL DEFAULT '0' COMMENT '程序版本',
  `big_version` varchar(10) NOT NULL DEFAULT '0' COMMENT '大程序版本',
  `web_site` varchar(255) NOT NULL DEFAULT '' COMMENT '节点的第三方主页',
  `details` varchar(255) NOT NULL DEFAULT '' COMMENT '节点的描述',
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  `leave_time` timestamp NULL DEFAULT NULL COMMENT '离开时间',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '节点状态:\r\n1候选中,\r\n2退出中,\r\n3已退出',
  `is_consensus` int(2) NOT NULL DEFAULT '2' COMMENT '是否共识周期验证人:\r\n1是,\r\n2否',
  `is_settle` int(2) NOT NULL DEFAULT '2' COMMENT '是否结算周期验证人\r\n:1是,\r\n2否',
  `is_init` int(2) NOT NULL DEFAULT '2' COMMENT '是否为链初始化时内置的候选人:\r\n1是,\r\n2否',
  `stat_delegate_hes` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '未锁定的委托(von)',
  `stat_delegate_locked` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '锁定的委托(von)',
  `stat_delegate_released` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '待提取的委托(von)',
  `block_reward_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '出块奖励统计(激励池)(von)',
  `predict_staking_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '当前结算周期预计可获得的质押奖励',
  `staking_reward_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '质押奖励(激励池)(von)',
  `fee_reward_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '出块奖励统计(手续费)(von)',
  `cur_cons_block_qty` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前共识周期出块数',
  `pre_cons_block_qty` bigint(20) NOT NULL DEFAULT '0' COMMENT '上个共识周期出块数',
  `annualized_rate_info` longtext COMMENT '最近几个结算周期收益和质押信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `reward_per` int(11) NOT NULL COMMENT '委托奖励比例',
  `have_dele_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '节点当前质押已领取委托奖励',
  `pre_dele_annualized_rate` double(16,2) NOT NULL DEFAULT '0.00' COMMENT '前一参与周期预计委托收益率',
  `dele_annualized_rate` double(16,2) NOT NULL DEFAULT '0.00' COMMENT '预计委托收益率',
  `total_dele_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '节点当前质押总的委托奖励',
  `exception_status` int(2) NOT NULL DEFAULT '1' COMMENT '1正常,2低出块异常,3被双签,4因低出块被惩罚(例如在连续两个周期当选验证人，但在第一个周期出块率低),5因双签被惩罚',
  `un_stake_freeze_duration` int(11) NOT NULL COMMENT '解质押理论上锁定的结算周期数',
  `un_stake_end_block` bigint(20) DEFAULT NULL COMMENT '解质押冻结的最后一个区块：理论结束块与投票结束块中的最大者',
  PRIMARY KEY (`node_id`,`staking_block_num`),
  KEY `staking_addr` (`staking_addr`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for staking_history
-- ----------------------------
DROP TABLE IF EXISTS `staking_history`;
CREATE TABLE `staking_history` (
  `node_id` varchar(130) NOT NULL COMMENT '质押节点地址',
  `staking_block_num` bigint(20) NOT NULL COMMENT '质押区块高度',
  `staking_tx_index` int(11) NOT NULL COMMENT '发起质押交易的索引',
  `staking_hes` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '犹豫期的质押金(von)',
  `staking_locked` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '锁定期的质押金(von)',
  `staking_reduction` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '退回中的质押金(von)',
  `staking_reduction_epoch` int(11) NOT NULL DEFAULT '0' COMMENT '结算周期标识',
  `node_name` varchar(64) NOT NULL DEFAULT '' COMMENT '节点名称(质押节点名称)',
  `node_icon` varchar(255) DEFAULT '' COMMENT '节点头像(关联external_id，第三方软件获取)',
  `external_id` varchar(255) NOT NULL DEFAULT '' COMMENT '第三方社交软件关联id',
  `external_name` varchar(128) DEFAULT NULL COMMENT '第三方社交软件关联用户名',
  `staking_addr` varchar(42) NOT NULL COMMENT '发起质押的账户地址',
  `benefit_addr` varchar(42) NOT NULL DEFAULT '' COMMENT '收益地址',
  `annualized_rate` double(10,2) NOT NULL DEFAULT '0.00' COMMENT '预计年化率',
  `program_version` varchar(10) NOT NULL DEFAULT '0' COMMENT '程序版本',
  `big_version` varchar(10) NOT NULL DEFAULT '0' COMMENT '大程序版本',
  `web_site` varchar(255) NOT NULL DEFAULT '' COMMENT '节点的第三方主页',
  `details` varchar(255) NOT NULL DEFAULT '' COMMENT '节点的描述',
  `join_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '加入时间',
  `leave_time` timestamp NULL DEFAULT NULL COMMENT '离开时间',
  `status` int(2) NOT NULL DEFAULT '1' COMMENT '节点状态:\r\n1候选中,\r\n2退出中,\r\n3已退出',
  `is_consensus` int(2) NOT NULL DEFAULT '2' COMMENT '是否共识周期验证人:\r\n1是,\r\n2否',
  `is_settle` int(2) NOT NULL DEFAULT '2' COMMENT '是否结算周期验证人\r\n:1是,\r\n2否',
  `is_init` int(2) NOT NULL DEFAULT '2' COMMENT '是否为链初始化时内置的候选人:\r\n1是,\r\n2否',
  `stat_delegate_hes` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '未锁定的委托(von)',
  `stat_delegate_locked` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '锁定的委托(von)',
  `stat_delegate_released` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '待提取的委托(von)',
  `block_reward_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '出块奖励统计(激励池)(von)',
  `fee_reward_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '出块奖励统计(手续费)(von)',
  `staking_reward_value` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '质押奖励(激励池)(von)',
  `predict_staking_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '当前结算周期预计可获得的质押奖励',
  `cur_cons_block_qty` bigint(20) NOT NULL DEFAULT '0' COMMENT '当前共识周期出块数',
  `pre_cons_block_qty` bigint(20) NOT NULL DEFAULT '0' COMMENT '上个共识周期出块数',
  `annualized_rate_info` longtext COMMENT '最近几个结算周期收益和质押信息',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `reward_per` int(11) NOT NULL DEFAULT '0' COMMENT '委托奖励比例',
  `have_dele_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '已领取委托奖励(初始值等于前一条历史质押记录的【已领取委托奖励】)',
  `pre_dele_annualized_rate` double(16,2) DEFAULT '0.00' COMMENT '前一参与周期预计委托收益率',
  `dele_annualized_rate` double(16,2) NOT NULL DEFAULT '0.00' COMMENT '预计委托收益率',
  `total_dele_reward` decimal(65,0) NOT NULL DEFAULT '0' COMMENT '节点总的委托奖励(前一条历史质押记录的【节点总的委托奖励】+ 当前质押实时查询出来的奖励)',
  PRIMARY KEY (`node_id`,`staking_block_num`),
  KEY `staking_addr` (`staking_addr`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for tx_bak
-- ----------------------------
DROP TABLE IF EXISTS `tx_bak`;
CREATE TABLE `tx_bak` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `hash` varchar(66) NOT NULL COMMENT '交易Hash',
  `num` bigint(20) NOT NULL COMMENT '区块号',
  `info` text COMMENT '交易信息',
  PRIMARY KEY (`id`),
  KEY `block_number` (`num`) USING BTREE,
  KEY `id` (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for vote
-- ----------------------------
DROP TABLE IF EXISTS `vote`;
CREATE TABLE `vote` (
  `hash` varchar(66) NOT NULL COMMENT '投票交易Hash',
  `node_id` varchar(130) NOT NULL COMMENT '投票验证人(节点ID)',
  `node_name` varchar(128) NOT NULL COMMENT '投票验证人名称',
  `option` int(2) NOT NULL COMMENT '投票选项:1支持,2反对,3弃权',
  `proposal_hash` varchar(66) NOT NULL COMMENT '提案交易Hash',
  `timestamp` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '提案时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`hash`),
  KEY `verifier` (`node_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE INDEX address ON rp_plan (address);
CREATE INDEX proposal ON vote (proposal_hash,`timestamp`);
CREATE INDEX proposalOp ON vote (proposal_hash,`option`,`timestamp`);
CREATE INDEX staking ON delegation (is_history,`node_id`,`delegate_addr`,`sequence`,delegate_hes,delegate_locked,delegate_released);
CREATE INDEX home ON node (status,`is_consensus`,`big_version`,`total_value`,`staking_block_num`,`staking_tx_index`);