package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.lsqidsd.hodgepodge.bean.AdVideos;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.utils.Jump;
import com.lsqidsd.hodgepodge.utils.TimeUtil;
import com.squareup.picasso.Picasso;

public class VideosViewModule<T> {
    private NewsVideoItem.DataBean videos;
    private Context context;
    private boolean thisVideo;
    private AdVideos.ItemListBean adVieos;
    public ObservableInt show = new ObservableInt(View.VISIBLE);
    public ObservableInt unshow = new ObservableInt(View.GONE);

    public VideosViewModule(T videos, Context context) {
        if (videos instanceof NewsVideoItem.DataBean) {
            this.videos = (NewsVideoItem.DataBean) videos;
            thisVideo = true;
        } else if (videos instanceof AdVideos.ItemListBean) {
            this.adVieos = (AdVideos.ItemListBean) videos;
            thisVideo = false;
        }
        this.context = context;
    }


    public String getTitle() {
        if (thisVideo) {
            show.set(View.VISIBLE);
            unshow.set(View.GONE);

            return videos.getTitle();
        } else {
            show.set(View.GONE);
            unshow.set(View.VISIBLE);
            return adVieos.getData().getTitle();
        }

    }

    public String getAuthor() {
        return videos.getSource();
    }

    public String getTime() {
        if (thisVideo) {
            return TimeUtil.formatTime(videos.getPublish_time());
        } else {
            return "#" + adVieos.getData().getCategory() + "  /  " + TimeUtil.formatTime_(adVieos.getData().getDuration());
        }
    }

    public String getImageView() {
        if (thisVideo) {
            return videos.getBimg();
        } else {
            return adVieos.getData().getCover().getFeed();
        }

    }

    public String getViewCount() {
        return videos.getView_count() + "";
    }

    @BindingAdapter({"imageView"})
    public static void setView(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get().load(imageUrl).into(imageView);
        }
    }

    public void toPlay(View view) {
        Jump.jumpToWebActivity(context, videos.getUrl());
    }
}
