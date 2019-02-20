package com.lsqidsd.hodgepodge.api;

import android.databinding.ObservableInt;

import com.lsqidsd.hodgepodge.bean.AdVideos;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.databinding.RvhMoreBinding;

import java.util.List;

public class InterfaceListenter {
    public interface MainNewsDataListener {
        void mainDataChange(NewsMain dataBeans);
    }

    public interface VideosDataListener {
        void videoDataChange(List<NewsVideoItem.DataBean> dataBean);
    }

    public interface ItemShowListener {
        void itemShow(ObservableInt... observableInt);
    }

    public interface HotNewsDataListener {
        void hotDataChange(List<NewsHot.DataBean> dataBeans);
    }

    public interface ViewLoadFinish {
        void viewLoadFinish(RvhMoreBinding moreBinding);

    }

    public interface VideosLoadFinish {
        void videosLoadFinish(List<AdVideos> beans);
    }

    public interface HasFinish {
        void hasFinish(List<String> list);
    }

    public interface LoadFinish {
        void loadFinish();
    }
}
