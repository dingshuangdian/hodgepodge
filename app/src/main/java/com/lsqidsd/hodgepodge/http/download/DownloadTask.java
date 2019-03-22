package com.lsqidsd.hodgepodge.http.download;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntRange;

public class DownloadTask implements Runnable {
    public static final String TAG = "DownloadTask";
    private Context context;
    private DownloadInfo info;
    private FileInfo fileInfo;
    private DbHolder holder;
    private boolean isPause;


    public DownloadTask(Context context, DownloadInfo info, DbHolder holder) {
        this.context = context;
        this.info = info;
        this.holder = holder;
        //初始化下载文件信息
        fileInfo = new FileInfo();
        fileInfo.setId(info.getUniqueId());
        fileInfo.setDownloadUrl(info.getUrl());
        fileInfo.setFilePath(info.getFile().getAbsolutePath());

        FileInfo fileInfoFromDb = holder.getFileInfo(info.getUniqueId());
        long location = 0;
        long fileSize = 0;
        if (null != fileInfoFromDb) {
            location = fileInfoFromDb.getDownloadLocation();
            fileSize = fileInfoFromDb.getSize();
            if (location == 0) {
                if (info.getFile().exists()) {
                    info.getFile().delete();
                }
            } else {
                if (!info.getFile().exists()) {
                    holder.deleteFileInfo(info.getUniqueId());
                    location = 0;
                    fileSize = 0;
                }
            }
        } else {
            if (info.getFile().exists()) {
                info.getFile().delete();
            }
        }
        fileInfo.setSize(fileSize);
        fileInfo.setDownloadLocation(location);
    }

    @Override
    public void run() {
        download();

    }

    public void pause() {
        isPause = true;
    }

    @IntRange(from = DownloadStatus.WAIT, to = DownloadStatus.FAIL)
    public int getStatus() {
        if (null != fileInfo) {
            return fileInfo.getDownloadStatus();
        }
        return DownloadStatus.FAIL;
    }


    public void setFileStatus(@IntRange(from = DownloadStatus.WAIT, to = DownloadStatus.FAIL) int status) {
        fileInfo.setDownloadStatus(status);

    }

    public void sendBroadcast(Intent intent) {
        context.sendBroadcast(intent);
    }

    public DownloadInfo getDownLoadInfo() {
        return info;
    }

    public FileInfo getFileInfo() {
        return fileInfo;
    }

    private void download() {
        fileInfo.setDownloadStatus(DownloadStatus.PREPARE);
        Intent intent = new Intent();
        intent.setAction(info.getAction());
        intent.putExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD, fileInfo);
        context.sendBroadcast(intent);
    }
}
