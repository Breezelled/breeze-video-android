package com.example.cby2112114536.activity;

import static com.example.cby2112114536.common.Constants.ADS_GIF;
import static com.example.cby2112114536.common.Constants.ADS_PIC;
import static com.example.cby2112114536.common.Constants.BASE_URL;
import static com.example.cby2112114536.common.Constants.BUCKET_NAME;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bumptech.glide.Glide;

/**
 * @author breeze
 */
public class LauncherActivity extends AppCompatActivity {

    private LauncherViewModel launcherViewModel;
    private String adsGIFString = "";
    private String adsPicString = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        launcherViewModel = new ViewModelProvider(this).get(LauncherViewModel.class);
        launcherViewModel.getPresignedUrl(ADS_GIF).observe(
                this, url -> adsGIFString = BASE_URL + url.getData().getFileUrl());
        launcherViewModel.getPresignedUrl(ADS_PIC).observe(
                this, url -> adsPicString = BASE_URL + url.getData().getFileUrl());

        Log.i("ads", "预签名GIF链接: " + adsGIFString);
        Log.i("ads", "预签名图片链接: " + adsPicString);

        Glide.with(this).load(adsGIFString).preload();
        Glide.with(this).load(adsPicString).preload();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
//                SharedPreferences sharedPreferences = getSharedPreferences("setting",
//                        MODE_PRIVATE);
//                boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
//                if (isFirstTime) {
//                    Intent intent = new Intent(LauncherActivity.this,
//                            MainActivity.class);
//                    startActivity(intent);
//                    sharedPreferences.edit().putBoolean("isFirstTime", false).apply();
//                } else {
//
//                }
//                finish();
                Intent intent = new Intent(LauncherActivity.this,
                        SplashActivity.class);
                intent.putExtra("adsGIFString", adsGIFString);
                intent.putExtra("adsPicString", adsPicString);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }
}