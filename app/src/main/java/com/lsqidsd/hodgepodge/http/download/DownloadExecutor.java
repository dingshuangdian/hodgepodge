package com.lsqidsd.hodgepodge.http.download;

import android.content.Intent;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class DownloadExecutor extends ThreadPoolExecutor {
    public DownloadExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    public void executeTask(DownloadTask task) {
        int status = task.getStatus();
        if (status == DownloadStatus.PAUSE || status == DownloadStatus.FAIL) {
            task.setFileStatus(DownloadStatus.WAIT);
            Intent intent = new Intent();
            intent.setAction(task.getDownLoadInfo().getAction());
            intent.putExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD, task.getFileInfo());
            task.sendBroadcast(intent);
            execute(task);
        }
    }
}
