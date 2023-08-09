package com.platon.browser.service.receipt;

import com.platon.browser.AgentApplication;
import com.platon.browser.bean.Receipt;
import com.platon.browser.bean.ReceiptResult;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Iterator;
import java.util.Map;


@Slf4j
@SpringBootTest(classes = { AgentApplication.class })
@ActiveProfiles("appchain")
public class ReceiptServiceTest {
    @Mock
    private ReceiptRetryService retryService;
    @Spy
    private ReceiptService target;


    @Test
    public void test() throws Exception {
        ReceiptResult receiptResult = retryService.getReceipt(1367490L);

        Iterator< Map.Entry<String, Receipt>> it = receiptResult.getMap().entrySet().iterator();
       while(it.hasNext()){
           Map.Entry<String, Receipt> entity = it.next();
           System.out.println("key::" + entity.getKey());
           System.out.println("implicitPPOSTx::" + entity.getValue().getImplicitPPOSTx());
       }

    }

}
