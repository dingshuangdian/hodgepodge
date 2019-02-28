package com.lsqidsd.hodgepodge.view;

import android.os.Handler;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.databinding.ActivitySplashBinding;
import com.lsqidsd.hodgepodge.utils.Jump;

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
        handler.postDelayed(() -> {
            Jump.jumpToNormalActivity(SplashScreenActivity.this, MainActivity.class);
            SplashScreenActivity.this.finish();
        }, 3000);
    }
}
