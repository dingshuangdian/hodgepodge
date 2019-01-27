package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.databinding.Image01Binding;
import com.lsqidsd.hodgepodge.utils.JsonUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GridViewHotAdapter extends BaseAdapter {
    private List<NewsHot.DataBean> beans;
    private Context context;
    private LayoutInflater layoutInflater;

    public GridViewHotAdapter(List<NewsHot.DataBean> beans, Context context) {
        this.beans = beans;
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return beans.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Image01Binding binding = DataBindingUtil.inflate(layoutInflater, R.layout.image_01, viewGroup, false);
        Picasso.get().load(JsonUtils.jsonKey(beans.get(i).getImgs(),0)).into(binding.ivImage);
        return binding.getRoot();
    }
}
