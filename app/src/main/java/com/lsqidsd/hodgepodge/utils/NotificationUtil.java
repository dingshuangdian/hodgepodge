package com.lsqidsd.hodgepodge.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.DrawableRes;

public class NotificationUtil {
    private static final String channel_id = "channel_id";
    private static final String channel_name = "channel_name";
    private NotificationManager manager;
    private Context context;
    private static Notification.Builder builder;
    private static final int notification_id = 1;

    public NotificationUtil(Context context) {
        this.manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        this.context = context;
    }

    public Notification.Builder getBuilder(@DrawableRes int id) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(context, channel_id);
            NotificationChannel channel = new NotificationChannel(channel_id, channel_name, manager.IMPORTANCE_DEFAULT);
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
        } else {
            builder = new Notification.Builder(context);
        }
        builder.setSmallIcon(id);
        builder.setOngoing(true);
        builder.setAutoCancel(false);
        return builder;
    }

    public void sendNotification() {
        manager.notify(notification_id, builder.build());
    }

    public void cancelNotification() {
        manager.cancel(notification_id);
    }
}
