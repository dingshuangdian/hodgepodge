package com.lsqidsd.hodgepodge.base;
import android.app.Application;
import android.content.Context;

public class BaseApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();;
    }

    public static Context getmContext() {
        return mContext;
    }

}
