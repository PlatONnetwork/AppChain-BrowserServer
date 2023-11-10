package com.platon.browser.utils;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.annotation.JSONField;
import com.alibaba.fastjson2.support.config.FastJsonConfig;
import com.platon.protocol.core.methods.response.PlatonBlock;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

@Slf4j
public class JsonTest {

    @Test
    public void test_json_null() {
        PlatonBlock result = new PlatonBlock();
        log.debug("result: {}", com.alibaba.fastjson2.JSON.toJSONString(result));

    }



    @SneakyThrows
    @Test
    public void test_json() {
        String json = "{\"Name\": \"xiao li\", \"Age\": 18}";
        FastJsonConfig config = new FastJsonConfig();
        config.setReaderFeatures(JSONReader.Feature.FieldBased);
        Student s = JSON.parseObject(json, Student.class);

        System.out.println("name:" + s.name + " ,age:" + s.age);
    }

    @Data
    class Student {
        @JSONField(name = "name", alternateNames = {"Name"})
        private String name;

        @JSONField(name = "age", alternateNames = {"Age"})
        private Integer age;
    }
}
