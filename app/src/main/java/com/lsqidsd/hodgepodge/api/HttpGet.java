package com.lsqidsd.hodgepodge.api;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsTop;
import com.lsqidsd.hodgepodge.http.RxHttpManager;
import com.lsqidsd.hodgepodge.http.download.DownService;
import com.lsqidsd.hodgepodge.http.download.FileInfo;
import java.util.HashMap;
import io.reactivex.Observable;
import io.reactivex.observers.DisposableObserver;
public class HttpGet {
    private static RxHttpManager rxHttpManager = RxHttpManager.getInstance();


    /**
     * 获取视频列表
     */
    public static <T> void getVideo(DisposableObserver<T> subscriber, int page) {
        Observable observable = rxHttpManager.create(HttpApi.class, BaseConstant.BASE_URL).getVideos(page);
        rxHttpManager.subscribe(observable, subscriber);
    }

    /**
     * 获取分类新闻
     */
    public static <T> void getNews(DisposableObserver<T> subscriber, int page, HashMap<String, String> params) {
        Observable observable = rxHttpManager.create(HttpApi.class, BaseConstant.BASE_URL).getMilite(params, page);
        rxHttpManager.subscribe(observable, subscriber);
    }

    /**
     * 获取热点新闻
     */
    public static <T> void getHot(DisposableObserver<T> subscriber, int page) {
        Observable observable = rxHttpManager.create(HttpApi.class, BaseConstant.BASE_URL).getHotNews(page, 15);
        rxHttpManager.subscribe(observable, subscriber);
    }

    /**
     * 获取主页新闻
     */
    public static <T> void getMain(DisposableObserver<T> subscriber) {
        Observable observable1 = rxHttpManager.create(HttpApi.class, BaseConstant.BASE_URL).getTopNews();
        Observable observable2 = rxHttpManager.create(HttpApi.class, BaseConstant.BASE_URL).getHotNews(0, 5);
        Observable observable3 = rxHttpManager.create(HttpApi.class, BaseConstant.BASE_URL).getMainNews(0);
        Observable o = Observable.zip(observable1, observable2, observable3, (a, b, c) -> {
                    NewsMain newsMain = new NewsMain();
                    NewsTop top = (NewsTop) a;
                    NewsHot hot = (NewsHot) b;
                    NewsItem item = (NewsItem) c;
                    for (NewsTop.DataBean dataBean : top.getData()) {
                        newsMain.getNewsTops().add(dataBean);
                    }
                    for (NewsHot.DataBean hotBean : hot.getData()) {
                        newsMain.getNewsHot().add(hotBean);
                    }
                    for (NewsItem.DataBean itemBean : item.getData()) {
                        newsMain.getNewsItems().add(itemBean);
                    }
                    return newsMain;
                }
        );
        rxHttpManager.subscribe(o, subscriber);
    }

    /**
     * 加载首页更多新闻
     */
    public static <T> void getloadNewsData(DisposableObserver<T> subscriber, int page) {
        Observable observable = rxHttpManager.create(HttpApi.class, BaseConstant.BASE_URL).getMainNews(page);
        rxHttpManager.subscribe(observable, subscriber);
    }

    /**
     * app更新
     */
    public static void updata(DisposableObserver observer) {
        Observable observable = rxHttpManager.create(HttpApi.class, BaseConstant.UPDATA_URL).download();
        rxHttpManager.subscribe(observable, observer);
    }

    /**
     * 视频下载
     */
    public static void download(DisposableObserver observer, FileInfo info) {
        Observable observable = rxHttpManager.create(DownService.class, BaseConstant.DOWNLOAD_URL).download("bytes=" + info.getDownloadLocation() + "-", info.getDownloadUrl());
        rxHttpManager.subscribe(observable, observer);
    }
}
