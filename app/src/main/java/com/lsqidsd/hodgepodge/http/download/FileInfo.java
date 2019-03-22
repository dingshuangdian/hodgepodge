package com.lsqidsd.hodgepodge.http.download;

import android.support.annotation.IntRange;

import java.io.Serializable;

public class FileInfo implements Serializable {

    private String id;
    private String downloadUrl;
    private String filePath;
    private long size;
    private long downloadLocation;
    @IntRange(from = DownloadStatus.WAIT, to = DownloadStatus.FAIL)
    private int downloadStatus = DownloadStatus.PAUSE;

    public FileInfo() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getDownloadLocation() {
        return downloadLocation;
    }

    public void setDownloadLocation(long downloadLocation) {
        this.downloadLocation = downloadLocation;
    }

    public int getDownloadStatus() {
        return downloadStatus;
    }

    public void setDownloadStatus(int downloadStatus) {
        this.downloadStatus = downloadStatus;
    }
}
