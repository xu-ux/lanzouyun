package com.github.xucux.single;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.github.xucux.Browser;
import com.github.xucux.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.net.ssl.HttpsURLConnection;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @descriptions: 解析
 * @author: xu-ux
 * @version: 1.0
 * <pre> </pre>
 */
@Slf4j
public class Parsing {

    private static final String PREFIX = "https://developer.lanzoug.com/file/";

    public static Parsing build() {
        return new Parsing(PREFIX);
    }

    public Parsing config(String prefix){
        if (StringUtils.isBlank(prefix)) {
            throw new RuntimeException("错误的前缀");
        }
        this.prefix = prefix;
        return this;
    }

    public Parsing url(String url) {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("错误的地址");
        }
        this.url = url;
        return this;
    }


    private String prefix;

    private WebClient webClient;

    private String url;

    private String result;

    private String realUrl;

    public Parsing(String prefix) {
        this.prefix = prefix;
    }

    public String getPrefix() {
        return prefix;
    }

    public WebClient getWebClient() {
        return webClient;
    }

    public String getUrl() {
        return url;
    }

    public String getResult() {
        return result;
    }

    public String getRealUrl() {
        return realUrl;
    }

    public Parsing go() throws IOException {
        // 获取浏览器实例
        webClient = Browser.getInstance();
        // 监听资源加载，这里的WebConnectionWrapper会监听所有资源加载
        webClient.setWebConnection(new WebConnectionWrapper(webClient){
            @Override
            public WebResponse getResponse(WebRequest request) throws IOException {
                WebResponse response = super.getResponse(request);
                String data = response.getContentAsString();
                String contentType = response.getContentType();
                if (contentType.contains("json")) {
                    result = data;
                    log.debug(data);
                    if(data.contains("{\"zt\":1,\"dom\":\"https")) {
                        JSONObject jsonObject= (JSONObject) JSON.parse(data);
                        log.info(jsonObject.toJSONString());
                        //解析得到下载链接
                        String url = prefix + jsonObject.getString("url");
                        realUrl = HttpUtils.getRedirectUrl(url);
                    }
                }

                return response;
            }
        });
        webClient.getPage(url);
        return this;
    }





}
