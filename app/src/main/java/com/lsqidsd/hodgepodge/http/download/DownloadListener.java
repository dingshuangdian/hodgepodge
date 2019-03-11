package com.lsqidsd.hodgepodge.http.download;

public interface DownloadListener {
    void onStartDownload(long length);

    void onProgress(int progress);

    void onFail(String error);
}
