package com.lsqidsd.hodgepodge.utils;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;

import com.lsqidsd.hodgepodge.view.WebViewActivity;

public class Jump {

    public static void jumpToWebActivity(Context context, String s) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", s);
        context.startActivity(intent);
    }

    public static void jumpToNormalActivity(Context context, Class<? extends Activity> cls, String... s) {
        Intent intent = new Intent(context, cls);
        if (!s.toString().isEmpty()) {
            intent.putExtra("extra", s);
        }
        context.startActivity(intent);
    }

    /**
     * onBind启动服务
     *
     * @param context
     * @param cls
     * @param connection
     */

    public static void bindService(Context context, Class<? extends Service> cls, ServiceConnection connection) {
        Intent intent = new Intent(context, cls);
        context.bindService(intent, connection, context.BIND_AUTO_CREATE);

    }

    public static void startService(Context context, Class<? extends Service> cls) {
        Intent intent = new Intent(context, cls);
        context.startService(intent);
    }
}
