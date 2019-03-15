package com.lsqidsd.hodgepodge.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.widget.Toast;

import com.lsqidsd.hodgepodge.BuildConfig;
import com.lsqidsd.hodgepodge.api.HttpGet;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.http.HttpOnNextListener;
import com.lsqidsd.hodgepodge.http.MyDisposableObserver;
import com.lsqidsd.hodgepodge.http.download.DaoUtil;
import com.lsqidsd.hodgepodge.http.download.FileCallBack;
import com.lsqidsd.hodgepodge.http.download.Info;
import com.lsqidsd.hodgepodge.http.download.InstalledReceiver;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.ResponseBody;

public class DownLoadService extends Service {
    private NotificationManager manager;
    private Context context;
    private DownloadFinish downloadFinish;
    private int oldProgress = 0;
    private MyBinder myBinder;
    private DaoUtil daoUtil;
    private Info info;
    private InstalledReceiver receiver;
    private final String STORGE_PATH = Environment.getExternalStorageDirectory() + "/Download";//存储路径
    private final String APK_NAME = "app-release.apk";

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

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    showNotificationProgress(msg.arg1);
                    break;
            }
        }
    };
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
            int currentProgress = (int) (100 * progress);
            if (currentProgress != oldProgress) {
                Message message = new Message();
                message.what = 1;
                message.arg1 = currentProgress;
                handler.sendMessage(message);
            }
            oldProgress = currentProgress;
        }
    };

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
        myBinder = new MyBinder();
        loadLocalData();
        receiver = new InstalledReceiver(handler);
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.intent.action.PACKAGE_ADDED");
        filter.addDataScheme("package");
        registerReceiver(receiver, filter);
    }


    public class MyBinder extends Binder {
        public void startDownload(Context mContext, DownloadFinish finish) {
            context = mContext;
            downloadFinish = finish;
            download();
        }
    }

    private void loadLocalData() {
        daoUtil = DaoUtil.getInstance();
        info = daoUtil.queryDownBy(1);
        if (info == null) {
            File outputFile = new File(STORGE_PATH, APK_NAME);
            Info info = new Info(BaseConstant.UPDATA_URL);
            info.setId(1);
            info.setSavePath(outputFile.getAbsolutePath());
            daoUtil.save(info);
        }
    }

    private void download() {
        MyDisposableObserver observer = new MyDisposableObserver(new HttpOnNextListener() {
            @Override
            public void onSuccess(Object o) {
                ResponseBody responseBody = (ResponseBody) o;
                new Thread(() -> {
                    try {
                        callBack.saveFile(responseBody);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }

            @Override
            public void onFail(String e) {
                Toast.makeText(context, e, Toast.LENGTH_SHORT).show();
            }
        });
        HttpGet.downLoad(observer, info);
    }

    private void showNotificationProgress(int currentProgress) {
        Notification.Builder builder = creatNotification("腾讯新闻", "当前下载进度: " + currentProgress + "%");
        int maxProgress = 100;
        builder.setProgress(maxProgress, currentProgress, false);
        manager.notify(0, builder.build());
        if (currentProgress == maxProgress) {
            if (manager != null) {
                manager.cancel(0);//下载完毕  移除通知栏
            }
            if (downloadFinish != null) {
                downloadFinish.downfinish();
            }
            //下载完成后自动安装apk
            startActivity(getInstallIntent());
        }
    }

    private Notification.Builder creatNotification(String title, String msg) {
        Notification.Builder builder = new Notification.Builder(this)
                .setAutoCancel(false)
                .setContentText(msg)
                .setContentTitle(title)
                .setOngoing(true)
                .setDefaults(Notification.DEFAULT_VIBRATE)
                .setSmallIcon(this.getResources().getIdentifier("flogo", "mipmap", this.getPackageName()));
        //适配8.0以上通知栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //通知渠道（Notification Channels）
            //创建NotificationChannel对象，指定Channel的id、name和通知的重要程度
            NotificationChannel channel = new NotificationChannel("channel_id", "app_msg", NotificationManager.IMPORTANCE_DEFAULT);
            channel.canBypassDnd();//是否可以绕过请勿打扰模式
            channel.canShowBadge();//是否可以显示icon角标
            channel.enableLights(true);//是否显示通知闪灯
            channel.enableVibration(true);//收到小时时震动提示
            channel.setBypassDnd(true);//设置绕过免打扰
            channel.setLightColor(Color.RED);//设置闪光灯颜色
            channel.getAudioAttributes();//获取设置铃声设置
            channel.setVibrationPattern(new long[]{0});//设置震动模式
            channel.shouldShowLights();//是否会闪光
            manager.createNotificationChannel(channel);
            builder.setChannelId("channel_id");//这个id参数要与上面channel构建的第一个参数对应
        }
        return builder;
    }

    /**
     * 启动安装界面
     *
     * @return
     */
    private Intent getInstallIntent() {
        File apkInstallDir = new File(STORGE_PATH, APK_NAME);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            intent.setDataAndType(Uri.fromFile(apkInstallDir), "application/vnd.android.package-archive");
        } else {
            // 声明需要的临时权限
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            // 第二个参数，即第一步中配置的authorities
            Uri contentUri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".fileProvider",
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

    public interface DownloadFinish {
        void downfinish();
    }
}
