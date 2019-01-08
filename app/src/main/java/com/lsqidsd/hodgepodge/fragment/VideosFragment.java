package com.lsqidsd.hodgepodge.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.databinding.VideosFragmentBinding;

public class VideosFragment extends Fragment {
    private VideosFragmentBinding videosFragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        videosFragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_videos, container, false);
        return videosFragmentBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
