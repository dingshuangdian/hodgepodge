package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.databinding.Image01Binding;
import com.lsqidsd.hodgepodge.utils.JsonUtils;
import com.lsqidsd.hodgepodge.utils.TimeUtil;
import com.lsqidsd.hodgepodge.view.WebViewActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ViewPageAdapter extends PagerAdapter {
    private Context context;
    private List<NewsHot.DataBean> newsHotList;
    private LayoutInflater layoutInflater;

    public ViewPageAdapter(Context context, List<NewsHot.DataBean> newsHotList) {
        this.context = context;
        layoutInflater = LayoutInflater.from(context);
        this.newsHotList = newsHotList;
    }

    @Override
    public int getCount() {
        return newsHotList.size() + 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        Image01Binding binding = DataBindingUtil.inflate(layoutInflater, R.layout.image_01, container, false);
        if (position == newsHotList.size()) {
            binding.cl1.setVisibility(View.VISIBLE);
        } else {
            Picasso.get().load(JsonUtils.jsonKey(newsHotList.get(position).getImgs(), 2)).into(binding.ivImage);
            binding.author.setText(newsHotList.get(position).getSource());
            binding.title.setText(newsHotList.get(position).getTitle());
            binding.time.setText(TimeUtil.formatTime(newsHotList.get(position).getPublish_time()));
            binding.ivImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, WebViewActivity.class);
                    intent.putExtra("url", newsHotList.get(position).getUrl());
                    context.startActivity(intent);
                }
            });
            binding.cl1.setVisibility(View.GONE);
        }
        container.addView(binding.getRoot());
        return binding.getRoot();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

}
