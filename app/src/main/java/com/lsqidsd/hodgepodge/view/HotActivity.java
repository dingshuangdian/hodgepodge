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
    private ActivityHotBinding activityHotBinding;
    private InterfaceListenter.HotNewsDataListener hotNewsDataListener;
    private ActivityHotAdapter adapter;
    List<NewsHot.DataBean> dataBeans = new ArrayList<>();
    @Override
    public int getLayout() {
        return R.layout.activity_hot;
    }

    @Override
    public void initView() {
        hotNewsDataListener = this;
        hotViewModule = new HotViewModule(this);
        activityHotBinding = getBinding(activityHotBinding);
        activityHotBinding.lv.tv.setText("热点精选");
        activityHotBinding.lv.toolbar.setNavigationOnClickListener(a -> finish());
        activityHotBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ActivityHotAdapter(this, activityHotBinding.refreshLayout);
        activityHotBinding.setHotview(hotViewModule);
        activityHotBinding.refreshLayout.setOnRefreshListener(a -> refresh());
        activityHotBinding.refreshLayout.autoRefresh();
    }

    private void refresh() {
        HttpModel.getActivityHotNews(this,0, hotNewsDataListener, dataBeans, activityHotBinding.refreshLayout);
    }

    @Override
    public void hotDataChange(List<NewsHot.DataBean> dataBeans) {
        adapter.addHot(dataBeans);
        activityHotBinding.rv.setAdapter(adapter);
    }
}
