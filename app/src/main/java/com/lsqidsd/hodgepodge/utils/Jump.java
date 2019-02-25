package com.lsqidsd.hodgepodge.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

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
}
