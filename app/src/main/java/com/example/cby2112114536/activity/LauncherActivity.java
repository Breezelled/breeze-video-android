package com.example.cby2112114536.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

/**
 * @author breeze
 */
public class LauncherActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("setting",
                        MODE_PRIVATE);
                boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
                if (isFirstTime) {
                    Intent intent = new Intent(LauncherActivity.this,
                            MainActivity.class);
                    startActivity(intent);
//                    sharedPreferences.edit().putBoolean("isFirstTime", false).apply();
                } else {

                }
//                finish();
            }
        }, 1000);
    }
}