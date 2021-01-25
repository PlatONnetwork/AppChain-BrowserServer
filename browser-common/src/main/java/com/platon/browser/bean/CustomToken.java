package com.platon.browser.bean;

import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

@Data
public class CustomToken {
    private String address;

    private String type;

    private String name;

    private String symbol;

    private BigDecimal totalSupply;

    private Integer decimal;

    private String webSite;

    private String details;

    private Date createTime;

    private Integer tokenTxQty;

    private Integer holder;

}