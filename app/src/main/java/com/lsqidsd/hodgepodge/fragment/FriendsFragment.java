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
import com.lsqidsd.hodgepodge.databinding.FriendsFragmentBinding;
import com.lsqidsd.hodgepodge.viewmodel.FriendsViewModule;

public class FriendsFragment extends Fragment {
    private FriendsFragmentBinding fragmentBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_friends, container, false);
        return fragmentBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
