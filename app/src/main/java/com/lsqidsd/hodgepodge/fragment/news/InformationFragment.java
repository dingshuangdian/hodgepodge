package com.lsqidsd.hodgepodge.fragment.news;

import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.YWAdapter;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.databinding.InformationDataBinding;
import com.lsqidsd.hodgepodge.viewmodel.newsitemmodel.NewsItemModel;

public class InformationFragment extends Fragment implements NewsItemModel.ItemNewsDataListener {
    private InformationDataBinding fragmentBinding;
    private NewsItemModel informationViewModel;
    private static InformationFragment informationFragment;

    public static InformationFragment getInstance() {
        informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", "");
        informationFragment.setArguments(bundle);
        return informationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.information_fragment, container, false);
        fragmentBinding.swipeRefreshLayout.setColorSchemeColors(Color.parseColor("#98a2a9"));
        return fragmentBinding.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getData();
        fragmentBinding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
                fragmentBinding.swipeRefreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (fragmentBinding.swipeRefreshLayout != null && fragmentBinding.swipeRefreshLayout.isRefreshing()) {
                            fragmentBinding.swipeRefreshLayout.setRefreshing(false);
                        }

                    }
                }, 1000);
            }
        });
    }

    private void getData() {
        Bundle bundle = getArguments();
        informationViewModel = new NewsItemModel(bundle.getString("url"), getContext(), this);
        fragmentBinding.setInformationview(informationViewModel);
        informationViewModel.getTopNews();
    }

    @Override
    public void dataBeanChange(NewsMain dataBeans) {
        YWAdapter ywAdapter = new YWAdapter(getContext(), dataBeans, getActivity());
        fragmentBinding.recyview.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.recyview.setAdapter(ywAdapter);
    }

}
