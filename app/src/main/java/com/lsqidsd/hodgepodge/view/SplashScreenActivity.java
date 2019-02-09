package com.lsqidsd.hodgepodge.view;

import android.content.Intent;
import android.os.Handler;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.databinding.ActivitySplashBinding;

public class SplashScreenActivity extends BaseActivity {
    private ActivitySplashBinding binding;


    @Override
    public int getLayout() {
        return R.layout.activity_splash;
    }

    @Override
    public void initView() {
        binding = getBinding(binding);
        Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                SplashScreenActivity.this.startActivity(intent);
                SplashScreenActivity.this.finish();

            }
        }, 3000);

    }
}
