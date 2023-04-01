package com.webServer.servlet;

import com.webServer.config.JDBCConfig;
import com.webServer.http.HttpServletRequest;
import com.webServer.http.HttpServletResponse;
import com.webServer.utils.SqlUtils;
import com.webServer.utils.Utils;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName LoginServlet
 * @Version 1.0
 * @Description TODO
 * @Date 2023/4/1、下午10:51
 */
public class LoginServlet {

    private Statement s ;

    public void service(HttpServletRequest request,HttpServletResponse response){
        String username = request.getParameters("username");
        String password =request.getParameters("password");
        System.out.println("username:"+username);
        System.out.println("password:"+password);

        try (
                Connection conn = JDBCConfig.getConn()
        ){
            s = conn.createStatement();
            if (SqlUtils.querySelect(username,s)!=1){
                response.setEntity(new File(Utils.DIR+"/myweb/fail.html"));
                return;
            }
            password = Utils.encode(password);
            String queryPassword = SqlUtils.queryGetPassword(username, this.s);
            if(queryPassword.equals(password)){
                response.setEntity(new File(Utils.DIR+"/myweb/ok.html"));
            }else{
                response.setEntity(new File(Utils.DIR+"/myweb/fail.html"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
