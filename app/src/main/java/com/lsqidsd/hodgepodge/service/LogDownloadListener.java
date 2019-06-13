package com.lsqidsd.hodgepodge.service;

import com.lsqidsd.hodgepodge.http.downserver.DownloadListener;
import com.lsqidsd.hodgepodge.http.downserver.model.Progress;

import java.io.File;

public class LogDownloadListener extends DownloadListener {

    public LogDownloadListener() {
        super("LogDownloadListener");
    }

    @Override
    public void onStart(Progress progress) {
        System.out.println("onStart: " + progress);
    }

    @Override
    public void onProgress(Progress progress) {
        System.out.println("onProgress: " + progress);
    }

    @Override
    public void onError(Progress progress) {
        System.out.println("onError: " + progress);
        progress.exception.printStackTrace();
    }

    @Override
    public void onFinish(File file, Progress progress) {
        System.out.println("onFinish: " + progress);
    }

    @Override
    public void onRemove(Progress progress) {
        System.out.println("onRemove: " + progress);
    }
}
