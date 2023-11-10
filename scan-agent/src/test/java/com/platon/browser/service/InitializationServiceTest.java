package com.platon.browser.service;

import com.platon.browser.AgentApplication;
import com.platon.browser.bootstrap.service.InitializationService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = { AgentApplication.class })
@ActiveProfiles("appchain")
public class InitializationServiceTest {


    @Resource
    private InitializationService initializationService;

    @SneakyThrows
    @Test
    public void test_init() {
        initializationService.init("my00001");
    }
}
