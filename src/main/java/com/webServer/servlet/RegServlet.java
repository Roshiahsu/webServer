package com.webServer.servlet;

import com.webServer.config.JDBCConfig;
import com.webServer.http.HttpServletRequest;
import com.webServer.http.HttpServletResponse;
import com.webServer.utils.SqlUtils;
import com.webServer.utils.Utils;

import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName RegServlet
 * @Version 1.0
 * @Description TODO 註冊業務
 * @Date 2023/4/1、下午8:52
 */
public class RegServlet {


    private Statement s ;

    public void service(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameters("username");
        String nick = request.getParameters("nickname");
        String password = request.getParameters("password");
        System.out.println("username:"+username);
        System.out.println("nickname:"+nick);
        System.out.println("password:"+password);

        password=Utils.encode(password);

        try(
                Connection conn = JDBCConfig.getConn()
        ) {
            s = conn.createStatement();
            if (SqlUtils.querySelect(username,s)!=0){
                response.setEntity(new File(Utils.DIR+"/myweb/fail.html"));
                return;
            }
            SqlUtils.insert(username,password,nick,s);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        response.setEntity(new File(Utils.DIR+"/myweb/ok.html"));
    }




}
