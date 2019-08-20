package com.platon.browser.util;
import	java.util.concurrent.TimeoutException;
import	java.util.HashMap;
import	java.lang.annotation.Target;
import	java.util.ArrayList;

import com.alibaba.fastjson.JSONObject;
import com.platon.browser.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * Created with IntelliJ IDEA.
 * User: 王章雄
 * Email:wangzhangxiong@juzix.net
 * Date: 2019/8/19
 * Time: 19:46
 * Desc:
 */
public final class MarkDownParserUtil {
    private final static Logger logger = LoggerFactory.getLogger(MarkDownParserUtil.class);
    private static final String MARKDOWN_BEFORE_INDEX = "<article class=\"markdown-body";
    private static final String MARKDOWN_LAST_INDEX = "</article>";

    /**
     * 获取md文件
     *
     * @param fileName
     * @return
     */
    private static String httpGet(String fileName) throws IOException {
        HttpGet httpGet = null;
        try {
            HttpClient httpClient = HttpClients.createDefault();
            httpGet = new HttpGet(fileName);
            RequestConfig requestConfig = RequestConfig.custom()
                    .setConnectTimeout(1000).setConnectionRequestTimeout(1000)
                    .setSocketTimeout(1000).build();
            httpGet.setConfig(requestConfig);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            //拿到实体
            HttpEntity httpEntity = httpResponse.getEntity();
            //获取结果，这里可以正对相应的数据精细字符集的转码
            if (httpEntity != null) {
                return EntityUtils.toString(httpEntity, "utf-8");
            }
        }catch (HttpResponseException e){
            logger.error("##ERROR #:HttpResponseException ${}",fileName);
            return null;
        }finally {
            if (httpGet != null) {
                //释放连接
                httpGet.releaseConnection();
            }
        }
        return null;
    }

    public static String acquireMD(String fileName) throws IOException {
        String text = httpGet(fileName);
        if (text == null) {
            return null;
        }
        String tempFile = StringUtils.substringAfterLast(text, MARKDOWN_BEFORE_INDEX);
        if (StringUtils.isBlank(tempFile)) {
            return null;
        }
        return MARKDOWN_BEFORE_INDEX + StringUtils.substringBeforeLast(tempFile, MARKDOWN_LAST_INDEX) + MARKDOWN_LAST_INDEX;
    }
    public static String parserMD(String mdFile){
        Document document=Jsoup.parse(mdFile);
        Elements table=document.select("table");
        Elements ths=table.select("th");
        Elements trs=table.select("td");
        Map<String, String> tableMap=new HashMap<> (ths.size());
        for (int i = 0; i < ths.size(); i++) {
            tableMap.put(ths.get(i).text(),trs.get(i).text());
        }
        return JSONObject.toJSONString(tableMap);
    }

}
