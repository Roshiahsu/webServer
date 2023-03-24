package com.webServer.core;

import com.webServer.http.HttpServletRequest;

import java.io.IOException;
import java.net.Socket;

/**
 * @ClassName ClientHandler
 * @Version 1.0
 * @Description TODO
 * @Date 2023/3/24、上午3:37
 */
public class ClientHandler implements Runnable{

    private Socket socket;

    public ClientHandler(Socket socket){
        this.socket = socket;

    }

    @Override
    public void run() {
        System.out.println("ClientHandler開始");
        try {
            //解析請求
            HttpServletRequest request = new HttpServletRequest(socket);




        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
