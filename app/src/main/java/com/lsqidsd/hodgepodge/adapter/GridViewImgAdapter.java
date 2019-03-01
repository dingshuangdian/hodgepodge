package com.lsqidsd.hodgepodge.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.bumptech.glide.Glide;
import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseApplication;
import com.lsqidsd.hodgepodge.databinding.ImageBinding;
import com.lsqidsd.hodgepodge.databinding.NewsItemHotBinding;
import com.lsqidsd.hodgepodge.databinding.OtherBinding;
import com.lsqidsd.hodgepodge.utils.Jump;

import java.util.List;

public class GridViewImgAdapter<T> extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private Context context;
    private String murl;
    private OtherBinding otherBinding;
    private NewsItemHotBinding newsItemHotBinding;
    private List<String> stringList;

    public GridViewImgAdapter(Context mContext) {
        context = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }

    public void addImgs(List<String> imageString, String url, T binding) {
        this.stringList = imageString;
        this.murl = url;
        if (binding instanceof OtherBinding) {
            otherBinding = (OtherBinding) binding;
            otherBinding.gv.setVisibility(View.VISIBLE);
            otherBinding.ivImage.setVisibility(View.GONE);
        }
        if (binding instanceof NewsItemHotBinding) {
            newsItemHotBinding = (NewsItemHotBinding) binding;
            newsItemHotBinding.gv.setVisibility(View.VISIBLE);
            newsItemHotBinding.ivImage.setVisibility(View.GONE);

        }
    }

    @Override
    public int getCount() {
        return stringList.size();
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
        Glide.with(BaseApplication.getmContext()).load(stringList.get(i)).into(imageBinding.ivImage);

        imageBinding.imgView.setOnClickListener(a -> Jump.jumpToWebActivity(context, murl));
        return imageBinding.getRoot();
    }

}
