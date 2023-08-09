package com.platon.browser.decoder;

import com.platon.rlp.solidity.RlpDecoder;
import com.platon.rlp.solidity.RlpList;
import com.platon.rlp.solidity.RlpString;
import org.apache.commons.codec.DecoderException;

public class RootChainEventFactory {
    //topicHash, 标识某个event的类型，是有abi.json中，根据event的说明来的，举例：
    /**
     *  String event = "UnstakeInit(address,uint256,uint256,uint256,uint256)";
     *
     *  ByteUtil.toHexString(HashUtil.hashAsKeccak(event.getBytes(StandardCharsets.UTF_8))))
     *  将得到：
     *  69b288bb79cd5386c9fe0af060f650e823bcdfa96a44cdc07f862db060f57120
     *
     */
    private final static String StakeEventTopicHash = "0x42aca3a8d2d8ba37d07af1816267145b860bb56fd509c7d123053e57951feda5";
    private final static String UnstakeInitEventTopicHash = "0x69b288bb79cd5386c9fe0af060f650e823bcdfa96a44cdc07f862db060f57120";
    private final static String StakeUpdateEventTopicHash = "0x35af9eea1f0e7b300b0a14fae90139a072470e44daa3f14b5069bebbc1265bda";


    public static RootChainEvent decodeRootChainEvent(String txInput) throws DecoderException {
        if (txInput.startsWith("0x")){
            txInput = txInput.substring(2);
        }
        byte[] rlpedLogBytes = new byte[0];
        try {
            rlpedLogBytes = org.apache.commons.codec.binary.Hex.decodeHex(txInput);
        } catch (DecoderException e) {
            throw new RuntimeException(e);
        }

        RlpList logInfoList = RlpDecoder.decode(rlpedLogBytes);
        RlpList logInfo = (RlpList)logInfoList.getValues().get(0);

        RlpString address =  (RlpString)(logInfo.getValues().get(0));
        RlpList topics =  (RlpList)(logInfo.getValues().get(1));
        RlpString data =  (RlpString)(logInfo.getValues().get(2));

        if (topics.getValues().size()>0){
          return createEvent(address.asString(), ((RlpString)topics.getValues().get(0)).asString(), data.asString());
        }else{
            throw new DecoderException("unknown log topic");
        }
    }

    public static RootChainEvent createEvent(String address, String topic, String data) throws DecoderException {
        RootChainEvent event;
        switch (topic){
            case StakeEventTopicHash:
                event = new StakedEvent();
                event.setAddress(address);
                event.setTopic(topic);
                event.setData(data);
                event.parseData();
                return event;
            case UnstakeInitEventTopicHash:
                event = new UnstakeInitEvent();
                event.parseData();
                return event;
            case StakeUpdateEventTopicHash:
                event = new StakeUpdateEvent();
                event.parseData();
                return event;
            default:
                return null;
        }
    }



}
