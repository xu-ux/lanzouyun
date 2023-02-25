package com.github.xucux.pwd;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.github.xucux.Browser;
import com.github.xucux.single.Parsing;
import com.github.xucux.single.ParsingMulti;
import com.github.xucux.util.DownUtils;
import com.github.xucux.util.HttpUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * @descriptions: 解析密码项
 * @author: xu-ux
 * @version: 1.0
 * <pre> </pre>
 */
@Slf4j
public class ParsingPwd {

    private static final String PREFIX = "https://developer.lanzoug.com/file/";

    public static ParsingPwd build() {
        return new ParsingPwd(PREFIX);
    }

    public ParsingPwd config(String prefix){
        if (StringUtils.isBlank(prefix)) {
            throw new RuntimeException("错误的前缀");
        }
        this.prefix = prefix;
        return this;
    }

    public ParsingPwd url(String url) throws MalformedURLException {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("错误的地址");
        }
        this.url = url;
        URL url1 = new URL(url);
        this.hostUrl = url1.getProtocol().concat("://").concat(url1.getHost());
        return this;
    }

    public ParsingPwd password(String password) {
        if (StringUtils.isBlank(password)) {
            throw new RuntimeException("错误的密码");
        }
        this.password = password;
        return this;
    }


    private String prefix;

    private WebClient webClient;

    private String url;

    private String password;

    private String result;

    private String hostUrl;

    private List<String> realUrl;

    public ParsingPwd(String prefix) {
        this.prefix = prefix;
    }

    public Parsing go() throws IOException {
        // 获取浏览器实例
        WebClient webClient = Browser.getInstance();
        // 监听资源加载
        webClient.setWebConnection(new WebConnectionWrapper(webClient){
            @Override
            public WebResponse getResponse(WebRequest request) throws IOException {
                WebResponse response = super.getResponse(request);
                String contentType = response.getContentType();
                String data = response.getContentAsString();
                if (contentType.contains("json")) {
                    log.debug(data);
                    if(data.contains("\"info\":\"sucess\"")) {
                        JSONObject jsonObject = (JSONObject) JSON.parse(data);
                        log.info(jsonObject.toJSONString());
                        // TODO: 解析多个地址
                        JSONArray conentList = jsonObject.getJSONArray("text");
                        if (CollectionUtil.isNotEmpty(conentList)) {
                            List<TextDto> textDtos = conentList.toJavaList(TextDto.class);
                            textDtos.forEach(s -> {
                                String singleUrl = hostUrl.concat("/").concat(s.getId());
                                try {
                                    ParsingMulti parsing = ParsingMulti.build().url(singleUrl).go();
                                    log.info("name:{} size:{} real url:{}",s.getNameAll(),s.getSize(),parsing.getRealUrl());
                                    DownUtils.downloadByNIO(parsing.getRealUrl(),"D:\\Download\\lanzouyun\\",s.getNameAll());
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            });
                        }


                    }
                }
                return response;
            }
        });
        // 加载页面
        HtmlPage page= webClient.getPage(url);
        page.executeJavaScript("document.getElementById(\"pwd\").value = '"+password+"'");
        DomElement button = page.getElementById("sub");
        button.click();
        // Page nextPage =  page.getEnclosingWindow().getTopWindow().getEnclosedPage();
        webClient.waitForBackgroundJavaScript(30000);
        return null;
    }
}
