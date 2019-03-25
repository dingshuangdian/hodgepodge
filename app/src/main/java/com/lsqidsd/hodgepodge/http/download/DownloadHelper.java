package com.lsqidsd.hodgepodge.http.download;

import android.content.Context;
import android.content.Intent;

import com.lsqidsd.hodgepodge.service.DownLoadService;

import java.io.File;
import java.util.ArrayList;

public class DownloadHelper {
    public static final String TAG = "DownloadHelper";
    private volatile static DownloadHelper SINGLETANCE;
    private static ArrayList<RequestInfo> requestInfos = new ArrayList<>();

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
        if (requestInfos.isEmpty()) {
            return;
        }
        Intent intent = new Intent(context, DownloadService.class);
        intent.putExtra(InnerConstant.Inner.SERVICE_INTENT_EXTRA, requestInfos);
        context.startService(intent);
        requestInfos.clear();
    }


    public DownloadHelper addTask(String url, File file, String action) {
        RequestInfo requestInfo = createRequest(url, file, action, InnerConstant.Request.loading);
        requestInfos.add(requestInfo);
        return this;
    }

    public DownloadHelper pauseTask(String url, File file, String action) {
        RequestInfo requestInfo = createRequest(url, file, action, InnerConstant.Request.pause);
        requestInfos.add(requestInfo);
        return this;
    }


    private RequestInfo createRequest(String url, File file, String action, int dictate) {
        RequestInfo requestInfo = new RequestInfo();
        requestInfo.setDictate(dictate);
        requestInfo.setDownloadInfo(new DownloadInfo(url, file, action));
        return requestInfo;
    }

}
