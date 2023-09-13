package com.platon.browser.service;

import com.alibaba.fastjson.JSON;
import com.platon.browser.AgentApplication;
import com.platon.browser.bean.*;
import com.platon.browser.client.SpecialApi;
import com.platon.browser.client.Web3jWrapper;
import com.platon.browser.utils.ChainVersionUtil;
import com.platon.contracts.ppos.dto.resp.Node;
import com.platon.protocol.Web3j;
import com.platon.protocol.Web3jService;
import com.platon.protocol.core.DefaultBlockParameter;
import com.platon.protocol.core.DefaultBlockParameterName;
import com.platon.protocol.http.HttpService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

@Slf4j
@SpringBootTest(classes = { AgentApplication.class })
@ActiveProfiles("appchain")
public class SpecialApiTest {

    @Resource
    SpecialApi specialApi;


    @SneakyThrows
    @Test
    public void test_getNodeVersion() {
        Web3jService web3jService =  new HttpService("http://192.168.16.189:6789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.16.189:6789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        List<NodeVersion> nodeVersionList = specialApi.getNodeVersionList(web3jWrapper);

        for (NodeVersion nodeVersion: nodeVersionList ) {
            System.out.println("node Id:" + nodeVersion.getNodeId() + ",  Version:" + ChainVersionUtil.toStringVersion(new BigInteger(String.valueOf(nodeVersion.getProgramVersion()))));
        }
    }

    @SneakyThrows
    @Test
    public void test_getProposalParticipants() {
        Web3jService web3jService =  new HttpService("http://192.168.16.189:6789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.16.189:6789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        ProposalParticipantStat proposalParticipants = specialApi.getProposalParticipants(web3jWrapper, "0xe193676cc5e0c8f057622cf70bb7ebce193d24a3e5b566ba41ea045c8c6157e5", "0x8a14572d06fd460c48c28aaef4d05c797961f5f658887079ef75e4e6b34d40b8");

        System.out.println("genesis block verifier:" + JSON.toJSONString(proposalParticipants));
    }

    @SneakyThrows
    @Test
    public void test_getAccountView() {
        Web3jService web3jService =  new HttpService("http://192.168.16.189:6789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.16.189:6789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        List<RestrictingBalance> restrictingBalanceList = specialApi.getRestrictingBalance(web3jWrapper, new String[]{"lat104hswfpm8qderhk0r8qmthqj0lxekm3sda29pk", "lat106485qeyt3rtvc32czxlgnrws3tfkswwu37rd7"}, 1381380L);

        for (RestrictingBalance restrictingBalance: restrictingBalanceList ) {
            System.out.println("restrictingBalance:" + JSON.toJSONString(restrictingBalance));
        }
    }

    @SneakyThrows
    @Test
    public void test_getSlashInfoByBlockNumber() {
        Web3jService web3jService =  new HttpService("http://192.168.16.189:6789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.16.189:6789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        List<HistoryLowRateSlash> historyLowRateSlashList = specialApi.getHistoryLowRateSlashList(web3jWrapper, new BigInteger("3746381"));

        for (HistoryLowRateSlash slash: historyLowRateSlashList ) {
            System.out.println("HistoryLowRateSlash:" + JSON.toJSONString(slash));
        }
    }

    @SneakyThrows
    @Test
    public void test_getEpochInfo() {

        Web3jService web3jService =  new HttpService("http://192.168.16.189:6789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.16.189:6789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        EpochInfo epochInfo = specialApi.getEpochInfo(web3jWrapper, new BigInteger("3746381"));

        System.out.println("epochInfo:" + JSON.toJSONString(epochInfo));
    }

    @SneakyThrows
    @Test
    public void test_getReceiptExtsByBlockNumber() {
        Web3jService web3jService =  new HttpService("http://192.168.16.189:6789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.16.189:6789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        ReceiptResult receiptResult = specialApi.getReceiptResult(web3jWrapper, new BigInteger("3746381"));

        System.out.println("receiptResult:" + JSON.toJSONString(receiptResult));

        for (Receipt receipt: receiptResult.getResult() ) {
            System.out.println("receiptList:" + JSON.toJSONString(receipt));
        }
    }


    @SneakyThrows
    @Test
    public void test_getVerifiersByBlockNumber() {
        Web3jService web3jService =  new HttpService("http://192.168.16.189:6789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.16.189:6789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        List<Node> nodeList = specialApi.getHistoryVerifierList(web3jWrapper, new BigInteger("0"));

        for (Node node: nodeList ) {
            System.out.println("genesis block verifier:" + JSON.toJSONString(node));
        }

        nodeList = specialApi.getHistoryVerifierList(web3jWrapper, new BigInteger("3746381"));

        for (Node node: nodeList ) {
            System.out.println("running verifier:" + JSON.toJSONString(node));
        }
    }




    @SneakyThrows
    @Test
    public void test_getValidatorsByBlockNumber() {
        Web3jService web3jService =  new HttpService("http://192.168.16.189:6789");
        Web3jWrapper web3jWrapper = Web3jWrapper.builder().address("http://192.168.16.189:6789").web3jService(web3jService).web3j(Web3j.build(web3jService)).build();


        List<Node> nodeList = specialApi.getHistoryValidatorList(web3jWrapper, new BigInteger("0"));

        for (Node node: nodeList ) {
            System.out.println("genesis block validator:" + JSON.toJSONString(node));
        }

        nodeList = specialApi.getHistoryValidatorList(web3jWrapper, new BigInteger("3746381"));

        for (Node node: nodeList ) {
            System.out.println("running validator:" + JSON.toJSONString(node));
        }
    }

    @Test
    public void testJson(){
        String data = "[{\"TxHash\":\"0x2837c70be661ea6d65c16854caf72e022a2c12bce3188f48ebdf5923788f1088\",\"TransDatas\":[{\"Input\":\"0000000000000000000000000000000000000000000000000000000000000003\",\"Code\":\"�X��Y�0�}�����\u000F\\r��Z��x\u0006]ή9�,\u0003�\u0010+4X-lC\u0019��\u001C)�ez�Y����+�7ԦUp�\u001F\u001B�J�#\\\"��\u0004.�\\t��P�Ĕ+&�\u0016lp����[\u0004�9��9\"}],\"From\":\"lat1u4flh5m0069nf9hx4jz6htdn6rt3v9yaakj28a\",\"To\":\"lat1xqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqpe9fgva\"}]";
        List<PPosInvokeContractInput> list = JSON.parseArray(data, PPosInvokeContractInput.class);

        List<String> addresses = Arrays.asList("0x01", "0x02");
        DefaultBlockParameter blockParameter = DefaultBlockParameterName.LATEST;
        List<Object> objList = Arrays.asList(addresses, blockParameter);
        System.out.println("running validator:" + JSON.toJSONString(objList));

        String[] addressArray = "0x01;0x02".split(";");
        List<Object> ob2jList = Arrays.asList(addressArray, blockParameter);
        System.out.println("ob2jList validator:" + JSON.toJSONString(ob2jList));

        System.out.println(String.format("【查询锁仓余额出错】地址:%s", Arrays.deepToString(addressArray)));
    }





}
