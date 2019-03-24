package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.databinding.ObservableInt;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.Milite;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.databinding.NewsItemHotBinding;
import com.lsqidsd.hodgepodge.utils.Jump;
import com.lsqidsd.hodgepodge.utils.TimeUtil;

import java.util.List;

public class HotViewModule<T> {
    private Context mContext;
    public ObservableInt imgfVisbility = new ObservableInt(View.VISIBLE);
    public ObservableInt gvVisbility = new ObservableInt(View.GONE);
    public ObservableInt commentVisibility = new ObservableInt(View.GONE);
    private List<String> imagsUrl;
    private NewsHot.DataBean dataBean;
    private Milite.DataBean milites;
    private boolean flag = true;

    public HotViewModule(Context mContext) {
        this.mContext = mContext;
    }

    public HotViewModule(Context mContext, List<String> imagsUrl, T t, NewsItemHotBinding hotBinding) {
        this.mContext = mContext;
        this.imagsUrl = imagsUrl;
        if (t instanceof NewsHot.DataBean) {
            this.dataBean = (NewsHot.DataBean) t;
            flag = true;
        }
        if (t instanceof Milite.DataBean) {
            this.milites = (Milite.DataBean) t;
            flag = false;
        }
        if (imagsUrl != null && imagsUrl.size() == 3) {
            hotBinding.gv.setVisibility(View.VISIBLE);
            hotBinding.ivImage.setVisibility(View.GONE);
        } else {
            hotBinding.gv.setVisibility(View.GONE);
            hotBinding.ivImage.setVisibility(View.VISIBLE);
        }
    }

    public String getTitle() {
        if (flag) {
            return dataBean.getTitle();
        } else {
            return milites.getTitle();
        }
    }

    public String getTime() {
        if (flag) {
            return TimeUtil.formatTime(dataBean.getPublish_time());
        } else {
            return TimeUtil.formatTime(milites.getPublish_time());
        }
    }

    public String getAuthor() {
        if (flag) {
            return dataBean.getSource();
        } else {
            return milites.getSource();
        }

    }

    public String getUrl() {
        if (flag) {
            return dataBean.getUrl();
        } else {
            return milites.getUrl();
        }

    }


    public String getComment() {
        if (flag) {
            if (dataBean.getComment_num() == 0) {
                commentVisibility.set(View.GONE);
            } else {
                commentVisibility.set(View.VISIBLE);
            }
            return dataBean.getComment_num() + "评";
        } else {
            if (milites.getComment_num() == 0) {
                commentVisibility.set(View.GONE);
            } else {
                commentVisibility.set(View.VISIBLE);
            }
            return milites.getComment_num() + "评";
        }


    }

    public String getImageeUrl() {
        if (imagsUrl != null) {
            return imagsUrl.get(0);
        } else if (flag) {
            return dataBean.getImg();
        } else {
            return milites.getImg();
        }
    }

    @BindingAdapter({"imageeUrl"})
    public static void setImageeUrl(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Glide.with(imageView.getContext()).load(imageUrl).into(imageView);
        }
    }

    public void click(View view) {
        switch (view.getId()) {
            case R.id.view_03:
                Jump.jumpToWebActivity(mContext, getUrl());
                break;
        }
    }
}
