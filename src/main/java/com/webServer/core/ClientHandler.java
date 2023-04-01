package com.webServer.core;

import com.webServer.http.HttpServletRequest;
import com.webServer.http.HttpServletResponse;
import com.webServer.servlet.RegServlet;

import java.io.File;
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
            //1.解析請求
            HttpServletRequest request = new HttpServletRequest(socket);
            HttpServletResponse response = new HttpServletResponse(socket);
            //2.處理請求
            String path = request.getRequestUri();

            if ("/myweb/reg".equals(path)){
                RegServlet regServlet = new RegServlet();
                regServlet.service(request,response);
            }else{
                File file = new File("./webapps"+path);
                if(file.exists()&&file.isFile()){
                    response.setEntity(file);
                }else{
                    response.setStatusCode(404);
                    response.setStatusReason("Not Found");
                    response.setEntity(new File("./webapps/root/404.html"));
                }
            }



            //3.發送響應
            response.response();


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
