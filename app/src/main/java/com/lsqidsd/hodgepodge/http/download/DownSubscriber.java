package com.lsqidsd.hodgepodge.http.download;

import android.os.Handler;

import com.lsqidsd.hodgepodge.http.RxHttpManager;

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
    public void updata(long read, long count, boolean down) {
        if (info.getCountLength() > count) {
            read = info.getCountLength() - count + read;
        } else {
            info.setCountLength(count);
        }
        info.setReadLength(read);
        if (listener.get() == null) return;
        handler.post(() -> {
            if (info.getState() == State.PAUSE || info.getState() == State.STOP) return;
            info.setState(State.DOWN);
            listener.get().updateProgress(info.getReadLength(), info.getCountLength());
        });

    }

    @Override
    public void onNext(T t) {
        if (listener.get() != null) {
            listener.get().onNext(t);
        }

    }

    @Override
    public void onError(Throwable t) {
        if (listener.get() != null) {
            listener.get().onError(t);
        }
        RxHttpManager.getInstance().remove(info);
        info.setState(State.ERROR);
        DaoUtil.getInstance().updata(info);

    }

    @Override
    public void onComplete() {
        if (listener.get() != null) {
            listener.get().onComplete();
        }
        RxHttpManager.getInstance().remove(info);
        info.setState(State.END);
        DaoUtil.getInstance().updata(info);

    }
}
