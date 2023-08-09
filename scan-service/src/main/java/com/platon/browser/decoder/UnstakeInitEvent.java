package com.platon.browser.decoder;

import com.platon.rlp.solidity.RlpDecoder;
import com.platon.rlp.solidity.RlpList;
import com.platon.rlp.solidity.RlpString;
import lombok.Data;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

@Data
public class UnstakeInitEvent extends RootChainEvent{
    //event UnstakeInit(address indexed user, uint256 indexed validatorId, uint256 nonce, uint256 deactivationEpoch, uint256 indexed amount)
    private String user;
    private BigInteger validatorId;
    private Long nonce;
    private Long activationEpoch;
    private BigInteger amount;

    public void parseData() throws DecoderException {
        RlpList eventInput = RlpDecoder.decode(Hex.decodeHex(this.data));

        this.user = ((RlpString)eventInput.getValues().get(0)).asString();
        this.validatorId = toBigInteger((RlpString)eventInput.getValues().get(1));
        this.nonce =  toLong((RlpString)eventInput.getValues().get(2));
        this.activationEpoch = toLong((RlpString)eventInput.getValues().get(3));
        this.amount = toBigInteger((RlpString)eventInput.getValues().get(4));
    }
}
