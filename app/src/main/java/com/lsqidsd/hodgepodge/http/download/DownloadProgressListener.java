package com.lsqidsd.hodgepodge.http.download;

public interface DownloadProgressListener {
    /**
     * 下载进度监听
     *
     * @param read
     * @param count
     * @param done
     */

    void updata(long read, long count, boolean done);
}
