package com.platon.browser.enums;

import java.util.ArrayList;
import java.util.List;

import com.platon.browser.dto.CustomTransaction.TxTypeEnum;

public enum ReqTransactionTypeEnum {

	/** 
    0：转账  1：合约发布  2：合约调用    5：MPC交易
    *                                 1000: 发起质押  1001: 修改质押信息  1002: 增持质押  1003: 撤销质押 1004: 发起委托  1005: 减持/撤销委托
    *                                 2000: 提交文本提案 2001: 提交升级提案 2002: 提交参数提案 2003: 给提案投票 2004: 版本声明
    *                                 3000: 举报多签
    *                                 4000: 创建锁仓计划
    */
	TRANSACTION_TRANSFER("transfer","0","转账"),
    TRANSACTION_DELEGATE("delegate","1","委托"),
    TRANSACTION_STAKING("staking","2","验证人"),
    TRANSACTION_PROPOSAL("proposal","3","提案");
	private ReqTransactionTypeEnum(String name,String code,String description) {
		this.code = code;
		this.name = name;
		this.description = description;
	}
	
	private String name;
	
	private String code;
	
	private String description;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	public static List<String> getTxType(String typeName){
		List<String> list = new ArrayList<String>();
		if(ReqTransactionTypeEnum.TRANSACTION_TRANSFER.getName().equals(typeName)) {
			list.add(String.valueOf(TxTypeEnum.TRANSFER.getCode()));
			list.add(String.valueOf(TxTypeEnum.CONTRACT_CREATION.getCode()));
			list.add(String.valueOf(TxTypeEnum.CONTRACT_EXECUTION.getCode()));
			list.add(String.valueOf(TxTypeEnum.OTHERS.getCode()));
			list.add(String.valueOf(TxTypeEnum.MPC.getCode()));
			list.add(String.valueOf(TxTypeEnum.REPORT_VALIDATOR.getCode()));
			list.add(String.valueOf(TxTypeEnum.CREATE_RESTRICTING.getCode()));
			list.add(String.valueOf(TxTypeEnum.DUPLICATE_SIGN.getCode()));
		}
		if(ReqTransactionTypeEnum.TRANSACTION_DELEGATE.getName().equals(typeName)) {
			list.add(String.valueOf(TxTypeEnum.DELEGATE.getCode()));
			list.add(String.valueOf(TxTypeEnum.UN_DELEGATE.getCode()));
		}
		if(ReqTransactionTypeEnum.TRANSACTION_STAKING.getName().equals(typeName)) {
			list.add(String.valueOf(TxTypeEnum.CREATE_VALIDATOR.getCode()));
			list.add(String.valueOf(TxTypeEnum.EDIT_VALIDATOR.getCode()));
			list.add(String.valueOf(TxTypeEnum.INCREASE_STAKING.getCode()));
			list.add(String.valueOf(TxTypeEnum.EXIT_VALIDATOR.getCode()));
		}
		if(ReqTransactionTypeEnum.TRANSACTION_PROPOSAL.getName().equals(typeName)) {
			list.add(String.valueOf(TxTypeEnum.CREATE_PROPOSAL_TEXT.getCode()));
			list.add(String.valueOf(TxTypeEnum.CREATE_PROPOSAL_UPGRADE.getCode()));
			list.add(String.valueOf(TxTypeEnum.CREATE_PROPOSAL_PARAMETER.getCode()));
			list.add(String.valueOf(TxTypeEnum.CANCEL_PROPOSAL.getCode()));
			list.add(String.valueOf(TxTypeEnum.VOTING_PROPOSAL.getCode()));
			list.add(String.valueOf(TxTypeEnum.DECLARE_VERSION.getCode()));
		}
		return list;
		
	}
}
