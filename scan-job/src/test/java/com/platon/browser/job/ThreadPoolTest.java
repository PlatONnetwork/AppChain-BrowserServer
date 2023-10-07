package com.platon.browser.job;


import com.platon.browser.JobApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@Slf4j
@SpringBootTest(classes = { JobApplication.class })
@ActiveProfiles("appchain")
public class ThreadPoolTest {
    @SneakyThrows
    @Test
    public void test_scheduler() {
        for(;;){
            System.out.println("等待任务调度执行...");
            Thread.sleep(3000000);
        }
    }
}
