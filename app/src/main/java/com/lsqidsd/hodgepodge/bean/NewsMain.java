package com.lsqidsd.hodgepodge.bean;

import java.io.Serializable;
import java.util.List;

public class NewsMain implements Serializable {

    private List<NewsTop.DataBean> newsTops;
    private List<NewsItem.DataBean> newsItems;
    private List<NewsHot.DataBean> newsHot;

    public List<NewsHot.DataBean> getNewsHot() {
        return newsHot;
    }

    public void setNewsHot(List<NewsHot.DataBean> newsHot) {
        this.newsHot = newsHot;
    }

    public NewsMain() {
    }

    public List<NewsTop.DataBean> getNewsTops() {
        return newsTops;
    }

    public void setNewsTops(List<NewsTop.DataBean> newsTops) {
        this.newsTops = newsTops;

    }

    public List<NewsItem.DataBean> getNewsItems() {
        return newsItems;
    }

    public void setNewsItems(List<NewsItem.DataBean> newsItems) {
        this.newsItems = newsItems;
    }
}
