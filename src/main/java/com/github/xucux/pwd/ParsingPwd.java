package com.github.xucux.pwd;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.github.xucux.Browser;
import com.github.xucux.single.Parsing;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.util.List;

/**
 * @descriptions:
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

    public ParsingPwd url(String url) {
        if (StringUtils.isBlank(url)) {
            throw new RuntimeException("错误的地址");
        }
        this.url = url;
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
                    if(data.contains("{\"info\":\"success\"")) {
                        JSONObject jsonObject = (JSONObject) JSON.parse(data);
                        log.info(jsonObject.toJSONString());
                        // TODO: 解析多个地址



                    }
                }
                return response;
            }
        });
        // 加载页面
        HtmlPage page= webClient.getPage(url);
        page.executeJavaScript("document.getElementById(\"pwd\").value = "+password);
        DomElement button = page.getElementById("sub");
        button.click();
        // Page nextPage =  page.getEnclosingWindow().getTopWindow().getEnclosedPage();
        webClient.waitForBackgroundJavaScript(30000);
        return null;
    }
}
