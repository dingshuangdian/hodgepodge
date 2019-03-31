package com.lsqidsd.hodgepodge.http.download;

import java.io.Serializable;

public class RequestInfo implements Serializable {

    private int dictate;//下载状态标记
    private DownloadInfo downloadInfo;

    public int getDictate() {
        return dictate;
    }

    public void setDictate(int dictate) {
        this.dictate = dictate;
    }

    public DownloadInfo getDownloadInfo() {
        return downloadInfo;
    }

    public void setDownloadInfo(DownloadInfo downloadInfo) {
        this.downloadInfo = downloadInfo;
    }
}
