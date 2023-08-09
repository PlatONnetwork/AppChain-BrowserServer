package com.platon.browser.service;


import com.alibaba.fastjson.JSON;
import com.platon.browser.BrowserApiApplication;
import com.platon.browser.response.home.StakingListNewResp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;


@Slf4j
@SpringBootTest(classes = {BrowserApiApplication.class }, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("hskchain")
public class HomeServiceTest  {

    @Resource
    private HomeService homeService;


    @Test
    public void testStakingListNew() {
        StakingListNewResp stakingListNewResp = homeService.stakingListNew();
        System.out.println("stakingListNewResp:" + JSON.toJSONString(stakingListNewResp));
    }

}
