package com.lsqidsd.hodgepodge.fragment;

import android.databinding.DataBindingUtil;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.databinding.VideosFragmentBinding;

public class VideosFragment extends Fragment {
    private VideosFragmentBinding videosFragmentBinding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videosFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false);
        videosFragmentBinding.vv.setVideoPath("http://baobab.kaiyanapp.com/api/v1/playUrl?vid=149816&resourceType=video&editionType=default&source=aliyun");
        videosFragmentBinding.vv.setMediaController(new MediaController(getContext()));
        videosFragmentBinding.vv.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mediaPlayer) {
                videosFragmentBinding.vv.start();
            }
        });
        return videosFragmentBinding.getRoot();
    }
    @Override
    public void onStart() {
        super.onStart();

    }
}
