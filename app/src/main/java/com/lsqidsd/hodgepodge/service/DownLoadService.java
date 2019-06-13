package com.lsqidsd.hodgepodge.service;

import android.app.Activity;
import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.util.DisplayMetrics;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lsqidsd.hodgepodge.BuildConfig;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.api.HttpGet;
import com.lsqidsd.hodgepodge.http.HttpOnNextListener;
import com.lsqidsd.hodgepodge.http.MyDisposableObserver;
import com.lsqidsd.hodgepodge.utils.NotificationUtil;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;

import okhttp3.Call;
import okhttp3.ResponseBody;

public class DownLoadService extends Service {
    private int oldProgress = 0;
    private MyBinder myBinder;
    private MyHandler handler;
    private InstalledReceiver receiver;
    private static final String STORGE_PATH = Environment.getExternalStorageDirectory() + "/Download";//存储路径
    private static final String APK_NAME = "app-release.apk";
    private static NotificationUtil notificationUtil;
    private static WeakReference<Activity> reference;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
    private static class MyHandler extends Handler {
        public MyHandler(Activity activity) {
            reference = new WeakReference<>(activity);
        }
        @Override
        public void handleMessage(Message msg) {
            if (reference.get() != null) {
                switch (msg.what) {
                    case 1:
                        showNotificationProgress(msg.arg1);
                        break;
                }
            }
        }
    }

    FileCallBack callBack = new FileCallBack(STORGE_PATH, APK_NAME) {
        @Override
        public void onError(Call call, Exception e, int id) {
        }

        @Override
        public void onResponse(File response, int id) {
        }

        @Override
        public void inProgress(float progress, long total) {
            super.inProgress(progress, total);
            int cProgress = (int) (progress * 100);
            if (cProgress != oldProgress) {
                Message message = new Message();
                message.what = 1;
                message.arg1 = cProgress;
                handler.sendMessage(message);
            }
            oldProgress = cProgress;
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        notificationUtil = new NotificationUtil(this);
        myBinder = new MyBinder();
        receiver = new InstalledReceiver(handler);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addDataScheme("package");
        registerReceiver(receiver, filter);
    }

    public class MyBinder extends Binder {
        public void startDownload(Activity activity) {
            handler = new MyHandler(activity);
            download();
        }
    }

    private void download() {
        MyDisposableObserver observer = new MyDisposableObserver(new HttpOnNextListener() {
            @Override
            public void onSuccess(Object o) {
                new Thread(() -> {
                    try {
                        callBack.saveFile((ResponseBody) o);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            @Override
            public void onFail(String e) {
                Toast.makeText(reference.get(), e, Toast.LENGTH_SHORT).show();
            }
        });
        HttpGet.updata(observer);
    }

    private static void showNotificationProgress(int currentProgress) {
        int maxProgress = 100;
        notificationUtil.getBuilder(R.mipmap.flogo)
                .setContentText("腾讯新闻")
                .setContentTitle("当前下载进度: " + currentProgress + "%")
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setProgress(maxProgress, currentProgress, false);
        notificationUtil.sendNotification();
        if (currentProgress == maxProgress) {
            notificationUtil.cancelNotification();
            //下载完成后自动安装apk
            showDialog(reference.get());
        }
    }

    private static void showDialog(Activity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context, R.style.AlertDialog);
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        double width = metrics.widthPixels * 0.9;
        AlertDialog alertDialog = builder
                .setCancelable(false)
                .setMessage("下载完成，是否安装？")
                .setPositiveButton("确定", (a, b) -> reference.get().startActivity(getInstallIntent()))
                .setNegativeButton("取消", (a, b) ->
                        builder.create().dismiss())
                .create();
        alertDialog.show();
        alertDialog.getWindow().setLayout((int) width, ViewGroup.LayoutParams.WRAP_CONTENT);
    }

    /**
     * 启动安装界面
     *
     * @return
     */
    private static Intent getInstallIntent() {
        File apkInstallDir = new File(STORGE_PATH, APK_NAME);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(apkInstallDir), "application/vnd.android.package-archive");
        } else {
            // 声明需要的临时权限
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // 第二个参数，即第一步中配置的authorities
            Uri contentUri = FileProvider.getUriForFile(reference.get(), BuildConfig.APPLICATION_ID + ".fileProvider",
                    apkInstallDir);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        }
        return intent;
    }

    /**
     * 安装完成后删除安装包
     */
    private void deleteApk() {
        File apkInstallDir = new File(STORGE_PATH, APK_NAME);
        if (apkInstallDir != null && apkInstallDir.exists()) {
            if (apkInstallDir.delete()) {
                Toast.makeText(this, "安装包已删除", Toast.LENGTH_LONG).show();
            }
        }
    }
}
