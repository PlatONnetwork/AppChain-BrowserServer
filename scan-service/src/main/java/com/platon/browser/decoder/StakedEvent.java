package com.platon.browser.decoder;

import com.platon.rlp.solidity.RlpDecoder;
import com.platon.rlp.solidity.RlpList;
import com.platon.rlp.solidity.RlpString;
import lombok.Data;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;

import java.math.BigInteger;

@Data
public class StakedEvent extends RootChainEvent {
    //event Staked(address indexed signer, address owner, uint256 indexed validatorId, uint256 nonce, uint256 indexed activationEpoch, uint256 amount, uint256 total, bytes pubkeys)
    private String signer;
    private String owner;
    private BigInteger validatorId;
    private Long nonce;
    private Long activationEpoch;
    private BigInteger amount;
    private BigInteger total;
    private byte[] pubkeys;


    public void parseData() throws DecoderException {
        RlpList eventInput = RlpDecoder.decode(Hex.decodeHex(this.data));
        this.signer = ((RlpString)eventInput.getValues().get(0)).toString();
        this.owner = ((RlpString)(RlpString)eventInput.getValues().get(1)).asString();
        this.validatorId = toBigInteger((RlpString)eventInput.getValues().get(2));
        this.nonce = toLong((RlpString)eventInput.getValues().get(3));
        this.activationEpoch = toLong((RlpString)eventInput.getValues().get(4));
        this.amount = toBigInteger((RlpString)eventInput.getValues().get(5));
        this.total = toBigInteger((RlpString)eventInput.getValues().get(6));
        this.pubkeys = toBytes((RlpString)eventInput.getValues().get(7));
    }
}
