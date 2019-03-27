package com.lsqidsd.hodgepodge.http.download;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.support.annotation.IntRange;
import android.util.Log;
import com.lsqidsd.hodgepodge.api.HttpGet;
import com.lsqidsd.hodgepodge.http.HttpOnNextListener;
import com.lsqidsd.hodgepodge.http.MyDisposableObserver;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import okhttp3.ResponseBody;
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
            Log.e("status+++",fileInfo.getDownloadStatus()+"");
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
        try {
            RandomAccessFile randomAccessFile = new RandomAccessFile(info.getFile(), "rwd");
            MyDisposableObserver observer = new MyDisposableObserver(new HttpOnNextListener() {
                @Override
                public void onSuccess(Object o) {
                    if (((ResponseBody) o).contentLength() <= 0) {
                        if (info.getFile().exists()) {
                            info.getFile().delete();
                        }
                        holder.deleteFileInfo(info.getUniqueId());
                        Log.e(TAG, "文件大小 = " + ((ResponseBody) o).contentLength() + "\t, 终止下载过程");
                        return;
                    }
                    if (fileInfo.getSize() <= 0) {
                        fileInfo.setSize(((ResponseBody) o).contentLength());
                    }
                    new Thread(() -> {
                        InputStream inputStream = ((ResponseBody) o).byteStream();
                        byte[] buffer = new byte[1024 * 8];
                        int offset;
                        try {
                            randomAccessFile.seek(fileInfo.getDownloadLocation());
                            long millis = SystemClock.uptimeMillis();
                            while ((offset = inputStream.read(buffer)) != -1) {
                                if (isPause) {
                                    Log.i(TAG, "下载过程 设置了 暂停");
                                    fileInfo.setDownloadStatus(DownloadStatus.PAUSE);
                                    isPause = false;
                                    holder.saveFile(fileInfo);
                                    context.sendBroadcast(intent);
                                    randomAccessFile.close();
                                    inputStream.close();
                                    return;
                                }
                                randomAccessFile.write(buffer, 0, offset);
                                fileInfo.setDownloadStatus(DownloadStatus.LOADING);
                                fileInfo.setDownloadLocation(fileInfo.getDownloadLocation() + offset);
                                if (SystemClock.uptimeMillis() - millis >= 1000) {
                                    millis = SystemClock.uptimeMillis();
                                    holder.saveFile(fileInfo);
                                    context.sendBroadcast(intent);
                                }
                            }
                            fileInfo.setDownloadStatus(DownloadStatus.COMPLETE);
                            holder.saveFile(fileInfo);
                            context.sendBroadcast(intent);
                        } catch (IOException e) {
                            Log.e(TAG, "下载过程发生失败");
                            holder.saveFile(fileInfo);
                            context.sendBroadcast(intent);
                            e.printStackTrace();
                        } finally {
                            try {
                                if (randomAccessFile != null) {
                                    randomAccessFile.close();
                                }
                                if (inputStream != null) {
                                    inputStream.close();
                                }

                            } catch (IOException e) {
                                Log.e(TAG, "finally 块  关闭文件过程中 发生异常");
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }

                @Override
                public void onFail(String e) {
                    Log.e(TAG, e);
                    holder.saveFile(fileInfo);
                    context.sendBroadcast(intent);
                }
            });
            HttpGet.download(observer, fileInfo);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
