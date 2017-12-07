package com.eg.onedaytem;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.luomi.lm.ad.ADType;
import com.luomi.lm.ad.DRAgent;
import com.luomi.lm.ad.IAdSuccessBack;
import com.luomi.lm.ad.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    LinearLayout ll_layout;
    private boolean canJump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ll_layout = (LinearLayout) findViewById(R.id.ll_layout);
        LogUtil.setENABLE_LOGCAT(false);
        //动态加载权限
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_PHONE_STATE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()) {
            String[] permissions = permissionList.toArray(new
                    String[permissionList.size()]);
            ActivityCompat.requestPermissions(this, permissions, 1);
        } else {
            requestAds();
        }
    }

    private void requestAds() {
        /**
         * this  上下文
         * adtype 广告类型（详情请看附录表）
         * true  针对开屏是否显示倒计时展示 针对banner是是否显示关闭钮
         * IAdSuccessBack 广告展示回调接口
         */
        DRAgent.getInstance().getOpenView(this,
                ADType.FULL_SCREEN, true, new IAdSuccessBack() {

            @Override
            public void onError(final String result) {

                // TODO Auto-generated method stub
//                System.out.println(">>>>>>广告展示失败:" + result);
                Log.d("TAG",">>>>>>广告展示失败:");
                handler.post(new Runnable() {

                    @Override
                    public void run() {
                        // TODO Auto-generated method stub
                        Toast.makeText(SplashActivity.this, result, Toast.LENGTH_SHORT).show();
//                        SplashActivity.this.startActivity(new
//                                Intent(SplashActivity.this,MainActivity.class));
//                        SplashActivity.this.finish();
                        canJump = true;
                        forward();
                    }
                });


                // TODO Auto-generated method stub

            }

            @Override
            public void onClick(String result) {
                // TODO Auto-generated method stub
                // TODO Auto-generated method stub
//                System.out.println(">>>>>广告被点击:" + result);
                Log.d("TAG",">>>>>>广告被点击:");
            }

            @Override
            public void OnSuccess(String result) {
                // TODO Auto-generated method stub

                // TODO Auto-generated method stub
                Log.d("TAG",">>>>>>广告展示成功:");
//                System.out.println(">>>广告展示成功:" + result);
//                Toast.makeText(SplashActivity.this, result, Toast.LENGTH_SHORT).show();
                if (result.equals("7")) {
                    // AdShowActivity.this.finish();
                    // AdShowActivity.this.startActivity(new
                    // Intent(AdShowActivity.this, MainActivity2.class));
                    forward();
                }

            }

            @Override
            public void OnLoadAd(View view) {
                // TODO Auto-generated method stub
//                System.out.println(">>>>>>广告加载成功");
                Log.d("TAG",">>>>>>广告加载成功:");
                //
                ll_layout.addView(view);
            }
        });


    }

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {

        }


    };

//    @Override
//    protected void onPause() {
//        super.onPause();
//        canJump = false;
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (canJump) {
//            forward();
//        }
//        canJump = true;
//    }

    private void forward() {
        if (canJump) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            canJump = true;
        }
    }

}
