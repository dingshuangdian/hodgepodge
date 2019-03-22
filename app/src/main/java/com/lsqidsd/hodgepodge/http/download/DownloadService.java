package com.lsqidsd.hodgepodge.http.download;


import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class DownloadService extends Service {
    public static final String TAG = "DownloadService";
    public static boolean canRequest = true;
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
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (canRequest) {
            canRequest = false;
            if (null != intent && intent.hasExtra(InnerConstant.Inner.SERVICE_INTENT_EXTRA)) {
                ArrayList<RequestInfo> requestInfos = (ArrayList<RequestInfo>) intent.getSerializableExtra(InnerConstant.Inner.SERVICE_INTENT_EXTRA);
                if (null != requestInfos && requestInfos.size() > 0) {
                    for (RequestInfo requestInfo : requestInfos) {

                    }
                }
            }
            canRequest = true;

        }
        return super.onStartCommand(intent, flags, startId);
    }

    private synchronized void executeDownload(RequestInfo requestInfo) {
        DownloadInfo downloadInfo = requestInfo.getDownloadInfo();
        DownloadTask task = taskHashMap.get(downloadInfo.getUniqueId());
        DbHolder holder = new DbHolder(getBaseContext());
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
            if (task.getStatus() == DownloadStatus.COMPLETE || task.getStatus() == DownloadStatus.LOADING) {
                if (!downloadInfo.getFile().exists()) {
                    task.pause();
                    taskHashMap.remove(downloadInfo.getUniqueId());
                    executeDownload(requestInfo);
                    return;
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
}
