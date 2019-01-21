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
import com.lsqidsd.hodgepodge.adapter.YWAdapter;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.databinding.InformationDataBinding;
import com.lsqidsd.hodgepodge.viewmodel.newsitemmodel.NewsItemModel;

import java.util.List;

public class InformationFragment extends Fragment implements NewsItemModel.ItemNewsDataListener {
    private InformationDataBinding fragmentBinding;
    private NewsItemModel informationViewModel;
    private static InformationFragment informationFragment;

    public static InformationFragment getInstance(String url) {
        informationFragment = new InformationFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        informationFragment.setArguments(bundle);
        return informationFragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.information_fragment, container, false);
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

    }

    private void getData() {
        Bundle bundle = getArguments();
        informationViewModel = new NewsItemModel(bundle.getString("url"), getContext(), this);
        fragmentBinding.setInformationview(informationViewModel);
        informationViewModel.getNewsData(0);
    }

    @Override
    public void dataBeanChange(List<NewsItem.DataBean> dataBeans) {
        YWAdapter ywAdapter = new YWAdapter(getContext(), dataBeans);
        fragmentBinding.recyview.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentBinding.recyview.setAdapter(ywAdapter);
    }
}
