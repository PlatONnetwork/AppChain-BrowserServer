package com.platon.browser.service.account;

import com.platon.browser.AgentApplication;
import com.platon.browser.client.PlatOnClient;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.BigInteger;

@Slf4j
@SpringBootTest(classes = { AgentApplication.class })
@ActiveProfiles("appchain")
public class AccountServiceTest {
    @Resource
    private PlatOnClient platOnClient;
    @Resource
    private AccountService accountService;


    /**
     * 根据区块号获取激励池余额
     */
    @Test
    public void getInciteBalance() {
        BigDecimal balance = accountService.getInciteBalance(BigInteger.valueOf(5));
        System.out.println("balance:" + balance.toString());

    }

    /**
     * 根据区块号获取锁仓池余额
     */
    @Test
    public void getLockCabinBalance() {
        BigDecimal balance = accountService.getLockCabinBalance(BigInteger.valueOf(5));
        System.out.println("balance:" + balance.toString());
    }

    /**
     * 根据区块号获取质押池余额
     */
    @Test
    public void getStakingBalance() {
        BigDecimal balance = accountService.getStakingBalance(BigInteger.valueOf(501));
        System.out.println("balance:" + balance.toString());
    }
}
