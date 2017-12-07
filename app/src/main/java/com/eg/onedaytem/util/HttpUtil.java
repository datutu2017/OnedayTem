package com.eg.onedaytem.util;

import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by 大土土 on 2017/11/29.
 * yiyun0331@163.com
 */

public class HttpUtil {
    public static void sendOkHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client=new OkHttpClient();
        Request request =new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }

}
