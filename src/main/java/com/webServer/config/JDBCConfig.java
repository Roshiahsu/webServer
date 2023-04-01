package com.webServer.config;

import com.alibaba.druid.pool.DruidDataSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @ClassName JDBCConfig
 * @Version 1.0
 * @Description TODO
 * @Date 2023/4/1、下午9:02
 */
public class JDBCConfig {

    public static Connection getConn() throws SQLException {
        //1.獲取資料庫連接
        //?characterEncoding=utf8&serverTimezone=Asia/Shanghai
        Connection conn =
                DriverManager.getConnection("jdbc:mysql://localhost:3306/empdb", "root", "123456");
        System.out.println("連接對象:"+conn);
        return conn;
    }
}
