package com.webServer.utils;


import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @ClassName Utils
 * @Version 1.0
 * @Description TODO
 * @Date 2023/4/1、下午9:45
 */
public class Utils {

    public static final String DIR ="./webapps";


    /**
     * 使用SHA進行加密
     * @return
     */
    public static String encode(String str){
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
