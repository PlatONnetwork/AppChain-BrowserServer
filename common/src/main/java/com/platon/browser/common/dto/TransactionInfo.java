package com.platon.browser.common.dto;

import lombok.Data;

@Data
public class TransactionInfo {
    private String txHash;

    private String from;

    private String to;

    private double value;

    private int blockHeight;

    private int transactionIndex;

    private long timestamp;
}
