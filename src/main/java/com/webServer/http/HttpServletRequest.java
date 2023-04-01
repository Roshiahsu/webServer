package com.webServer.http;

import com.sun.xml.internal.ws.util.StringUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName HttpServletRequest
 * @Version 1.0
 * @Description 解析請求
 * @Date 2023/3/24、上午3:41
 */

public class HttpServletRequest {
    /**
     * 回車常量
     */
    public static final int CR =13;
    /**
     * 換行常量
     */
    public static final int LF =10;

    private Socket socket;
    /**
     * 請求方法
     */
    private String method;
    /**
     * 抽象路徑
     */
    private String uri;
    /**
     * 協議版本
     */
    private String protocol;
    /**
     * 消息頭
     */
    private Map<String,String> headers=new HashMap<>();
    /**
     * 請求路徑
     */
    private String requestUri;

    /**
     * 放置請求參數
     */
    private Map<String,String> parameters = new HashMap<>();


    public HttpServletRequest(Socket socket) throws IOException {
        System.out.println("HttpServletRequest開始");
        this.socket=socket;
        parseRequestLine();
        parseRequestHeaders();



    }

    /**
     * 解析請求行
     * @throws IOException
     */
    private void parseRequestLine() throws IOException {
        String line = readLine();
        System.out.println("獲取到的請求行:"+line);
        /*
         * 分析獲取的請求行"GET / HTTP/1.1"，每段資料中間間隔一個"space"
         * 分段的時候regex要用 "\\s" 代表"space"
         */
        String[] data = line.split("\\s");
        method = data[0];
        uri = data[1];
        parseUri(uri);
        protocol = data[2];
        System.out.println("Method:"+method);
        System.out.println("uri:"+uri);
        System.out.println("protocol:"+protocol);
    }

    /**
     * 抽象路徑進一步解析
     * @param uri 抽象路徑
     */
    private void parseUri(String uri){
            System.out.println("開始分析uri");
        /*
         * uri包含兩種情況:1. 不包含參數 2.包含參數
         * 舉個例子
         * 不包含參數 /myweb/reg.html
         * 包含參數 /myweb/reg?username=roshia&password=123456
         */
        String[] data = uri.split("\\?");
        /*
         * 假設要分析的路徑:/myweb/reg?username=roshia&password=123456
         * 透過?進行分析 會得到 [/myweb/reg,username=roshia&password=123456]
         * /myweb/reg 是我們的請求路徑
         */
        requestUri = data[0];
            //data.length>1 代表有附帶參數
            if(data.length>1){
                /*
                 * username=roshia&password=123456 透過"&"進行第二次解析
                 * 會得到[username=roshia,password=123456]
                 */
                String[] parse = data[1].split("&");
                for (int i = 0; i <parse.length; i++) {
                    /*
                     * 第三次透過"="解析可以獲得請參數
                     * [username,roshia,password,123456]
                     */
                    String[] split = parse[i].split("=");
                    /*
                     * 把請求結果放入Map備用
                     * 由於請求的參數可能只有參數名，沒有參數值
                     * 例如：username= ， 解析出來的結果[username]只有一個元素
                     * 所以put value的時候添加一個判斷，不含參數值就put Null
                     */
                    parameters.put(split[0],split.length>1?split[1]:null);
                }
            }


        }

    /**
     * 解析消息頭
     */
    private void parseRequestHeaders() throws IOException {
        System.out.println("開始解析消息頭");
        String line;
        while (true){
            line = readLine();
            if(line.isEmpty()){
                break;
            }
            String[] data = line.split(":\\s");
            headers.put(data[0],data[1]);
        }
        System.out.println("headers:"+headers.toString());
    }

    /**
     * 讀取Socket中的流
     * @return
     * @throws IOException
     */
    private String readLine() throws IOException {
        InputStream in = socket.getInputStream();
        StringBuilder stringBuffer = new StringBuilder();
        char pre = 'a';
        char cur = 'a';
        int d;
        while ((d=in.read())!=-1){
            cur = (char)d;
            if(pre == CR && cur == LF){
                break;
            }
            stringBuffer.append(cur);
            pre = cur;
        }
        return stringBuffer.toString().trim();
    }

    public String getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getRequestUri() {
        return requestUri;
    }

    public String getHeaders(String name){
        return headers.get(name);
    }

    public String getParameters(String name){
        return parameters.get(name);
    }
}
