package com.platon.browser.abi;

import com.alibaba.fastjson.JSON;
import com.platon.browser.abi.decoder.AbiDecoder;
import com.platon.browser.abi.decoder.DecodedFunctionCall;
import com.platon.browser.abi.util.HashUtil;
import com.platon.crypto.Hash;
import com.platon.rlp.solidity.RlpDecoder;
import com.platon.rlp.solidity.RlpList;
import com.platon.rlp.solidity.RlpString;
import com.platon.utils.ByteUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Slf4j
public class AbiDecoderTest {

    @Test
    public void testEventSignature_topicHash(){
        String event = "UnstakeInit(address,uint256,uint256,uint256,uint256)";

        System.out.println("topicHash.Keccak:" + ByteUtil.toHexString(HashUtil.hashAsKeccak(event.getBytes(StandardCharsets.UTF_8))));
        System.out.println("topicHash.sha3:" + Hash.sha3(event));
    }


    @Test
    public void testStaking() throws IOException {
        // Abi can be found here: https://etherscan.io/address/0x7a250d5630b4cf539739df2c5dacb4c659f2488d#code
        AbiDecoder innerSimulatedStakingAbi = new AbiDecoder(this.getClass().getResource("/contract/InnerSimulatedStakingAbi.json").getFile());
        AbiDecoder rootChainStakingAbiAbi = new AbiDecoder(this.getClass().getResource("/contract/RootChainStakingAbi.json").getFile());

        String inputData = "0xbb02fc890000000000000000000000000000000000000000000000000000000001b501bf00000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000000000000000000000000000000000000000";

        DecodedFunctionCall decodedFunctionCall = innerSimulatedStakingAbi.decodeFunctionCall(inputData);

        System.out.println("method name:" + decodedFunctionCall.getName());

        Collection<DecodedFunctionCall.Param> params = decodedFunctionCall.getParams();

        for(DecodedFunctionCall.Param param : params) {
            System.out.println("param::" + JSON.toJSONString(param));
        }
    }


    @Test
    public void testRootStakingEvent() throws IOException, DecoderException {
                /*
        logPlain := &Log{
		Address:     common.MustBech32ToAddress("lat1anu0sluppm852z2qe8mqqe4557jsr448jq8h34"), //0xecf8f87f810ecf450940c9f60066b4a7a501d6a7
		BlockHash:   common.HexToHash("0x656c34545f90a730a19008c0e7a7cd4fb3895064b48d6d69761bd5abad681056"),
		BlockNumber: 2019236,
		Data:        hexutil.MustDecode("0x000000000000000000000000000000000000000000000001a055690d9db80000"),
		Index:       2,
		TxIndex:     3,
		TxHash:      common.HexToHash("0x3b198bfd5d2907285af009e9ae84a0ecd63677110d89d7e030251acb87f6487e"),
		Topics: []common.Hash{
			common.HexToHash("0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef"),
			common.HexToHash("0x00000000000000000000000080b2c9d7cbbf30a1b0fc8983c647d754c6525615"),
		},
	}
	buffer := new(bytes.Buffer)
	logPlain.EncodeRLP(buffer)
         */

        // Abi can be found here: https://etherscan.io/address/0x7a250d5630b4cf539739df2c5dacb4c659f2488d#code
        AbiDecoder innerSimulatedStakingAbi = new AbiDecoder(this.getClass().getResource("/contract/InnerSimulatedStakingAbi.json").getFile());
        AbiDecoder rootChainStakingAbiAbi = new AbiDecoder(this.getClass().getResource("/contract/RootChainStakingAbi.json").getFile());


        String rlpLog = "f87a94ecf8f87f810ecf450940c9f60066b4a7a501d6a7f842a0ddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3efa000000000000000000000000080b2c9d7cbbf30a1b0fc8983c647d754c6525615a0000000000000000000000000000000000000000000000001a055690d9db80000";

        byte[] rlpLogBytes = Hex.decodeHex(rlpLog);
        RlpList logInfoList = RlpDecoder.decode(rlpLogBytes);
        RlpList logInfo = (RlpList)logInfoList.getValues().get(0);

        RlpString address =  (RlpString)(logInfo.getValues().get(0));
        RlpList topics =  (RlpList)(logInfo.getValues().get(1));
        RlpString data =  (RlpString)(logInfo.getValues().get(2));

        System.out.println("address:" +address.asString());
        for(int i=0; i<topics.getValues().size(); i++){
            RlpString topic = (RlpString)topics.getValues().get(i);
            System.out.println("topic, idx:" + i + " topic:" + topic.asString());
        }
        System.out.println("data:" +  Hex.encodeHexString(data.getBytes()));
    }

