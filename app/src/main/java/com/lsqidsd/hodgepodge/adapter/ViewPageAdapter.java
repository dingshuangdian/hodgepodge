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
import com.lsqidsd.hodgepodge.databinding.RvhMoreBinding;
import com.lsqidsd.hodgepodge.utils.JsonUtils;
import com.lsqidsd.hodgepodge.utils.TimeUtil;
import com.lsqidsd.hodgepodge.view.WebViewActivity;
import com.squareup.picasso.Picasso;
import java.util.List;
public class ViewPageAdapter extends PagerAdapter {
    private Context context;
    private List<NewsHot.DataBean> newsHotList;
    private LayoutInflater layoutInflater;
    private ViewLoadFinish viewLoadFinish;

    public ViewPageAdapter(Context context, List<NewsHot.DataBean> newsHotList, ViewLoadFinish viewLoadFinish) {
        this.context = context;
        this.newsHotList = newsHotList;
        this.layoutInflater = LayoutInflater.from(context);
        this.viewLoadFinish = viewLoadFinish;
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
        if (position < newsHotList.size()) {
            Image01Binding binding = DataBindingUtil.inflate(layoutInflater, R.layout.image_01, container, false);
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
            container.addView(binding.getRoot());
            return binding.getRoot();
        } else {
            RvhMoreBinding rvhMoreBinding = DataBindingUtil.inflate(layoutInflater, R.layout.rvh_more, container, false);
            container.addView(rvhMoreBinding.getRoot());
            if (viewLoadFinish != null) {
                viewLoadFinish.viewLoadFinish(rvhMoreBinding);
            }
            return rvhMoreBinding.getRoot();
        }
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    public interface ViewLoadFinish {
        void viewLoadFinish(RvhMoreBinding moreBinding);

    }
}
