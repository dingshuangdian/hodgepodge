package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.utils.Jump;
import com.lsqidsd.hodgepodge.utils.TimeUtil;
import com.lsqidsd.hodgepodge.view.DownloadActivity;

public class VideosViewModule<T> {
    private NewsVideoItem.DataBean videos;
    private static Context context;
    private boolean thisVideo;
    public ObservableInt showVideo = new ObservableInt(View.GONE);
    public ObservableInt showPicture = new ObservableInt(View.GONE);
    private DailyVideos.IssueListBean.ItemListBean adVieos;

    public VideosViewModule(T videos, Context context) {
        if (videos instanceof NewsVideoItem.DataBean) {
            this.videos = (NewsVideoItem.DataBean) videos;
            showVideo.set(View.GONE);
            showPicture.set(View.VISIBLE);
            thisVideo = true;
        } else if (videos instanceof DailyVideos.IssueListBean.ItemListBean) {
            this.adVieos = (DailyVideos.IssueListBean.ItemListBean) videos;
            thisVideo = false;
            showVideo.set(View.VISIBLE);
            showPicture.set(View.GONE);
        }
        this.context = context;
    }


    public String getTitle() {
        if (thisVideo) {
            return videos.getTitle();
        } else {
            return adVieos.getData().getTitle();
        }

    }

    public String getAuthor() {
        if (thisVideo) {
            return videos.getSource();
        } else {
            return adVieos.getData().getAuthor().getName();
        }

    }

    public String getReplyCount() {
        if (thisVideo) {
            return videos.getComment_num() + "";
        } else {
            return adVieos.getData().getConsumption().getReplyCount() + "";
        }
    }

    public String getShareCount() {
        if (thisVideo) {
            return "分享";
        } else {
            return adVieos.getData().getConsumption().getShareCount() + "";
        }
    }

    public String getCollectionCount() {
        if (thisVideo) {
            return TimeUtil.formatNum(videos.getView_count(), false);
        } else {
            return adVieos.getData().getConsumption().getCollectionCount() + "";
        }
    }

    public String getTime() {
        if (thisVideo) {
            return TimeUtil.formatTime(videos.getPublish_time()) + "  |  " + TimeUtil.formatTime_(videos.getDuration());
        } else {
            return adVieos.getData().getCategory() + "  |  " + TimeUtil.formatTime_(adVieos.getData().getDuration());
        }
    }

    public String getImageView() {
        if (thisVideo) {
            return videos.getBimg();
        } else {
            return adVieos.getData().getCover().getFeed();
        }
    }

    public String getImageAuthor() {
        if (thisVideo) {
            return videos.getMedia_icon();
        } else {
            return adVieos.getData().getAuthor().getIcon();
        }
    }

    @BindingAdapter({"imageAuthor"})
    public static void setImageAuthor(ImageView imageAuthor, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(context).load(imageUrl).into(imageAuthor);
        }
    }


    @BindingAdapter({"imageView"})
    public static void setView(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(context).load(imageUrl).into(imageView);
        }
    }

    public void toPlay(View view) {
        switch (view.getId()) {
            case R.id.video_play:
                if (thisVideo) {
                    Jump.jumpToWebActivity(context, videos.getUrl());
                }
                break;
            case R.id.download:
                Jump.jumpToNormalActivity(context, DownloadActivity.class, adVieos.getData().getPlayUrl());
                break;
        }

    }

}
