package com.github.xucux;

import cn.hutool.core.lang.Singleton;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;

/**
 * @descriptions:
 * @author: xu-ux
 * @version: 1.0
 * <pre> 浏览器实例获取 </pre>
 */
public class Browser {

    private static class WebClientHolder {

        private static final WebClient INSTANCE = new WebClient();

        static {
            INSTANCE.getOptions().setJavaScriptEnabled(true);
            INSTANCE.getOptions().setCssEnabled(false);
            INSTANCE.getOptions().setThrowExceptionOnScriptError(false);
            INSTANCE.getOptions().setThrowExceptionOnFailingStatusCode(false);
            INSTANCE.getOptions().setActiveXNative(false);
            INSTANCE.setAjaxController(new NicelyResynchronizingAjaxController());
        }
    }

    public static WebClient getInstance(){
        return  WebClientHolder.INSTANCE;
    }

}
