package com.lsqidsd.hodgepodge.http.download;

public interface ProgressListener {
    /**
     * 下载进度
     */
    void updata(long read, long count,boolean done);
}
