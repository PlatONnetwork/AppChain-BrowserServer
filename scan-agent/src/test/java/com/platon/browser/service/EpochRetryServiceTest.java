package com.platon.browser.service;

import com.platon.browser.AgentApplication;
import com.platon.browser.service.epoch.EpochRetryService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.math.BigInteger;

@Slf4j
@SpringBootTest(classes = { AgentApplication.class })
@ActiveProfiles("appchain")
public class EpochRetryServiceTest {

    @Resource
    private EpochRetryService epochRetryService;

    @SneakyThrows
    @Test
    public void getBlockAsync() {
        epochRetryService.consensusChange(BigInteger.ZERO);
    }
}
