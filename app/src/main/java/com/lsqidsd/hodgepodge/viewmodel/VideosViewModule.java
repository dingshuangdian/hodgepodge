package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.utils.TimeUtil;
import com.lsqidsd.hodgepodge.view.WebViewActivity;
import com.squareup.picasso.Picasso;

public class VideosViewModule {
    private NewsVideoItem.DataBean videos;
    private Context context;

    public VideosViewModule(NewsVideoItem.DataBean videos, Context context) {
        this.videos = videos;
        this.context = context;
    }

    public String getTitle() {
        return videos.getTitle();
    }

    public String getAuthor() {
        return videos.getSource();
    }

    public String getTime() {
        return TimeUtil.formatTime(videos.getPublish_time());
    }

    public String getImageView() {
        return videos.getBimg();
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
        Intent intent = new Intent(context, WebViewActivity.class);
        intent.putExtra("url", videos.getUrl());
        context.startActivity(intent);

    }
}
