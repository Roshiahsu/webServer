package com.webServer.core;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @ClassName WebServerApplication
 * @Version 1.0
 * @Description TODO
 * @Date 2023/3/23、上午7:11
 */
public class WebServerApplication {
    private ServerSocket serverSocket;

    //構造訪法，用於初始化
    public WebServerApplication() {
        try {
            System.out.println("伺服器啟動中");
            //創建serversocket 8088port
            serverSocket = new ServerSocket(8088);
            System.out.println("伺服器啟動中");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //負責啟動
    public void start(){
        try {
            System.out.println("等待客戶端連接");
            Socket socket = serverSocket.accept();
            System.out.println("一個客戶端連線了");
            ClientHandler handler = new ClientHandler(socket);
            Thread thread = new Thread(handler);
            thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        WebServerApplication server = new WebServerApplication();
        server.start();
    }
}
