package com.lsqidsd.hodgepodge.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.VideoListAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.bean.DailyVideos;
import com.lsqidsd.hodgepodge.databinding.VideosFragmentBinding;
import com.lsqidsd.hodgepodge.diyview.videoview.JZMediaManager;
import com.lsqidsd.hodgepodge.diyview.videoview.Jzvd;
import com.lsqidsd.hodgepodge.diyview.videoview.JzvdMgr;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;

import java.util.ArrayList;
import java.util.List;


public class VideosFragment extends Fragment implements InterfaceListenter.VideosLoadFinish {
    private VideosFragmentBinding videosFragmentBinding;
    private VideoListAdapter adapter;
    private List<DailyVideos.IssueListBean.ItemListBean> videosList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videosFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        videosFragmentBinding.recyview.setLayoutManager(linearLayoutManager);
        initRefresh();
        videosFragmentBinding.recyview.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                //子view显示界面调用
            }
            @Override
            public void onChildViewDetachedFromWindow(View view) {
                //子view离开界面调用
                Jzvd jzvd = view.findViewById(R.id.video);
                if (jzvd != null && jzvd.jzDataSource.containsTheUrl(JZMediaManager.getCurrentUrl())) {
                    Jzvd currentJzvd = JzvdMgr.getCurrentJzvd();

                    if (currentJzvd != null && currentJzvd.currentScreen != Jzvd.SCREEN_WINDOW_FULLSCREEN) {
                        Jzvd.releaseAllVideos();
                    }
                }
            }
        });
        return videosFragmentBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    private void initRefresh() {
        videosFragmentBinding.refreshLayout.setOnRefreshListener(a -> loadData());
        videosFragmentBinding.refreshLayout.autoRefresh();
    }

    private void loadData() {
        HttpModel.getDailyVideos(videosList, this::videosLoadFinish, videosFragmentBinding.refreshLayout, BaseConstant.VIDEO_URL, "refresh");
    }

    @Override
    public void videosLoadFinish(List<DailyVideos.IssueListBean.ItemListBean> beans, String url) {
        videosList = beans;
        for (int i = 0; i < beans.size(); i++) {
        }
        adapter = new VideoListAdapter(beans, getContext(), videosFragmentBinding.refreshLayout, url);
        videosFragmentBinding.recyview.setAdapter(adapter);
    }
    @Override
    public void onPause() {
        super.onPause();
        Jzvd.releaseAllVideos();
    }
}
