package com.lsqidsd.hodgepodge.view;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.ActivityHotAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.databinding.ActivityHotBinding;
import com.lsqidsd.hodgepodge.viewmodel.HotViewModule;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;

import java.util.ArrayList;
import java.util.List;

public class HotActivity extends BaseActivity implements InterfaceListenter.HotNewsDataListener {
    private HotViewModule hotViewModule;
    private HttpModel httpModel;
    private List<NewsHot.DataBean> hotList = new ArrayList<>();
    private ActivityHotBinding activityHotBinding;

    @Override
    public int getLayout() {
        return R.layout.activity_hot;
    }

    @Override
    public void initView() {
        httpModel = new HttpModel();
        hotViewModule = new HotViewModule(this);
        activityHotBinding = getBinding(activityHotBinding);
        activityHotBinding.lv.tv.setText("热点精选");
        activityHotBinding.lv.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activityHotBinding.setHotview(hotViewModule);
        httpModel.getActivityHotNews(0, this, hotList);
    }

    @Override
    public void hotDataChange(List<NewsHot.DataBean> dataBeans) {
        ActivityHotAdapter adapter = new ActivityHotAdapter(this, dataBeans);
        activityHotBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        activityHotBinding.rv.setAdapter(adapter);
    }
}
