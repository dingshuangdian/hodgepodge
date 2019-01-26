package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.databinding.ImageBinding;
import com.lsqidsd.hodgepodge.view.WebViewActivity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

public class GridViewImgAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private JSONArray imgpath;
    private Context context;
    private String url;

    public GridViewImgAdapter(JSONArray list, String mUrl, Context mContext) {
        this.imgpath = list;
        context = mContext;
        url = mUrl;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return imgpath.length();
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
        ImageBinding imageBinding = DataBindingUtil.inflate(layoutInflater, R.layout.image, viewGroup, false);
        try {
            Picasso.get().load(imgpath.get(i).toString()).into(imageBinding.ivImage);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        imageBinding.imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WebViewActivity.class);
                intent.putExtra("url", url);
                context.startActivity(intent);
            }
        });
        return imageBinding.getRoot();
    }
}
