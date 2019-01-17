package com.lsqidsd.hodgepodge.viewmodel.newsmodel;


import android.util.Log;

import com.lsqidsd.hodgepodge.base.OnWriteDataFinishListener;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultListener;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultSub;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;

public class InformationViewModel {
    private String url;

    public InformationViewModel(String url) {
        this.url = url;

    }

    public void getNewsData(OnWriteDataFinishListener listener) {
        getMainViewData(new OnSuccessAndFaultSub(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(NewsItem result) {
                if (listener != null) {
                    for (NewsItem.DataBean dataBean : result.getData()) {
                        Log.e("result", dataBean.getTitle());
                    }
                    listener.onSuccess(result.getData());
                }
            }
            @Override
            public void onFault(String errorMsg) {
            }
        }));
    }
    public void getMainViewData(DisposableObserver<NewsItem> subscriber) {
        Observable<NewsItem> observable = RetrofitServiceManager.getInstance().getHttpApi().getMainNews(0);
        RetrofitServiceManager.getInstance().toSubscribe(observable, subscriber);
    }

}
