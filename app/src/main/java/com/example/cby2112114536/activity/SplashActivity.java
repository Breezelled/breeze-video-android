package com.example.cby2112114536.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.vectordrawable.graphics.drawable.Animatable2Compat;

import android.content.Intent;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.resource.gif.GifDrawable;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.cby2112114536.R;
import com.example.cby2112114536.api.SplashApi;
import com.example.cby2112114536.databinding.ActivitySplashBinding;
import com.example.cby2112114536.utils.NetUtil;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * @author breeze
 */
public class SplashActivity extends AppCompatActivity {

    private ActivitySplashBinding binding;
    private boolean isSkip;
    private Drawable pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        binding = ActivitySplashBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Button skip = binding.button;
        ImageView ads = binding.imageView;
        Intent intent = getIntent();
        String adsPicString = intent.getStringExtra("adsPicString");
        String adsGIFString = intent.getStringExtra("adsGIFString");
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    pic = (Drawable) Glide.with(SplashActivity.this).load(adsPicString)
                            .submit().get();
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(runnable).start();

        Glide.with(this).load(adsGIFString)
                .into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource,
                                        @Nullable Transition<? super Drawable> transition) {
                if (resource instanceof GifDrawable) {
                    GifDrawable gifDrawable = (GifDrawable) resource;
                    gifDrawable.setLoopCount(1);
                    Animatable2Compat.AnimationCallback animationCallback = new
                            Animatable2Compat.AnimationCallback() {
                        @Override
                        public void onAnimationEnd(Drawable drawable) {
                            super.onAnimationEnd(drawable);
                            ads.setImageDrawable(pic);
                        }
                    };

                    gifDrawable.registerAnimationCallback(animationCallback);
                    ads.setImageDrawable(gifDrawable);
                    gifDrawable.start();
                }
            }
        });

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
        }, 7000);
    }

    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        home.addCategory(Intent.CATEGORY_HOME);
        startActivity(home);
    }

}