    @Test
    public void testDecodeFunctionCallUniswapV2Router02() throws IOException {

        // Abi can be found here: https://etherscan.io/address/0x7a250d5630b4cf539739df2c5dacb4c659f2488d#code
        AbiDecoder uniswapv2Abi = new AbiDecoder(this.getClass().getResource("/abiFiles/UniswapV2Router02.json").getFile());

        // tx: https://etherscan.io/tx/0xde2b61c91842494ac208e25a2a64d99997c382f6aaf0719d6a719b5cff1f8a07
        String inputData = "0x18cbafe5000000000000000000000000000000000000000000000000000000000098968000000000000000000000000000000000000000000000000000165284993ac4ac00000000000000000000000000000000000000000000000000000000000000a0000000000000000000000000d4cf8e47beac55b42ae58991785fa326d9384bd10000000000000000000000000000000000000000000000000000000062e8d8510000000000000000000000000000000000000000000000000000000000000002000000000000000000000000a0b86991c6218b36c1d19d4a2e9eb0ce3606eb48000000000000000000000000c02aaa39b223fe8d0a0e5c4f27ead9083c756cc2";

        /**
         * #	Name	      Type	     Data
         * 0	amountIn	  uint256	 10000000
         * 1	amountOutMin  uint256	 6283178947560620
         * 2	path	      address[]	 0xA0b86991c6218b36c1d19D4a2e9Eb0cE3606eB48
         *                               0xC02aaA39b223FE8D0A0e5C4F27eAD9083C756Cc2
         * 3	to	          address	 0xD4CF8e47BeAC55b42Ae58991785Fa326d9384Bd1
         * 4	deadline	uint256	1659426897
         */

        DecodedFunctionCall decodedFunctionCall = uniswapv2Abi.decodeFunctionCall(inputData);

        Assertions.assertEquals("swapExactTokensForETH", decodedFunctionCall.getName());

        List<DecodedFunctionCall.Param> paramList = decodedFunctionCall.getParamList();

        DecodedFunctionCall.Param param0 = paramList.get(0);
        Assertions.assertEquals("amountIn", param0.getName());
        Assertions.assertEquals("uint256", param0.getType());
        Assertions.assertEquals(BigInteger.valueOf(10000000), param0.getValue());

        DecodedFunctionCall.Param param1 = paramList.get(1);
        Assertions.assertEquals("amountOutMin", param1.getName());
        Assertions.assertEquals("uint256", param1.getType());
        Assertions.assertEquals(new BigInteger("6283178947560620"), param1.getValue());

        DecodedFunctionCall.Param param2 = paramList.get(2);
        Assertions.assertEquals("path", param2.getName());
        Assertions.assertEquals("address[]", param2.getType());
        Assertions.assertEquals(Arrays.toString(new String[]{"0xa0b86991c6218b36c1d19d4a2e9eb0ce3606eb48", "0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2"}), Arrays.toString((Object[]) param2.getValue()));

        DecodedFunctionCall.Param param3 = paramList.get(3);
        Assertions.assertEquals("to", param3.getName());
        Assertions.assertEquals("address", param3.getType());
        Assertions.assertEquals("0xd4cf8e47beac55b42ae58991785fa326d9384bd1", param3.getValue());

        DecodedFunctionCall.Param param4 = paramList.get(4);
        Assertions.assertEquals("deadline", param4.getName());
        Assertions.assertEquals("uint256", param4.getType());
        Assertions.assertEquals(BigInteger.valueOf(1659426897), param4.getValue());
    }

