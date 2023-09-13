package com.platon.browser.bean.appchain;

import com.alibaba.fastjson.JSON;
import com.platon.utils.JSONUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Slf4j
public class RootChainTxTest {

    public static void main2(String[] args){
        String rootTxJson ="{\"txType\":\"Stake\",\"txHash\":\"0x0100000000000000000000000000000000000000000000000000000000000000\",\"txParam\":{\"amount\":\"0x1\",\"nodeId\":\"02000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\",\"stakingAddress\":\"0x0100000000000000000000000000000000000000\",\"validatorId\":\"0x3e3aeb4ae1383562f4b82261d969f7ac94ca4000000000000000\"},\"rootChainBlockNumber\":100,\"rootChainTxHash\":\"0x0102030400000000000000000000000000000000000000000000000000000000\",\"rootChainTxIndex\":39121}";

        RootChainTx tx = JSONUtil.parseObject(rootTxJson, RootChainTx.class);
        //Object txParam = tx.parseTxParamByTxType();
        log.debug(tx.parseTxParamByTxType().getClass().getSimpleName());

        List<RootChainTx> txList = new ArrayList<>();
        txList.add(tx);
        txList.add(tx);

        String json = JSON.toJSONString(txList);

        log.debug("txList json:{}", json);
        List<RootChainTx> txObjList = JSON.parseArray(json, RootChainTx.class);
        for(RootChainTx tx1 : txObjList){
            //Object txParamObj = tx1.parseTxParamByTxType();
            log.debug(tx.parseTxParamByTxType().getClass().getSimpleName());
        }
    }


    public static void main1(String[] args){
        String rootTxJson ="[{\"txType\":0,\"txHash\":\"0x0100000000000000000000000000000000000000000000000000000000000000\",\"txParam\":{\"amount\":\"0x1\",\"nodeId\":\"02000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000\",\"stakingAddress\":\"0x0100000000000000000000000000000000000000\",\"validatorId\":\"0x3e3aeb4ae1383562f4b82261d969f7ac94ca4000000000000000\"},\"rootChainBlockNumber\":100,\"rootChainTxHash\":\"0x0102030400000000000000000000000000000000000000000000000000000000\",\"rootChainTxIndex\":39121},{\"txType\":1,\"txHash\":\"0x0100000000000000000000000000000000000000000000000000000000000000\",\"txParam\":{\"validatorId\":\"0x3e3aeb4ae1383562f4b82261d969f7ac94ca4000000000000000\"},\"rootChainBlockNumber\":100,\"rootChainTxHash\":\"0x0102030400000000000000000000000000000000000000000000000000000000\",\"rootChainTxIndex\":39121},{\"txType\":1,\"txHash\":\"0x0100000000000000000000000000000000000000000000000000000000000000\",\"txParam\":{\"validatorId\":\"0x3e3aeb4ae1383562f4b82261d969f7ac94ca4000000000000000\"},\"rootChainBlockNumber\":100,\"rootChainTxHash\":\"0x0102030400000000000000000000000000000000000000000000000000000000\",\"rootChainTxIndex\":39121}]";

        List<RootChainTx> txList = JSON.parseArray(rootTxJson, RootChainTx.class);
        for(RootChainTx tx : txList){
            //RootChainTxParam txParam = tx.parseTxParamByTxType();
            log.debug(tx.parseTxParamByTxType().getClass().getSimpleName());
        }
    }

}
