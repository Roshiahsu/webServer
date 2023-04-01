package com.webServer.utils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @ClassName SqlUtils
 * @Version 1.0
 * @Description TODO
 * @Date 2023/4/1、下午11:22
 */
public class SqlUtils {


    /**
     * 使用username統計用戶數
     * @param username 用戶名稱
     * @param s SQL傳輸物件
     * @return
     * @throws SQLException
     */
    public static int querySelect(String username,Statement s) throws SQLException {
        String sql ="select count(*) from user where username = '"+username+"'";
        ResultSet rs = s.executeQuery(sql);
        rs.next();
        return rs.getInt(1);
    }

    /**
     *  根據用戶名獲取密碼
     * @param username 用戶名
     * @param s
     * @return 密碼
     * @throws SQLException
     */
    public static String queryGetPassword(String username,Statement s) throws SQLException {
        String sql ="select password from user where username = '"+username+"'";
        ResultSet rs = s.executeQuery(sql);
        rs.next();
        return rs.getString(1);
    }

    /**
     *新增使用者數據
     * @param username 用戶名
     * @param password 用戶密碼
     * @param nick 用戶暱稱
     * @param s SQL傳輸物件
     * @throws SQLException
     */
    public static void insert(String username,String password,String nick,Statement s) throws SQLException {
        String sql = "insert into user values (null,'"+username+"','"+password+"','"+nick+"')";
        s.executeUpdate(sql);
        System.out.println("執行完成");
    }
}
