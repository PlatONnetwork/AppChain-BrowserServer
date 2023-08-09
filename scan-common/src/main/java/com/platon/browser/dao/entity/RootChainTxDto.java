package com.platon.browser.dao.entity;

import lombok.Data;

@Data
public class RootChainTxDto {
    private Long id;
    private Long rootChainBlockNumber;
    private String rootChainTxHash;
    private Integer rootChainTxIndex;
    private String txHash;
    private Long blockNumber;
    private String txType;
    private String txParamInfo;
}
