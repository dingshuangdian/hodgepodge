package com.lsqidsd.hodgepodge.viewmodel;

import android.util.Log;

import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.base.OnWriteDataFinishListener;
import com.lsqidsd.hodgepodge.bean.CategoriesBean;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;
import com.lsqidsd.hodgepodge.utils.BaseDataDao;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
import okhttp3.ResponseBody;

public class MainViewModel {
    public void getMainViewData(DisposableObserver<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = RetrofitServiceManager.getInstance().getHttpApi().getAllNews(BaseConstant.BASE_URL);
        RetrofitServiceManager.getInstance().toSubscribe(observable, subscriber);
    }

    public void getCategoriesBeans(Document document, final OnWriteDataFinishListener writeDataFinishListener) {
        Log.e("document+++", document.outerHtml());
        Elements elements = document.select("ul.main-list fl").select("li").select("a");
        Log.e("elements", elements.toString());
        for (Element element : elements) {
            CategoriesBean categoriesBean = new CategoriesBean();
            categoriesBean.setUrl(element.attr("href"));
            categoriesBean.setTitle(element.text());
            BaseDataDao.insertData(categoriesBean);
        }
        if (BaseDataDao.queryAllData(CategoriesBean.class).size() > 0) {
            writeDataFinishListener.onSuccess();
        }
    }

    public void getData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(BaseConstant.BASE_URL).get();
                    Log.e("document", document.outerHtml());
                    Elements elements=document.select("div.qq_channel").select(".qq_content").select(".cf").select(".slide-wrap").select(".main").select(".fl")
                           ;
                    Elements elements1=elements.select("div#List").next();
                    Log.e("elements+++++++++",elements1.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        }).start();
    }
}
