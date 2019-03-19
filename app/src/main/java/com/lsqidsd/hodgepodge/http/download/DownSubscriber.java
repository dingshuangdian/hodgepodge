package com.lsqidsd.hodgepodge.http.download;

import android.os.Handler;

import java.lang.ref.WeakReference;

import io.reactivex.observers.DisposableObserver;

public class DownSubscriber<T> extends DisposableObserver<T> implements ProgressListener {
    private WeakReference<HttpDownOnNextListener> listener;
    private Info info;
    private Handler handler;

    public DownSubscriber(Info info, Handler handler) {
        this.listener = new WeakReference<>(info.getListener());
        this.info = info;
        this.handler = handler;
    }

    public void setInfo(Info info) {
        this.listener = new WeakReference<>(info.getListener());
        this.info = info;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (listener.get() != null) {
            listener.get().onStart();
        }
        info.setState(State.START);
    }

    @Override
    public void updata(long read, long count) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {
        if (listener.get() != null) {
            listener.get().onComplete();
        }

    }
}
