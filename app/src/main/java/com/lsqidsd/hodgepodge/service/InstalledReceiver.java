package com.lsqidsd.hodgepodge.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;

public class InstalledReceiver extends BroadcastReceiver {
    private Handler mHandler;
    /**
     * 安装完成
     */
    public final static int INSTALL_SUCCESS = 200;

    public InstalledReceiver(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {


    }

    /**
     * 获取包信息
     *
     * @param context
     * @return
     */
    public PackageInfo getPackageInfo(Context context) {
        PackageInfo info = null;
        try {
            info = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace(System.err);
        }
        if (info == null) {
            info = new PackageInfo();
        }
        return info;
    }
}
