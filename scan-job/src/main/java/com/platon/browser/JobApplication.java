package com.platon.browser;


import com.platon.browser.exception.ConfigLoadingException;
import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
@EnableRetry
@Configuration
@EnableScheduling
@SpringBootApplication
@EnableEncryptableProperties
@MapperScan(basePackages = {
        "com.platon.browser",
        "com.platon.browser.dao.mapper"
})
public class JobApplication {
    static {
        File saltFile = FileUtils.getFile(System.getProperty("user.dir"), "jasypt.properties");
        Properties properties = new Properties();
        try (InputStream in = new FileInputStream(saltFile)) {
            properties.load(in);
            String salt = properties.getProperty("jasypt.encryptor.password");
            if (StringUtils.isBlank(salt))
                throw new ConfigLoadingException("加密盐不能为空!");
            salt = salt.trim();
            System.setProperty("JASYPT_ENCRYPTOR_PASSWORD", salt);
            log.info("salt:{}", salt);
        } catch (IOException | ConfigLoadingException e) {
            log.error("加载解密文件出错", e);
            System.exit(1);
        }
    }
    public static void main(String[] args) {
        SpringApplication.run(JobApplication.class, args);
    }
}
