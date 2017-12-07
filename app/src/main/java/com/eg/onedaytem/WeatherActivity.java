package com.eg.onedaytem;

import android.annotation.SuppressLint;
import android.content.Intent;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.TextView;
import android.widget.Toast;

import com.eg.onedaytem.gson.Now;
import com.eg.onedaytem.util.HttpUtil;
import com.eg.onedaytem.util.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WeatherActivity extends AppCompatActivity {
    public SwipeRefreshLayout swipeRefresh;
    private TextView chengshi;
    private TextView wendu;
    private TextView fengxiang;
    private TextView zhuangkuang;

    @SuppressLint("ResourceAsColor")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_weather);
        swipeRefresh = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefresh.setColorSchemeColors(R.color.colorPrimary);
        chengshi = (TextView) findViewById(R.id.chengshi);
        wendu = (TextView) findViewById(R.id.weather_temperature);
        fengxiang = (TextView) findViewById(R.id.weather_wind);
        zhuangkuang = (TextView) findViewById(R.id.weather_cond);

//        SharedPreferences prefs=PreferenceManager
//                .getDefaultSharedPreferences(this);
//        String weatherString=prefs.getString("weather_id",null);
//        final String weatherId;
//        if (weatherString!=null){
//            Now now=Utility.handleWeatherResponse(weatherString);
//            weatherId=now.basic.loc;
//            showWeatherInfo(now);
//        }else {
        final String weatherId = getIntent().getStringExtra("weather_id");
        requestWeather(weatherId);
//        }
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                requestWeather(weatherId);
//                Toast.makeText(getApplicationContext(), "数据更新成功",
//                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void requestWeather(final String weatherId) {
        String weatherUrl = "https://free-api.heweather.com/s6/weather/now?location=" + weatherId + "&key=c25f7e29caf9442d87b26edad0fd19f3";
        HttpUtil.sendOkHttpRequest(weatherUrl, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(WeatherActivity.this,
                                "失败", Toast.LENGTH_SHORT).show();
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String responseText = response.body().string();
                //解析JSON
                final Now now = Utility.handleWeatherResponse(responseText);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (now != null && "ok".equals(now.status)) {
//                            Toast.makeText(WeatherActivity.this, "获取天气信息成功", Toast.LENGTH_SHORT).show();
                            SharedPreferences.Editor editor = PreferenceManager.
                                    getDefaultSharedPreferences(WeatherActivity.this).edit();
                            editor.putString("weather", responseText);
                            editor.apply();
                            showWeatherInfo(now);
                        } else {
                            Toast.makeText(WeatherActivity.this,
                                    "获取天气信息失败", Toast.LENGTH_SHORT).show();
                        }
                        swipeRefresh.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void showWeatherInfo(Now now) {
        String city1 ="城市：" +now.basic.loc;
        String tem1 = now.now.temperature + "℃";
        String con1 = now.now.cond;
        String wind1 = now.now.dir;
        chengshi.setText(city1);
        wendu.setText(tem1);
        zhuangkuang.setText(con1);
        fengxiang.setText(wind1);


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Intent intent = new Intent(WeatherActivity.this, MainActivity.class);
            startActivity(intent);
            WeatherActivity.this.finish();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }

    }


}
