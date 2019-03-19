package com.lsqidsd.hodgepodge.http.download;

public abstract class HttpDownOnNextListener<T> {


    /**
     * 成功的回调
     */
    public abstract void onNext(T t);

    /**
     * 开始下载
     */
    public abstract void onStart();

    /**
     * 完成下载
     */
    public abstract void onComplete();

    /**
     * 下载进度
     */
    public abstract void updateProgress(long readLength, long countLength);

    /**
     * 失败错误
     */
    public void onError(Throwable e) {

    }

    /**
     * 暂停下载
     */
    public void onPause() {

    }

    /**
     * 停止下载销毁
     */
    public void onStop() {
    }
}
