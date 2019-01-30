package com.lsqidsd.hodgepodge.view;

import android.databinding.DataBindingUtil;
import android.view.LayoutInflater;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.base.BaseActivity;
import com.lsqidsd.hodgepodge.databinding.ActivityHotBinding;
import com.lsqidsd.hodgepodge.databinding.TopViewBinding;
import com.lsqidsd.hodgepodge.viewmodel.HotViewModule;
import com.lsqidsd.hodgepodge.viewmodel.basemodel.BaseModule;

public class HotActivity extends BaseActivity {
    private HotViewModule hotViewModule;
    private BaseModule baseModule;
    private TopViewBinding topViewBinding;
    private ActivityHotBinding activityHotBinding;

    @Override
    public int getLayout() {
        return R.layout.activity_hot;
    }

    @Override
    public void initView() {
        activityHotBinding = getBinding(activityHotBinding);
        topViewBinding = DataBindingUtil.setContentView(this, R.layout.top_view);
        baseModule = new BaseModule(this);
        baseModule.setTitle("热点精选");
        hotViewModule = new HotViewModule(this);
        activityHotBinding.setHotview(hotViewModule);
        topViewBinding.setTopview(baseModule);
        hotViewModule.getHotNews(0);
    }

}
