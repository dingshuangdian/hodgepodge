package com.lsqidsd.hodgepodge.http.download;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;

public class ProgressDownSubscriber<T> extends DisposableObserver<T> implements DownloadProgressListener {
    //弱引用结果回调
    private WeakReference<HttpProgressOnNextListener> mSubscriberOnNextListener;
    private DownInfo downInfo;

    public ProgressDownSubscriber(WeakReference<HttpProgressOnNextListener> mSubscriberOnNextListener, DownInfo downInfo) {
        this.mSubscriberOnNextListener = mSubscriberOnNextListener;
        this.downInfo = downInfo;
    }

    /**
     * 订阅开始时调用
     * 显示ProgressDialog
     */

    @Override
    protected void onStart() {
        super.onStart();
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onStart();
        }
        downInfo.setState(DownState.START);
    }

    @Override
    public void updata(long read, long count, boolean done) {
        if (downInfo.getCountLength() > count) {
            read = downInfo.getCountLength() - count + read;
        } else {
            downInfo.setCountLength(count);
        }
        downInfo.setReadLength(read);
        if (mSubscriberOnNextListener.get() != null) {
            Observable.just(read).observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Long>() {
                        @Override
                        public void accept(Long aLong) throws Exception {
                            if (downInfo.getState() == DownState.PAUSE || downInfo.getState() == DownState.STOP)
                                return;
                            downInfo.setState(DownState.DOWN);
                            mSubscriberOnNextListener.get().updataProgress(aLong, downInfo.getCountLength());
                        }
                    });
        }


    }

    /**将onNext方法中的返回结果交给Activity或Fragment自己处理
     * @param t
     */

    @Override
    public void onNext(T t) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onNext(t);
        }

    }

    /**
     * 对错误进行统一处理
     *
     * @param e
     */

    @Override
    public void onError(Throwable e) {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onError(e);
        }
        downInfo.setState(DownState.ERROR);

    }

    /**
     * 下载完成，隐藏ProgressDialog
     */

    @Override
    public void onComplete() {
        if (mSubscriberOnNextListener.get() != null) {
            mSubscriberOnNextListener.get().onComplete();
        }
        downInfo.setState(DownState.FINISH);

    }
}
