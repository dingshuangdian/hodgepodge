package com.lsqidsd.hodgepodge.http.download;

import java.io.File;
import java.io.Serializable;

public class DownloadInfo implements Serializable {
    private String url;
    private File file;
    private DwonProgress dwonProgress;


    private String action;//广播接收者的各种行为


    public DownloadInfo(String url, File file, String action) {
        this.url = url;
        this.file = file;
        this.action = action;
    }

    public DwonProgress getDwonProgress() {
        return dwonProgress;
    }

    public void setDwonProgress(DwonProgress dwonProgress) {
        this.dwonProgress = dwonProgress;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUniqueId() {
        return url + file.getAbsolutePath();
    }
}
