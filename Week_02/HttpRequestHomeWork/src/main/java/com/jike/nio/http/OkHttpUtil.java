package com.jike.nio.http;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * @author: taoxp
 * @create: 2021-01-23 00:20
 */
public class OkHttpUtil {
    public static String requestGet(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request.Builder request = new Request.Builder();
        request.url(url).get();
        Call call = okHttpClient.newCall(request.build());
        String result=null;
        try {
            Response response = call.execute();
            if (!response.isSuccessful()){
                System.out.println("request:"+url+" failed. error code:"+response.code());
                return null;
            }
            result=response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}
