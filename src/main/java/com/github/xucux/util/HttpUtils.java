package com.github.xucux.util;

import lombok.extern.slf4j.Slf4j;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @descriptions:
 * @author: xu-ux
 * @version: 1.0
 * <pre> </pre>
 */
@Slf4j
public class HttpUtils {

    /**
     * 获取重定向地址
     * @param path
     * @return
     * @throws Exception
     */
    public static String getRedirectUrl(String path)  {
        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(path)
                    .openConnection();
            conn.setInstanceFollowRedirects(false);
            conn.setConnectTimeout(10000);
            conn.addRequestProperty("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
            return conn.getHeaderField("Location");
        } catch (Exception e) {
            log.error("获取重定向地址失败",e);
            return "";
        }
    }

//    public static void redirect(String url) throws IOException {
//        Connection connect = Jsoup.connect(url);
//
//        connect.header("Accept-Language","zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2");
//        Connection.Response response = connect.followRedirects(false).execute();
//
//        System.out.println("Is URL going to redirect : " + response.hasHeader("Location"));
//        System.out.println("Target : " + response.header("Location"));
//    }

}
