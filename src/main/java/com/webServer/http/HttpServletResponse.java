package com.webServer.http;

import jdk.nashorn.internal.ir.CallNode;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @ClassName HttpServletResponse
 * @Version 1.0
 * @Description TODO
 * @Date 2023/3/31、下午9:09
 */
public class HttpServletResponse {

    private Socket socket;

    private int statusCode = 200;

    private String statusReason = "OK";

    private Map<String, String> headers = new HashMap<>();

    private File entity;

    public HttpServletResponse(Socket socket) {
        this.socket = socket;
    }

    /**
     * 主方法
     *
     * @throws IOException
     */
    public void response() throws IOException {
        sendStatusLine();
        sendHeaders();
        sendContent();
    }

    /**
     * 發送狀態行
     *
     * @throws IOException
     */
    private void sendStatusLine() throws IOException {
        String line = "HTTP/1.1 " + statusCode + " " + statusReason;
        printLn(line);

    }

    /**
     * 發送響應頭
     */
    private void sendHeaders() throws IOException {
        String line;
        Set<Map.Entry<String, String>> entrySet = headers.entrySet();
        for (Map.Entry<String, String> e : entrySet) {
            line = e.getKey()+": "+e.getValue();
            printLn(line);
        }
        printLn("");
    }

    private void sendContent() throws IOException {
        OutputStream out = socket.getOutputStream();
        try (
                FileInputStream in = new FileInputStream(entity);
        ) {
            int len;
            byte[] data = new byte[1024 * 10];
            while ((len = in.read(data)) != -1) {
                out.write(data, 0, len);
            }
        }
    }

    /**
     * 發送一行字符串
     *
     * @param data
     */
    private void printLn(String data) throws IOException {
        OutputStream out = socket.getOutputStream();
        byte[] bytes = data.getBytes(StandardCharsets.ISO_8859_1);
        out.write(bytes);
        out.write(13);
        out.write(10);
    }

    public void setEntity(File entity) {
        this.entity = entity;
        String fileName = entity.getName();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        addHeaders("Content-Type",HttpContext.mimeMapping.get(ext));
        addHeaders("Content-Length",entity.length()+"");
    }

    public File getEntity() {

        return entity;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason;
    }

    public void addHeaders(String name, String value) {
        this.headers.put(name, value);
    }

}
