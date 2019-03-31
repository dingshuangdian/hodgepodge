package com.lsqidsd.hodgepodge.http.download;

import android.content.Context;
import android.content.Intent;
import java.io.File;

public class DownloadHelper {
    public static final String TAG = "DownloadHelper";
    private volatile static DownloadHelper SINGLETANCE;
    private RequestInfo requestInfo;

    private DownloadHelper() {
    }


    public static DownloadHelper getInstance() {
        if (SINGLETANCE == null) {
            synchronized (DownloadHelper.class) {
                if (SINGLETANCE == null) {
                    SINGLETANCE = new DownloadHelper();
                }
            }
        }
        return SINGLETANCE;
    }

    public synchronized void submit(Context context) {
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(InnerConstant.Inner.SERVICE_INTENT_EXTRA, requestInfo);
        context.startService(intent);
    }


    public DownloadHelper addTask(String url, File file, String action) {
        requestInfo = createRequest(url, file, action, InnerConstant.Request.loading);

        return this;
    }

    public DownloadHelper pauseTask(String url, File file, String action) {
        requestInfo = createRequest(url, file, action, InnerConstant.Request.pause);
        return this;
    }


    private RequestInfo createRequest(String url, File file, String action, int dictate) {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setDictate(dictate);
        requestInfo.setDownloadInfo(new DownloadInfo(url, file, action));
        return requestInfo;
    }

}
