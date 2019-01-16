package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;


import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.base.OnWriteDataFinishListener;
import com.lsqidsd.hodgepodge.bean.CategoriesBean;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;
import com.lsqidsd.hodgepodge.utils.BaseDataDao;
import com.lsqidsd.hodgepodge.utils.CategoriesUtils;

import org.jsoup.nodes.Document;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class MainViewModel {
    private Context context;

    public MainViewModel(Context context) {
        this.context = context;
    }

    public void getMainViewData(DisposableObserver<NewsItem> subscriber) {

        Observable<NewsItem> observable = RetrofitServiceManager.getInstance().getHttpApi().getMainNews(0);
        RetrofitServiceManager.getInstance().toSubscribe(observable, subscriber);
    }

    public void getCategoriesBeans(Document document, final OnWriteDataFinishListener writeDataFinishListener) {


        if (BaseDataDao.queryAllData(CategoriesBean.class).size() > 0) {
            writeDataFinishListener.onSuccess();
        }
    }
}
