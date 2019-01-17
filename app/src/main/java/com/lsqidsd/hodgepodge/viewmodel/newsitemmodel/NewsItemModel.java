package com.lsqidsd.hodgepodge.viewmodel.newsitemmodel;

import android.content.Context;
import android.databinding.BindingAdapter;
import android.text.TextUtils;
import android.widget.ImageView;

import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsItemModel {
    private Context context;
    private NewsItem.DataBean dataBean;

    public NewsItemModel(Context context, NewsItem.DataBean dataBean) {
        this.context = context;
        this.dataBean = dataBean;
    }

    public NewsItem.DataBean getDataBean() {
        return dataBean;
    }

    public String getAuthor() {
        return dataBean.getSource();
    }

    public String getTime() {
        return formatTime(dataBean.getPublish_time());
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

    public void setDataBean(NewsItem.DataBean dataBean) {
        this.dataBean = dataBean;
    }

    private String formatTime(String time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long day = 0;
        long hour = 0;
        long minute = 0;
        long second = 0;
        try {
            Date begin = sdf.parse(time);
            long thisTime = System.currentTimeMillis();
            long between = (thisTime - begin.getTime()) / 1000;//除以1000是为了转换成秒
            day = between / (24 * 3600);
            hour = between % (24 * 3600) / 3600;
            minute = between % 3600 / 60;
            second = between % 60 / 60;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (hour == 0) {
            return minute + "分钟前";
        } else {
            return hour + "小时前";
        }
    }
}
