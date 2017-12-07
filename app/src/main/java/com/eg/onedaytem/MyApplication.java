package com.eg.onedaytem;

import android.app.Application;
import android.content.Context;

import com.luomi.lm.ad.DRAgent;

/**
 * Created by 大土土 on 2017/12/6.
 * yiyun0331@163.com
 */

public class MyApplication extends Application {
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        DRAgent.getInstance().init(this,
                "067c51d9a56983f4956588cb86fcb2a6", true);
    }

    public static Context getContext() {
        return context;
    }
}
