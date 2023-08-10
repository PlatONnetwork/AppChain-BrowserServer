package com.platon.browser.service;

import com.platon.browser.AgentApplication;
import com.platon.browser.bean.Receipt;
import com.platon.browser.bean.ReceiptResult;
import com.platon.browser.client.SpecialApi;
import com.platon.browser.client.Web3jWrapper;
import com.platon.contracts.ppos.dto.resp.Node;
import com.platon.protocol.Web3j;
import com.platon.protocol.Web3jService;
import com.platon.protocol.http.HttpService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.List;

@Slf4j
@SpringBootTest(classes = { AgentApplication.class })
@ActiveProfiles("hskchain")
public class SpecialApiTest {

    @Resource
    SpecialApi specialApi;

    @SneakyThrows
    @Test
    public void testGetGoven() {

        Web3jService web3jService =  new HttpService("http://8.219.49.57:6789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://8.219.49.57:6789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        List<Node> nodeList = specialApi.getHistoryVerifierList(web3jWrapper, new BigInteger("473000"));

        System.out.println("nodeList:" + nodeList.size());
    }

    @SneakyThrows
    @Test
    public void testGetHistoryVerifierList() {

        Web3jService web3jService =  new HttpService("http://192.168.9.139:7789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.9.139:7789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        List<Node> nodeList = specialApi.getHistoryVerifierList(web3jWrapper, new BigInteger("0"));

        System.out.println("nodeList:" + nodeList.size());
    }



    @SneakyThrows
    @Test
    public void testGetHistoryValidatorist() {

        Web3jService web3jService =  new HttpService("http://192.168.9.139:7789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.9.139:7789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        List<Node> nodeList = specialApi.getHistoryValidatorList(web3jWrapper, new BigInteger("0"));

        System.out.println("nodeList:" + nodeList.size());
    }

    @SneakyThrows
    @Test
    public void testGetReceiptResult() {

        Web3jService web3jService =  new HttpService("http://192.168.9.139:7789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.9.139:7789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        ReceiptResult receiptResult = specialApi.getReceiptResult(web3jWrapper, new BigInteger("2170"));
        Receipt receipt = receiptResult.getMap().get("0xc592d5e17affc84eef16bdf3b18ce84a9fb45efb03f1ba4685ba5b41649e1a94");


        System.out.println("receipt.getStatus():" + receipt.getStatus() + "   receipt.getLogStatus():" + receipt.getLogStatus());
    }
}
