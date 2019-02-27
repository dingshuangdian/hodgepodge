package com.lsqidsd.hodgepodge.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.VideoViewAdapter;
import com.lsqidsd.hodgepodge.adapter.YWAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseLazyFragment;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.databinding.InformationDataBinding;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;
import java.util.ArrayList;
import java.util.List;
public class InformationFragment extends BaseLazyFragment implements InterfaceListenter.MainNewsDataListener, InterfaceListenter.VideosDataListener {
    private InformationDataBinding fragmentBinding;
    private static InformationFragment informationFragment;
    public static InformationFragment getInstance(int i) {
        informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", i);
        informationFragment.setArguments(bundle);
        return informationFragment;
    }
    private void initRefresh() {
        fragmentBinding.refreshLayout.setOnRefreshListener(a -> loadData());
        fragmentBinding.refreshLayout.autoRefresh();
    }
    @Override
    public void initFragment() {
        fragmentBinding = (InformationDataBinding) setBinding(fragmentBinding);
    }
    @Override
    public void initData() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentBinding.recyview.setLayoutManager(linearLayoutManager);
    }
    @Override
    public int setContentView() {
        return R.layout.information_fragment;
    }
    @Override
    public void lazyLoad() {
        initRefresh();
    }
    private void loadData() {
        Bundle bundle = getArguments();
        switch (bundle.getInt("flag")) {
            case 0:
                HttpModel.getMainNewData(this::mainDataChange, fragmentBinding.refreshLayout);
                break;
            case 1:
                List<NewsVideoItem.DataBean> videosList = new ArrayList<>();
                HttpModel.getVideoList(0, this::videoDataChange, videosList, fragmentBinding.refreshLayout);
                break;
            case 2:
                HttpModel.getMainNewData(this::mainDataChange, fragmentBinding.refreshLayout);
                break;
            case 3:
                HttpModel.getMainNewData(this::mainDataChange, fragmentBinding.refreshLayout);
                break;
            case 4:
                HttpModel.getMainNewData(this::mainDataChange, fragmentBinding.refreshLayout);
                break;
            case 5:
                HttpModel.getMainNewData(this::mainDataChange, fragmentBinding.refreshLayout);
                break;
            case 6:
                HttpModel.getMainNewData(this::mainDataChange, fragmentBinding.refreshLayout);
                break;
            case 7:
                HttpModel.getMainNewData(this::mainDataChange, fragmentBinding.refreshLayout);
                break;
        }
    }

    @Override
    public void mainDataChange(NewsMain dataBeans) {
        YWAdapter ywAdapter = new YWAdapter(getContext(), dataBeans, fragmentBinding.refreshLayout);
        fragmentBinding.recyview.setAdapter(ywAdapter);
    }

    @Override
    public void videoDataChange(List<NewsVideoItem.DataBean> dataBean) {
        VideoViewAdapter viewAdapter = new VideoViewAdapter(dataBean, getContext(), fragmentBinding.refreshLayout);
        fragmentBinding.recyview.setAdapter(viewAdapter);
    }
}
