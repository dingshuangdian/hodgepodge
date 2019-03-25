package com.lsqidsd.hodgepodge.http.download;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntRange;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownloadTask implements Runnable {
    public static final String TAG = "DownloadTask";
    private Context context;
    private DownloadInfo info;
    private FileInfo fileInfo;
    private DbHolder holder;
    private boolean isPause;
    private final int DEFAULT_TIME_OUT = 10;


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
        Log.e("准备下载", "准备下载");
        Intent intent = new Intent();
        intent.setAction(info.getAction());
        intent.putExtra(DownloadConstant.EXTRA_INTENT_DOWNLOAD, fileInfo);
        context.sendBroadcast(intent);
        RandomAccessFile randomAccessFile = null;
        FileChannel channelOut = null;
        InputStream inputStream = null;

        URL sizeUrl = null;
        HttpURLConnection sizeHttp = null;
        try {
            sizeUrl = new URL(info.getUrl());
            sizeHttp = (HttpURLConnection) sizeUrl.openConnection();
            sizeHttp.setRequestMethod("GET");
            sizeHttp.connect();
            long totalSize = sizeHttp.getContentLength();
            sizeHttp.disconnect();
            if (totalSize <= 0) {
                if (info.getFile().exists()) {
                    info.getFile().delete();
                }
                holder.deleteFileInfo(info.getUniqueId());
                return;
            }
            fileInfo.setSize(totalSize);
            randomAccessFile = new RandomAccessFile(info.getFile(), "rwd");

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            builder.writeTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            builder.readTimeout(DEFAULT_TIME_OUT, TimeUnit.SECONDS);
            builder.retryOnConnectionFailure(true);//错误重连
            Retrofit retrofit = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .baseUrl(fileInfo.getDownloadUrl())
                    .build();
            DownService downService = retrofit.create(DownService.class);
            downService.download("bytes=" + fileInfo.getDownloadLocation() + "-")
                    .subscribeOn(Schedulers.io())
                    .unsubscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new DisposableObserver<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {

                        }

                        @Override
                        public void onError(Throwable e) {

                        }

                        @Override
                        public void onComplete() {

                        }
                    });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
