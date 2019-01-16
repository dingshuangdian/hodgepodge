package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.base.OnWriteDataFinishListener;
import com.lsqidsd.hodgepodge.bean.CategoriesBean;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;
import com.lsqidsd.hodgepodge.utils.BaseDataDao;
import com.lsqidsd.hodgepodge.utils.CategoriesUtils;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class MainViewModel {
    private Context context;

    public MainViewModel(Context context) {
        this.context = context;
    }

    public void getMainViewData(DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitServiceManager.getInstance().getHttpApi().getAllNews(BaseConstant.BASE_URL.concat(CategoriesUtils.getCategories().get(0).getUrl()));
        RetrofitServiceManager.getInstance().toSubscribe(observable, subscriber);
    }

    public void getCategoriesBeans(Document document, final OnWriteDataFinishListener writeDataFinishListener) {



        if (BaseDataDao.queryAllData(CategoriesBean.class).size() > 0) {
            writeDataFinishListener.onSuccess();
        }
    }
}
