package com.lsqidsd.hodgepodge.http.download;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;

public class InstalledReceiver extends BroadcastReceiver {
    private Handler mHandler;
    /**
     * 卸载App的广播
     */
    public final static String BROADCAST_RECEIVER_ACTION_PACKAGE_REMOVED = "android.intent.action.PACKAGE_REMOVED";

    /**
     * 安装App的广播
     */
    public final static String BROADCAST_RECEIVER_ACTION_PACKAGE_ADDED = "android.intent.action.PACKAGE_ADDED";

    /**
     * 安装完成
     */
    public final static int INSTALL_SUCCESS = 200;

    public InstalledReceiver(Handler mHandler) {
        this.mHandler = mHandler;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        // 接收卸载广播
        if (BROADCAST_RECEIVER_ACTION_PACKAGE_REMOVED.equals(intent.getAction())) {
            // String packageName = intent.getDataString();// 卸载程序的包名
            // Toast.makeText(context, packageName + "卸载成功",
            // Toast.LENGTH_LONG).show();
        }

        // 接收安装广播
        if (BROADCAST_RECEIVER_ACTION_PACKAGE_ADDED.equals(intent.getAction())) {
            // 安装程序的包名
            String installPackageName = intent.getDataString();
            PackageInfo packageInfo = getPackageInfo(context);
            String packageName = "package:" + packageInfo.packageName;
            if (!TextUtils.isEmpty(installPackageName) && installPackageName.equals(packageName)) {
                mHandler.obtainMessage(InstalledReceiver.INSTALL_SUCCESS).sendToTarget();
            }
        }

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
