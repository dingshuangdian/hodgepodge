package com.lsqidsd.hodgepodge.http.download;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.lsqidsd.hodgepodge.R;

import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class DownloadService extends Service {
    public static final String TAG = "DownloadService";
    private Context context;
    private RemoteViews remoteViews;
    private int notificationid = 10;
    private boolean isForeground;
    private NotificationManager mNotificationManager;
    //关于线程池的一些配置
    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int CORE_POOL_SIZE = Math.max(3, CPU_COUNT / 2);
    private static final int MAX_POOL_SIZE = CORE_POOL_SIZE * 2;
    private static final long KEEP_ALIVE_TIME = 0L;
    private DownloadExecutor executor = new DownloadExecutor(CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>());
    private HashMap<String, DownloadTask> taskHashMap = new HashMap<>();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            RequestInfo requestInfo = (RequestInfo) intent.getSerializableExtra(InnerConstant.Inner.SERVICE_INTENT_EXTRA);
            executeDownload(requestInfo);
            upDateNotification();
            Toast.makeText(context, "已添加到下载队列", Toast.LENGTH_SHORT).show();
        }
        return super.onStartCommand(intent, flags, startId);
    }

    private synchronized void executeDownload(RequestInfo requestInfo) {
        DownloadInfo downloadInfo = requestInfo.getDownloadInfo();
        DownloadTask task = taskHashMap.get(downloadInfo.getUniqueId());
        DbHolder holder = new DbHolder(context);
        FileInfo info = holder.getFileInfo(downloadInfo.getUniqueId());
        if (null == task) {
            if (null != info) {
                if (info.getDownloadStatus() == DownloadStatus.LOADING ||
                        info.getDownloadStatus() == DownloadStatus.PREPARE) {
                    holder.updateState(info.getId(), DownloadStatus.PAUSE);

                } else if (info.getDownloadStatus() == DownloadStatus.COMPLETE) {
                    if (downloadInfo.getFile().exists()) {
                        if (!TextUtils.isEmpty(downloadInfo.getAction())) {
                            Intent intent = new Intent();
                            intent.setAction(downloadInfo.getAction());
                            intent.putExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD, info);
                            sendBroadcast(intent);
                        }
                        return;
                    } else {
                        holder.deleteFileInfo(downloadInfo.getUniqueId());
                    }
                }
            }
            if (requestInfo.getDictate() == InnerConstant.Request.loading) {
                task = new DownloadTask(this, downloadInfo, holder);
                taskHashMap.put(downloadInfo.getUniqueId(), task);
            }
        } else {
            Log.e("task=", "不为空");
            if (task.getStatus() == DownloadStatus.COMPLETE || task.getStatus() == DownloadStatus.LOADING) {
                if (!downloadInfo.getFile().exists()) {
                    task.pause();
                    taskHashMap.remove(downloadInfo.getUniqueId());
                    executeDownload(requestInfo);
                    return;
                } else {
                    holder.updateState(info.getId(), DownloadStatus.PAUSE);
                    Toast.makeText(context, "该任务已经在下载中", Toast.LENGTH_SHORT).show();
                }
            }
        }
        if (null != task) {
            if (requestInfo.getDictate() == InnerConstant.Request.loading) {
                executor.executeTask(task);
            } else {
                task.pause();
            }
        }
    }

    private Notification getNotification(boolean complete) {
        remoteViews = new RemoteViews(this.getPackageName(), R.layout.down_notification);
        if (complete) {
            remoteViews.setTextViewText(R.id.msg, "一个视频下载完毕,点击查看");
            remoteViews.setImageViewResource(R.id.iv_image, R.mipmap.complete);
        } else {
            remoteViews.setTextViewText(R.id.msg, "一个视频正在下载,点击查看");
            remoteViews.setImageViewResource(R.id.iv_image, R.mipmap.down);
        }
        Notification.Builder builder = new Notification.Builder(this).setContent(remoteViews)

                .setSmallIcon(R.mipmap.down);
        return builder.build();
    }

    private void upDateNotification() {
        if (!isForeground) {
            startForeground(notificationid, getNotification(false));
            isForeground = true;
        } else {
            mNotificationManager.notify(notificationid, getNotification(false));
        }
    }
}
