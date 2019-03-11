package com.lsqidsd.hodgepodge.http.download;

import io.reactivex.observers.DisposableObserver;

public class DownSubscriber<T> extends DisposableObserver implements DownloadProgressListener{
    @Override
    public void update(long read, long count, boolean done) {

    }

    @Override
    public void onNext(Object o) {

    }

    @Override
    public void onError(Throwable e) {

    }

    @Override
    public void onComplete() {

    }
}
