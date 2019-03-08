package com.lsqidsd.hodgepodge.http.download;

public class DownInfo {
    private String savePath;
    private String url;
    private String baseUrl;
    private long countLength;
    private long readLength;
    private int DEFAULT_TIMEOUT = 6;
    /**
     * 下载状态
     */
    private DownState state;

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public void setBaseUrl(String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public long getCountLength() {
        return countLength;
    }

    public void setCountLength(long countLength) {
        this.countLength = countLength;
    }

    public long getReadLength() {
        return readLength;
    }

    public void setReadLength(long readLength) {
        this.readLength = readLength;
    }

    public int getDEFAULT_TIMEOUT() {
        return DEFAULT_TIMEOUT;
    }

    public void setDEFAULT_TIMEOUT(int DEFAULT_TIMEOUT) {
        this.DEFAULT_TIMEOUT = DEFAULT_TIMEOUT;
    }

    public DownState getState() {
        return state;
    }

    public void setState(DownState state) {
        this.state = state;
    }
}
