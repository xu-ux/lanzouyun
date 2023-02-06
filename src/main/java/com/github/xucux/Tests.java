package com.github.xucux;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.util.WebConnectionWrapper;
import com.github.xucux.single.Parsing;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @descriptions:
 * @author: xu-ux
 * @version: 1.0
 * <pre> </pre>
 */
@Slf4j
public class Tests {

    public static void main(String[] args) throws IOException {
        Parsing parsing = Parsing.build().url("https://xuux.lanzouy.com/i3kku0d").go();
        log.info(parsing.getRealUrl());
    }



}
