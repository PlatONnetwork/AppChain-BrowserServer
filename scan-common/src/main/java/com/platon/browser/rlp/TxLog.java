package com.platon.browser.rlp;

import lombok.Data;

@Data
public class TxLog {
    private byte[] address;
    private byte[][] topics;
    private byte[] data;
}
