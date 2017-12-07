package com.eg.onedaytem.gson;

import com.google.gson.annotations.SerializedName;

/**
 * Created by 大土土 on 2017/11/29.
 * yiyun0331@163.com
 */

public class Now {

    public String status;
    public Basic basic;
    public Noww now;
    public Update update;

    public class Basic {
        //地点
        @SerializedName("location")
        public String loc;
        //上级城市
        @SerializedName("parent_city")
        public String parent_city;
        //所属城市
        @SerializedName("admin_area")
        public String admin_area;

    }

    public class Noww {
        //温度
        @SerializedName("tmp")
        public String temperature;
        //天气状况
        @SerializedName("cond_txt")
        public String cond;
        @SerializedName("wind_dir")
        public String dir;
    }

    public class Update {
        @SerializedName("loc")
        public String loc;
        @SerializedName("utc")
        public String utc;
    }


}
