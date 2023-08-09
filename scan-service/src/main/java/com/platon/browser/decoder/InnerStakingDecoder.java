package com.platon.browser.decoder;

// hashkey chain监控platon链上事件，如果是stake相关事件，则把事件封装成交易参数，发送到hashchain的内置合约staking_contract上。
// hashkey 监控platon链上每个块的相关事件（stake事件），并把这些事件作为作为交易参数的一部分，发送给hashkey的内置合约。
// 参考：InnerStakingAbi.json，知道内置合约有两个合约方法：
// 1. stakeStateSync(uint256, bytes[])，输入参数是：platon链上区块高度，收到的platon链上事件
// 参数结构，参考：hashkey chain链代码：core/vm/staking_contract.go:279
// type InputArgs struct {
//		BlockNumber *big.Int       //platon的区块高度
//		Events      [][]byte       //platon这个区块高度上，发生的相关事件。
//	}
//  在hashkey，整个交易
//
//2. blockNumber() uint264, 无参数, 返回hashkey的区块高度

import com.platon.browser.abi.decoder.AbiDecoder;
import com.platon.browser.abi.decoder.DecodedFunctionCall;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

//首先利用abi的编解码规则，对input的进行解码，如果是方法blockNumber(),则略过不处理；如果是stakeStateSync方法，则进一步解析logs
@Slf4j
public class InnerStakingDecoder {
    static AbiDecoder innerStakingAbiDecoder;
    {
        try {
            innerStakingAbiDecoder = new AbiDecoder(this.getClass().getResource("/contract/InnerSimulatedStakingAbi.json").getFile());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //解析出方法
    public static DecodedFunctionCall decodeFunctionName(String inputHex) {
        if (innerStakingAbiDecoder == null) {
            throw new RuntimeException("cannot load inner staking contract ABI");
        }
        DecodedFunctionCall decodedFunctionCall = innerStakingAbiDecoder.decodeFunctionCall(inputHex);
        return decodedFunctionCall;
    }



}
