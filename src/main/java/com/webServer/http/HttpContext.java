package com.webServer.http;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @ClassName HttpContext
 * @Version 1.0
 * @Description TODO
 * @Date 2023/4/1、下午6:41
 */
public class HttpContext {

    public static Map<String,String> mimeMapping = new HashMap<>();

    static {
        initMimeMapping();
    }

    private static void initMimeMapping(){
        try{
            Properties properties = new Properties();
            properties.load(
                HttpContext.class.getResourceAsStream("./web.properties")
            );
            properties.forEach(
                    (k,v)->mimeMapping.put(k.toString(),v.toString())
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