    @Test
    public void testDecodeFunctionCallWithTuple() throws IOException {
        AbiDecoder uniswapv3Abi = new AbiDecoder(this.getClass().getResource("/abiFiles/UniswapV3SwapRouter02.json").getFile());

        String inputData = "0x04e45aaf000000000000000000000000c02aaa39b223fe8d0a0e5c4f27ead9083c756cc20000000000000000000000002260fac5e5542a773aa44fbcfedf7c193bc2c59900000000000000000000000000000000000000000000000000000000000001f4000000000000000000000000bebc44782c7db0a1a60cb6fe97d0b483032ff1c70000000000000000000000000000000000000000000000000000000000067932000000000000000000000000000000000000000000000000000000000000002a0000000000000000000000000000000000000000000000000000000000000000";

        DecodedFunctionCall decodedFunctionCall = uniswapv3Abi.decodeFunctionCall(inputData);

        Assertions.assertEquals("exactInputSingle", decodedFunctionCall.getName());


        DecodedFunctionCall.Param param0 = decodedFunctionCall.getParams().stream().findFirst().get();
        Assertions.assertEquals("params", param0.getName());
        Assertions.assertEquals("tuple", param0.getType());
        Object[] v = (Object[]) param0.getValue();
        Assertions.assertEquals("0xc02aaa39b223fe8d0a0e5c4f27ead9083c756cc2", v[0].toString());
        Assertions.assertEquals("0x2260fac5e5542a773aa44fbcfedf7c193bc2c599", v[1].toString());
        Assertions.assertEquals("500", v[2].toString());
        Assertions.assertEquals("424242", v[4].toString());
        Assertions.assertEquals("42", v[5].toString());
    }


    @Test
    public void testDecodeFunctionCallUniswapV3SwapRouter02() throws IOException {


        AbiDecoder uniswapv3SwapRouter02Abi = new AbiDecoder(this.getClass().getResource("/abiFiles/UniswapV3SwapRouter02.json").getFile());

        // https://etherscan.io/tx/0x731847de5b19b26039f283826ae5218ac7e070ed1b7fff689c2253a3035d8bd6
        String inputData = "0x5ae401dc0000000000000000000000000000000000000000000000000000000062ed6b0d000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000016000000000000000000000000000000000000000000000000000000000000000e4472b43f3000000000000000000000000000000000000000000000000000008c75ee6fb3900000000000000000000000000000000000000000000000001cb1a1493ed3d4b0000000000000000000000000000000000000000000000000000000000000080000000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000020000000000000000000000009bbe10ba8ad02c2a54963b3e2a64f1754c90f411000000000000000000000000c02aaa39b223fe8d0a0e5c4f27ead9083c756cc200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004449404b7c00000000000000000000000000000000000000000000000001cb1a1493ed3d4b000000000000000000000000c0da58d88e967d883ef0540db458381e9f5e9c8000000000000000000000000000000000000000000000000000000000";
        List<DecodedFunctionCall> decodedFunctionCalls = uniswapv3SwapRouter02Abi.decodeFunctionsCalls(inputData);


        int i = 0;
        for (DecodedFunctionCall func : decodedFunctionCalls) {
            log.debug("{}: function: {}", i, func.getName());
            int p = 0;
            for (DecodedFunctionCall.Param param : func.getParams()) {
                log.debug("param {}: name={}, type={}, value={}", p, param.getName(), param.getType(), param.getValue());
                p++;
            }
            i++;
            log.debug("-------------------------");
        }
    }

    @Test
    public void testDecodeFunctionCallUniswapV3SwapRouter02Swap() throws IOException {


        AbiDecoder uniswapv3SwapRouter02Abi = new AbiDecoder(this.getClass().getResource("/abiFiles/UniswapV3SwapRouter02.json").getFile());

        // https://etherscan.io/tx/0xb8ea1e889a4b7bfd1a51359801645518e2f648522c6662c4dd2939a5d9fe6ff4
        String inputData = "0x5ae401dc0000000000000000000000000000000000000000000000000000000062fba2e2000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000040000000000000000000000000000000000000000000000000000000000000016000000000000000000000000000000000000000000000000000000000000000e4472b43f3000000000000000000000000000000000000000003a5efc474214c296d933ea100000000000000000000000000000000000000000000000000d5b866fcc68674000000000000000000000000000000000000000000000000000000000000008000000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000002000000000000000000000000d041e4427412ede81ccac87f08fa3490af61033d000000000000000000000000c02aaa39b223fe8d0a0e5c4f27ead9083c756cc200000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000004449404b7c00000000000000000000000000000000000000000000000000d5b866fcc68674000000000000000000000000caed7876d89f4f67808e0301a1dfb218d73f569000000000000000000000000000000000000000000000000000000000";
        List<DecodedFunctionCall> decodedFunctionCalls = uniswapv3SwapRouter02Abi.decodeFunctionsCalls(inputData);


        int i = 0;
        for (DecodedFunctionCall func : decodedFunctionCalls) {
            log.debug("{}: function: {}", i, func.getName());
            int p = 0;
            for (DecodedFunctionCall.Param param : func.getParams()) {
                log.debug("param {}: name={}, type={}, value={}", p, param.getName(), param.getType(), param.getValue());
                p++;
            }
            i++;
            log.debug("-------------------------");
        }
    }

}
