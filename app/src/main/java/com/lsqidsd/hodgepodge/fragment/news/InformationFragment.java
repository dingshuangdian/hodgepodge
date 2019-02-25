package com.lsqidsd.hodgepodge.fragment.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.VideoViewAdapter;
import com.lsqidsd.hodgepodge.adapter.YWAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.databinding.InformationDataBinding;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;

import java.util.ArrayList;
import java.util.List;

public class InformationFragment extends Fragment implements InterfaceListenter.MainNewsDataListener, InterfaceListenter.VideosDataListener {
    private InformationDataBinding fragmentBinding;
    private static InformationFragment informationFragment;

    public static InformationFragment getInstance(int i) {
        informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("flag", i);
        informationFragment.setArguments(bundle);
        return informationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.information_fragment, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        //fragmentBinding.recyview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL_LIST));
        fragmentBinding.recyview.setLayoutManager(linearLayoutManager);
        initRefresh();
        return fragmentBinding.getRoot();
    }

    private void initRefresh() {
        fragmentBinding.refreshLayout.setOnRefreshListener(a -> loadData());
        fragmentBinding.refreshLayout.autoRefresh();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        fragmentBinding.refreshLayout.finishRefresh();
    }

    @Override
    public void videoDataChange(List<NewsVideoItem.DataBean> dataBean) {
        VideoViewAdapter viewAdapter = new VideoViewAdapter(dataBean, getContext(), fragmentBinding.refreshLayout);
        fragmentBinding.recyview.setAdapter(viewAdapter);
        fragmentBinding.refreshLayout.finishRefresh();
    }
}
