package com.lsqidsd.hodgepodge.viewmodel;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsTop;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultListener;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultSub;
import com.lsqidsd.hodgepodge.http.RetrofitServiceManager;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.List;
import io.reactivex.Observable;
public class HttpModel {
    /**
     * 获取视频列表
     *
     * @param page
     * @param dataListener
     */
    public static void getVideoList(int page, InterfaceListenter.VideosDataListener dataListener, List<NewsVideoItem.DataBean> videoBeans, RefreshLayout refreshLayout) {
        Observable<NewsVideoItem> observable = RetrofitServiceManager.getInstance().getHttpApi().getVideos(page);
        RetrofitServiceManager.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsVideoItem newsVideoItem = (NewsVideoItem) o;
                if (newsVideoItem.getData().size() > 0) {
                    for (NewsVideoItem.DataBean video : newsVideoItem.getData()) {
                        videoBeans.add(video);
                    }
                    if (dataListener != null) {
                        dataListener.videoDataChange(videoBeans);
                        if (page > 0) {
                            refreshLayout.finishLoadMore();
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.resetNoMoreData();
                        }
                    }
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFault(String errorMsg) {
            }
        }));
    }
    /**
     * 获取热点新闻
     *
     * @param page
     * @param listener
     */
    public static void getHotNews(int page, InterfaceListenter.MainNewsDataListener listener, NewsMain newsMain,RefreshLayout refreshLayout) {
        Observable<NewsHot> observable = RetrofitServiceManager.getInstance().getHttpApi().getHotNews(page, 5);
        RetrofitServiceManager.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsHot newsHot = (NewsHot) o;
                for (NewsHot.DataBean hot : newsHot.getData()) {
                    newsMain.getNewsHot().add(hot);
                }
                getNewsData(0, listener, newsMain, refreshLayout);
            }

            @Override
            public void onFault(String errorMsg) {
            }
        }));
    }
    public static void getActivityHotNews(int page, InterfaceListenter.HotNewsDataListener listener, List<NewsHot.DataBean> hotBeans, RefreshLayout refreshLayout) {
        Observable<NewsHot> observable = RetrofitServiceManager.getInstance().getHttpApi().getHotNews(page, 15);
        RetrofitServiceManager.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsHot newsHot = (NewsHot) o;
                if (newsHot.getData().size() > 0) {
                    for (NewsHot.DataBean hot : newsHot.getData()) {
                        hotBeans.add(hot);
                    }
                    if (listener != null) {
                        listener.hotDataChange(hotBeans);
                        if (page > 0) {
                            refreshLayout.finishLoadMore();
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.resetNoMoreData();
                        }
                    }
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFault(String errorMsg) {
            }
        }));
    }
    /**
     * 获取置顶新闻
     *
     * @param listener
     */
    public static void getTopNews(InterfaceListenter.MainNewsDataListener listener, NewsMain newsMain,RefreshLayout refreshLayout) {
        Observable<NewsTop> observable = RetrofitServiceManager.getInstance().getHttpApi().getTopNews(0);
        RetrofitServiceManager.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object o) {
                NewsTop newsTop = (NewsTop) o;
                for (NewsTop.DataBean dataBean : newsTop.getData()) {
                    newsMain.getNewsTops().add(dataBean);
                }
                getHotNews(0, listener, newsMain,refreshLayout);
            }
            @Override
            public void onFault(String errorMsg) {
            }
        }));
    }
    /**
     * 获取新闻数据
     *
     * @param page
     * @param listener
     */
    public static void getNewsData(int page, InterfaceListenter.MainNewsDataListener listener, NewsMain newsMain, RefreshLayout refreshLayout) {
        Observable<NewsItem> observable = RetrofitServiceManager.getInstance().getHttpApi().getMainNews(page);
        RetrofitServiceManager.getInstance().toSubscribe(observable, new OnSuccessAndFaultSub<>(new OnSuccessAndFaultListener() {
            @Override
            public void onSuccess(Object result) {
                NewsItem newsItem = (NewsItem) result;
                if (newsItem.getData().size() > 0) {
                    for (NewsItem.DataBean dataBeann : newsItem.getData()) {
                        newsMain.getNewsItems().add(dataBeann);
                    }
                    if (listener != null) {
                        listener.mainDataChange(newsMain);
                        if (page > 0) {
                            refreshLayout.finishLoadMore();
                        } else {
                            refreshLayout.finishRefresh();
                            refreshLayout.resetNoMoreData();
                        }
                    }
                } else {
                    refreshLayout.finishLoadMoreWithNoMoreData();
                }
            }

            @Override
            public void onFault(String errorMsg) {
            }
        }));
    }

    /**
     * 抓取头部热点滚动
     *
     * @param finish
     * @param top
     */
    public static void getHotKey(InterfaceListenter.HasFinish finish, List<String> top) {
        try {
            Document document = Jsoup.connect(BaseConstant.SEARCH_URL).get();
            Elements elements = document.select("td").select("a");
            for (Element element : elements) {
                top.add(element.text());
            }
            if (finish != null) {
                finish.hasFinish(top);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
