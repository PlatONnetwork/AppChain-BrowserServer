package com.platon.browser.client;

import com.platon.browser.BrowserServiceApplication;
import com.platon.browser.bean.Receipt;
import com.platon.browser.bean.ReceiptResult;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;


@Slf4j
@SpringBootTest(classes = { BrowserServiceApplication.class })
@ActiveProfiles("hskchain")
public class PlatOnClientTest {

	@Resource
	private PlatOnClient platOnClient;


	@SneakyThrows
	@Test
	public void testGetReceiptResult() {


		ReceiptResult receiptResult  = platOnClient.getReceiptResult(2170L);
		Receipt receipt = receiptResult.getMap().get("0xc592d5e17affc84eef16bdf3b18ce84a9fb45efb03f1ba4685ba5b41649e1a94");

    	System.out.println("receipt.getStatus():" + receipt.getStatus() + "   receipt.getLogStatus():" + receipt.getLogStatus());
	}
}
