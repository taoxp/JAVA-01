package com.jike.nio.http;

/**
 * @author: taoxp
 * @create: 2021-01-23 01:54
 */
public class HttpTest {
    private final static String URL="http://localhost:8801";
    public static void main(String[] args) {
        String result=OkHttpUtil.requestGet(URL);
        System.out.println(result);
    }
}
