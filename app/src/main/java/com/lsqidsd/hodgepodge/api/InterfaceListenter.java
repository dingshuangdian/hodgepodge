package com.lsqidsd.hodgepodge.api;
import android.databinding.ObservableInt;
import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.bean.Milite;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import java.util.List;

public class InterfaceListenter {
    public interface MainNewsDataListener {
        void mainDataChange(NewsMain dataBeans);
    }

    public interface VideosDataListener {
        void videoDataChange(List<NewsVideoItem.DataBean> dataBean);
    }
    public interface NewsItemListener {
        void newsItemDataChange(List<NewsItem.DataBean> dataBean);
    }

    public interface ItemShowListener {
        void itemShow(ObservableInt... observableInt);
    }

    public interface HotNewsDataListener {
        void hotDataChange(List<NewsHot.DataBean> dataBeans);
    }

    public interface VideosLoadFinish {
        void videosLoadFinish(List<DailyVideos.IssueListBean.ItemListBean> beans, String url);
    }

    public interface HasFinish {
        void hasFinish(List<String> list);
    }

    public interface LoadCategoriesNews {
        void loadCategoriesNewsFinish(List<Milite.DataBean> dataBeans,String categorie);
    }
}
