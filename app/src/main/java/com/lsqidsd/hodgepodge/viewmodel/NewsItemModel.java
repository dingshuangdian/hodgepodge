package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.bean.NewsTop;
import com.lsqidsd.hodgepodge.databinding.OtherBinding;
import com.lsqidsd.hodgepodge.utils.Jump;
import com.lsqidsd.hodgepodge.utils.TimeUtil;
import com.lsqidsd.hodgepodge.view.HotActivity;

import java.util.List;
import java.util.Random;

public class NewsItemModel<T> {
    private Context context;
    public ObservableInt commentVisibility = new ObservableInt(View.GONE);
    public ObservableInt commentTopVisibility = new ObservableInt(View.GONE);
    public ObservableInt imgVisbility = new ObservableInt(View.VISIBLE);
    public ObservableInt roundVisbility = new ObservableInt(View.GONE);
    public ObservableInt vpVisbility = new ObservableInt(View.GONE);
    private NewsItem.DataBean dataBean;
    private NewsTop.DataBean newsTop;
    private List<String> imgUrl;
    private int[] radom = {0, 8};

    public NewsItemModel(Context context, NewsTop.DataBean newsTop) {
        this.context = context;
        this.newsTop = newsTop;

    }

    public NewsItemModel(Context context, NewsItem.DataBean dataBean, List<String> imgUrl, OtherBinding otherBinding) {
        this.context = context;
        this.imgUrl = imgUrl;
        this.dataBean = dataBean;
        if (imgUrl != null && imgUrl.size() == 3) {
            otherBinding.gv.setVisibility(View.VISIBLE);
            otherBinding.ivImage.setVisibility(View.GONE);
        } else {
            otherBinding.gv.setVisibility(View.GONE);
            otherBinding.ivImage.setVisibility(View.VISIBLE);
        }


    }

    public NewsItemModel(Context context, InterfaceListenter.ItemShowListener itemShowListener) {
        Random random = new Random();
        int x = radom[random.nextInt(radom.length)];
        roundVisbility.set(x);
        if (roundVisbility.get() == 0) {
            vpVisbility.set(View.GONE);
        } else {
            vpVisbility.set(View.VISIBLE);
        }
        if (itemShowListener != null) {
            itemShowListener.itemShow(roundVisbility, vpVisbility);
        }
        this.context = context;
    }

    public String getAuthor() {
        return dataBean.getSource();
    }

    public String getTopAuthor() {
        return newsTop.getSource();
    }

    public String getComment() {
        if (dataBean.getComment_num() == 0) {
            commentVisibility.set(View.GONE);
        } else {
            commentVisibility.set(View.VISIBLE);
        }
        return dataBean.getComment_num() + "评";
    }

    public String getTopComment() {
        if (newsTop.getComments() == 0) {
            commentTopVisibility.set(View.GONE);
        } else {
            commentTopVisibility.set(View.VISIBLE);
        }
        return newsTop.getComments() + "评";
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.view_01:
                Jump.jumpToWebActivity(context, getTopUrl());
                break;
            case R.id.view_03:
                Jump.jumpToWebActivity(context, getUrl());
                break;
            case R.id.vf:
                Jump.jumpToNormalActivity(context, HotActivity.class);
                break;
        }
    }

    public String getTopUrl() {
        return newsTop.getUrl();
    }

    public String getUrl() {
        return dataBean.getUrl();
    }

    public String getTopTitle() {
        return newsTop.getTitle();
    }

    public String getTime() {
        return TimeUtil.formatTime(dataBean.getPublish_time());
    }

    @BindingAdapter({"imageUrl"})
    public static void setImageView(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
        }
    }

    @BindingAdapter({"topImageUrl"})
    public static void setTopImageView(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
        }
    }

    public String getTopImageUrl() {
        if (!newsTop.getFlag().isEmpty()) {
            imgVisbility.set(View.VISIBLE);
        } else {
            imgVisbility.set(View.GONE);
        }
        return newsTop.getThumbnails().get(0);
    }

    public String getImageUrl() {
        if (imgUrl != null) {
            return imgUrl.get(0);
        } else {
            return dataBean.getBimg();
        }
    }

    public String getTitle() {
        return dataBean.getTitle();
    }


}
