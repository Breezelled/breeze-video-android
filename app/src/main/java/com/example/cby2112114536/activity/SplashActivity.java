package com.example.cby2112114536.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.cby2112114536.R;
import com.example.cby2112114536.databinding.ActivitySplashBinding;

/**
 * @author breeze
 */
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private boolean isSkip;
    private String adsString = "https://breeze-video-admin.s3.ap-east-1.amazonaws.com" +
            "/ads-files/cyberpunk_edgerunner.GIF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button skip = binding.button;
        ImageView ads = binding.imageView;
        Glide.with(this).load(adsString).into(ads);

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isSkip = true;
                Intent intent = new Intent(SplashActivity.this,
                        MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (!isSkip) {
                    Intent intent = new Intent(SplashActivity.this,
                            MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        }, 5000);
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }
}