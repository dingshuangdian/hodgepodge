package com.lsqidsd.hodgepodge.fragment.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.VideoViewAdapter;
import com.lsqidsd.hodgepodge.adapter.YWAdapter;
import com.lsqidsd.hodgepodge.api.InterfaceListenter;
import com.lsqidsd.hodgepodge.bean.NewsMain;
import com.lsqidsd.hodgepodge.bean.NewsVideoItem;
import com.lsqidsd.hodgepodge.databinding.HeadFreshBinding;
import com.lsqidsd.hodgepodge.databinding.InformationDataBinding;
import com.lsqidsd.hodgepodge.diyview.rfview.RefreashRecyclerView;
import com.lsqidsd.hodgepodge.viewmodel.HttpModel;

import java.util.ArrayList;
import java.util.List;

public class InformationFragment extends Fragment implements InterfaceListenter.MainNewsDataListener, InterfaceListenter.VideosDataListener {
    private InformationDataBinding fragmentBinding;
    private InterfaceListenter.VideosDataListener videosDataListener;
    private InterfaceListenter.MainNewsDataListener newsDataListener;
    private static InformationFragment informationFragment;
    private List<NewsVideoItem.DataBean> videosList = new ArrayList<>();
    private HeadFreshBinding freshBinding;
    private View mFooterView;
    private View mHeaderView;

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
        videosDataListener = this;
        newsDataListener = this;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        fragmentBinding.recyview.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        fragmentBinding.recyview.setLayoutManager(linearLayoutManager);

        return fragmentBinding.getRoot();
    }

    private void refresh() {
        Toast.makeText(getContext(), "刷新完成", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHeaderView = LayoutInflater.from(this.getContext()).inflate(R.layout.head_fresh, fragmentBinding.recyview, false);
        mFooterView = LayoutInflater.from(this.getContext()).inflate(R.layout.head_fresh, fragmentBinding.recyview, false);
        fragmentBinding.recyview.setHeaderView(mHeaderView);
        fragmentBinding.recyview.setFooterView(mFooterView);
        fragmentBinding.recyview.setOnRefreshListener(() -> refresh());

        changeFragment();
    }

    private void changeFragment() {

        Bundle bundle = getArguments();
        switch (bundle.getInt("flag")) {
            case 0:
                HttpModel.getTopNews(newsDataListener, new NewsMain());
                break;
            case 1:
                HttpModel.getVideoList(0, videosDataListener, videosList);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
            case 6:
                break;
            case 7:
                break;
        }
    }

    @Override
    public void mainDataChange(NewsMain dataBeans) {
        YWAdapter ywAdapter = new YWAdapter(getContext(), dataBeans);
        fragmentBinding.recyview.setAdapter(ywAdapter);
    }

    @Override
    public void videoDataChange(List<NewsVideoItem.DataBean> dataBean) {
        VideoViewAdapter viewAdapter = new VideoViewAdapter(dataBean, getContext());
        fragmentBinding.recyview.setAdapter(viewAdapter);

    }
}
