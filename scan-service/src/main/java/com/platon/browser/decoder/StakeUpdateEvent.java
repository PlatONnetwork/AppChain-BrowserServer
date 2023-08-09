package com.platon.browser.decoder;

import com.platon.rlp.solidity.RlpDecoder;
import com.platon.rlp.solidity.RlpList;
import com.platon.rlp.solidity.RlpString;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

public class StakeUpdateEvent extends RootChainEvent{
    //event StakeUpdate(uint256 indexed validatorId, uint256 indexed nonce, uint256 indexed newAmount)
    private BigInteger validatorId;
    private Long nonce;
    private BigInteger amount;

    public void parseData() throws DecoderException {
        RlpList eventInput = RlpDecoder.decode(Hex.decodeHex(this.data));

        this.validatorId = toBigInteger((RlpString)eventInput.getValues().get(0));
        this.nonce = toLong((RlpString)eventInput.getValues().get(1));
        this.amount =  toBigInteger((RlpString)eventInput.getValues().get(2));
    }
}
