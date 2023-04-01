package com.webServer.servlet;

import com.webServer.config.JDBCConfig;
import com.webServer.http.HttpServletRequest;
import com.webServer.http.HttpServletResponse;
import com.webServer.utils.Utils;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    public void service(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameters("username");
        String nick = request.getParameters("nickname");
        String password = request.getParameters("password");
        System.out.println("username:"+username);
        System.out.println("nickname:"+nick);
        System.out.println("password:"+password);

        password=encode(password);
        try(
                Connection conn = JDBCConfig.getConn()
        ) {
            //創建執行SQL語句、傳輸器物件
            Statement s = conn.createStatement();
            String sql = "insert into user values (null,'"+username+"','"+password+"','"+nick+"')";
            s.executeUpdate(sql);
            System.out.println("執行完成");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        response.setEntity(new File(Utils.DIR+"/myweb/ok.html"));
    }

    /**
     * 使用SHA進行加密
     * @return
     */
    private String encode(String str){
        BigInteger sha = null;
        byte[] bytes = str.getBytes();
        try{
            //建立一個MessageDigest物件，並指定使用SHA演算法
            MessageDigest messageDigest = MessageDigest.getInstance("SHA");
            // 將位元組陣列傳入MessageDigest物件中進行運算
            messageDigest.update(bytes);
            //將結果轉換成BigInteger
            sha = new BigInteger(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return sha.toString(32);
    }
}
