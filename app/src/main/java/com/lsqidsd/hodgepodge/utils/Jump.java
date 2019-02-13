package com.lsqidsd.hodgepodge.utils;

import android.content.Context;
import android.content.Intent;

import com.lsqidsd.hodgepodge.view.WebViewActivity;

public class Jump {

    public static void jumpToWebActivity(Context context, String s) {
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", s);
        context.startActivity(intent);
    }
}
