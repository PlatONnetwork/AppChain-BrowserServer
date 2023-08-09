package com.platon.browser.decoder;


import com.platon.rlp.solidity.RlpString;
import org.apache.commons.codec.DecoderException;

import java.math.BigInteger;

public abstract class RootChainEvent {
    //todo: 这个topicHash哪里来的？
    private final static String StakeEventTopicHash = "0x42aca3a8d2d8ba37d07af1816267145b860bb56fd509c7d123053e57951feda5";
    private final static String UnstakeInitEventTopicHash = "0x69b288bb79cd5386c9fe0af060f650e823bcdfa96a44cdc07f862db060f57120";
    private final static String StakeUpdateEventTopicHash = "0x35af9eea1f0e7b300b0a14fae90139a072470e44daa3f14b5069bebbc1265bda";

    String address; //0x prefix
    String topic; //0x prefix  区块链中，topic可以有多个，不过在hashkey中，对收到的platon上的event，如果需要发送一个交易到内置合约上，则只会设置一个预定的topic，以便好甄别是什么event，做什么操作
    String data; //no 0x prefix

    abstract void parseData() throws DecoderException;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static BigInteger toBigInteger(RlpString value){
        return new BigInteger(1, value.getBytes());
    }
    public static long toLong(RlpString value){
        return new BigInteger(1, value.getBytes()).longValue();
    }

    public static byte[] toBytes(RlpString value) {
        return value.getBytes();
    }
    public static String toString(RlpString value) {
        return value.asString();
    }
}
