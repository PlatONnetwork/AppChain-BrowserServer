package com.platon.browser.service;

import com.platon.browser.BrowserApiApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;

@Slf4j
@SpringBootTest(classes = { BrowserApiApplication.class })
@ActiveProfiles("appchain")
public class StakingServiceTest {
    @Resource
    private StakingService stakingService;

    @SneakyThrows
    @Test
    public void stakingStatisticNew() {
        stakingService.stakingStatisticNew();
    }
}
