package com.github.xucux.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.attribute.FileAttribute;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @descriptions:
 * @author: xu-ux
 * @version: 1.0
 * <pre> </pre>
 */
@Slf4j
public class DownUtils {

    /**
     * nio下载文件到本地并保存
     * @param url
     * @param filePath
     * @param fileName
     */
    public static void downloadByNIO(String url, String filePath, String fileName) {
        try (InputStream ins = new URL(url).openStream()) {
            Path target = Paths.get(filePath, fileName);
            //设置获取全部权限
            //Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
            //FileAttribute<Set<PosixFilePermission>> fileAttributes = PosixFilePermissions.asFileAttribute(permissions);
            //Files.createDirectories(target.getParent(),fileAttributes);
            Files.createDirectories(target.getParent());
            Files.copy(ins, target, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
