package com.lsqidsd.hodgepodge.viewmodel.newsitemmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.utils.TimeUtil;
import com.squareup.picasso.Picasso;
public class NewsItemModel {
    private Context context;
    private NewsItem.DataBean dataBean;
    public NewsItemModel(Context context, NewsItem.DataBean dataBean) {
        this.context = context;
        this.dataBean = dataBean;
    }
    public String getAuthor() {
        return dataBean.getSource();
    }
    public String getTime() {
        return TimeUtil.formatTime(dataBean.getPublish_time());
    }
    @BindingAdapter({"imageUrl"})
    public static void setImageView(ImageView imageView, String imageUrl) {
        if (!TextUtils.isEmpty(imageUrl)) {
            Picasso.get().load(imageUrl).into(imageView);
        }

    }
    public String getImageUrl() {
        return dataBean.getBimg();
    }
    public String getTitle() {
        return dataBean.getTitle();
    }
}
