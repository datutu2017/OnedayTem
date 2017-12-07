package com.eg.onedaytem.util;


import com.eg.onedaytem.gson.Now;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by 大土土 on 2017/11/29.
 * yiyun0331@163.com
 */

public class Utility {

//JSON解析
    public static Now handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather6");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Now.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
