package com.lsqidsd.hodgepodge.fragment;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.VideoListAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.bean.AdVideos;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.databinding.VideosFragmentBinding;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;

import java.util.ArrayList;
import java.util.List;

public class VideosFragment extends Fragment implements InterfaceListenter.VideosLoadFinish {
    private VideosFragmentBinding videosFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videosFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        videosFragmentBinding.recyview.setLayoutManager(linearLayoutManager);

        initRefresh();

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
        List<AdVideos> videosList = new ArrayList<>();
        HttpModel.getVideo(0, videosList, this::videosLoadFinish, videosFragmentBinding.refreshLayout);
    }

    @Override
    public void videosLoadFinish(List<AdVideos> beans) {

        VideoListAdapter adapter = new VideoListAdapter(beans, getContext(), videosFragmentBinding.refreshLayout);
        videosFragmentBinding.recyview.setAdapter(adapter);
    }
}
