package com.lsqidsd.hodgepodge.bean;

import java.io.Serializable;
import java.util.List;

public class NewsMain implements Serializable {

    public List<NewsTop> newsTops;
    public List<NewsItem.DataBean> newsItems;

    public NewsMain(List<NewsTop> newsTops, List<NewsItem.DataBean> newsItems) {
        this.newsTops = newsTops;
        this.newsItems = newsItems;
    }
    public NewsMain() {
    }

    public List<NewsTop> getNewsTops() {
        return newsTops;
    }

    public void setNewsTops(List<NewsTop> newsTops) {
        this.newsTops = newsTops;
    }
    public List<NewsItem.DataBean> getNewsItems() {
        return newsItems;
    }
    public void setNewsItems(List<NewsItem.DataBean> newsItems) {
        this.newsItems = newsItems;
    }
}
