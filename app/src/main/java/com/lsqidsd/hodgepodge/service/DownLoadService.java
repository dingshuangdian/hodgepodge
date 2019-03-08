package com.lsqidsd.hodgepodge.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;

public class DownLoadService extends Service {
    private final String PATH = Environment.getExternalStorageDirectory() + "/hodgepodge";
    private final String TAG = DownLoadService.class.getSimpleName();
    private int currentProgress = 0;
    private NotificationManager manager;
    private MyBinder myBinder = new MyBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        manager = (NotificationManager) getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public class MyBinder extends Binder {
        public void startDownload() {

        }

        public int getProgress() {
            return 0;
        }
    }

    private void showNotificationProgress(int currentProgress) {
        Notification.Builder builder = creatNotification("腾讯新闻", "当前下载进度: " + currentProgress + "%");
        int maxProgress = 100;
        builder.setDefaults(NotificationCompat.FLAG_ONLY_ALERT_ONCE);
        builder.setProgress(maxProgress, currentProgress, false);
        manager.notify(0, builder.build());
        if (currentProgress == 100) {
            if (manager != null) {
                manager.cancel(0);//下载完毕  移除通知栏
            }
            //下载完成后自动安装apk
            //installApk();
        }
    }
    private Notification.Builder creatNotification(String title, String msg) {
        Notification.Builder builder = new Notification.Builder(this)
                .setAutoCancel(false)//用户点击通知栏是否自动删除
                .setContentTitle(title)
                .setSmallIcon(this.getResources().getIdentifier("flogo", "mipmap", this.getPackageName()))
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), this.getResources().getIdentifier("flogo",
                        "mipmap", this.getPackageName())));//App大图标
        //适配8.0以上通知栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //第三个参数设置通知的优先级别
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
}
