package com.lsqidsd.hodgepodge.base;
import android.app.Application;
import android.content.Context;
import android.support.v7.app.AppCompatDelegate;

import com.lsqidsd.hodgepodge.diyview.refresh.ClassicsHeader;
import com.lsqidsd.hodgepodge.utils.DynamicTimeFormat;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

public class BaseApplication extends Application {
    private  static Context mContext;

    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer((context, layout) -> {

            layout.setEnableAutoLoadMore(true);
            layout.setEnableOverScrollDrag(false);
            layout.setEnableOverScrollBounce(true);
            layout.setEnableLoadMoreWhenContentNotFull(true);
            layout.setEnableScrollContentWhenRefreshed(true);
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
            layout.setPrimaryColorsId(android.R.color.white, android.R.color.black);
            return new ClassicsHeader(context).setTimeFormat(new DynamicTimeFormat("更新于 %s"));
        });
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
    }

    public static Context getmContext() {
        return mContext;
    }

}
