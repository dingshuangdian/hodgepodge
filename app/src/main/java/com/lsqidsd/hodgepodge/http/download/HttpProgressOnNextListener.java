package com.lsqidsd.hodgepodge.http.download;

/**
 * 下载过程的回调处理
 */

public abstract class HttpProgressOnNextListener<T> {

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
     *
     * @param readLength
     * @param countLenth
     */

    public abstract void updataProgress(long readLength, long countLenth);

    /**
     * 失败或者错误方法调用(非强制)
     *
     * @param e
     */
    public void onError(Throwable e) {

    }

    /**
     * 暂停下载
     */
    public void onPuase() {

    }

    /**
     * 停止下载
     */
    public void onStop() {

    }
}
