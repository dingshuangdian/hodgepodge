package com.lsqidsd.hodgepodge.view;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.ActivityHotAdapter;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.bean.NewsHot;
import com.lsqidsd.hodgepodge.databinding.ActivityHotBinding;
import com.lsqidsd.hodgepodge.viewmodel.HotViewModule;

import java.util.List;

public class HotActivity extends BaseActivity implements HotViewModule.ItemNewsDataListener {
    private HotViewModule hotViewModule;
    private ActivityHotBinding activityHotBinding;

    @Override
    public int getLayout() {
        return R.layout.activity_hot;
    }

    @Override
    public void initView() {
        hotViewModule = new HotViewModule(this, this);
        activityHotBinding = getBinding(activityHotBinding);
        activityHotBinding.lv.tv.setText("热点精选");
        activityHotBinding.lv.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        activityHotBinding.setHotview(hotViewModule);
        hotViewModule.getHotNews(0);
    }

    @Override
    public void dataBeanChange(List<NewsHot.DataBean> dataBeans) {
        ActivityHotAdapter adapter = new ActivityHotAdapter(this, dataBeans);
        activityHotBinding.rv.setLayoutManager(new LinearLayoutManager(this));
        activityHotBinding.rv.setAdapter(adapter);
    }
}
