package com.lsqidsd.hodgepodge.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;

import com.lsqidsd.hodgepodge.utils.StatusBarUtil;

public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        StatusBarUtil.setRootViewFitsSystemWindows(this, true);
        StatusBarUtil.setTranslucentStatus(this);
        if (!StatusBarUtil.setStatusBarDarkTheme(this, true)) {
            StatusBarUtil.setStatusBarColor(this, 0x55000000);
        }
    }
    public abstract int getLayout();
    public abstract void initView();
    public <T extends ViewDataBinding> T getBinding(T t) {
        t = DataBindingUtil.setContentView(this, getLayout());
        return t;
    }
}
