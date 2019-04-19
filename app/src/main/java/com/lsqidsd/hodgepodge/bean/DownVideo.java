package com.lsqidsd.hodgepodge.bean;

import com.lsqidsd.hodgepodge.http.download.DwonProgress;

public class DownVideo {
    private String title;
    private String img;
    private long size;
    private int progress;
    private DwonProgress dwonProgress;

    public DownVideo(String title, String img, long size, int progress) {
        this.title = title;
        this.img = img;
        this.size = size;
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public DwonProgress getDwonProgress() {
        return dwonProgress;
    }

    public void setDwonProgress(DwonProgress dwonProgress) {
        this.dwonProgress = dwonProgress;
    }
}
