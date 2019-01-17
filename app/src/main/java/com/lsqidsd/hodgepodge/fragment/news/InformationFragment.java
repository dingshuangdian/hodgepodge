package com.lsqidsd.hodgepodge.fragment.news;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.adapter.YWAdapter;
import com.lsqidsd.hodgepodge.base.BaseConstant;
import com.lsqidsd.hodgepodge.base.OnWriteDataFinishListener;
import com.lsqidsd.hodgepodge.bean.NewsItem;
import com.lsqidsd.hodgepodge.databinding.InformationDataBinding;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultListener;
import com.lsqidsd.hodgepodge.http.OnSuccessAndFaultSub;
import com.lsqidsd.hodgepodge.view.MainActivity;
import com.lsqidsd.hodgepodge.viewmodel.newsmodel.InformationViewModel;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.List;

public class InformationFragment extends Fragment {
    private InformationDataBinding fragmentBinding;
    private InformationViewModel informationViewModel;
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
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        informationViewModel = new InformationViewModel(bundle.getString("url"));
        fragmentBinding.setInformationview(informationViewModel);
        informationViewModel.getNewsData(new OnWriteDataFinishListener() {
            @Override
            public void onSuccess(List<NewsItem.DataBean> list) {
                YWAdapter adapter = new YWAdapter(getContext(), list);
                fragmentBinding.recyview.setLayoutManager(new LinearLayoutManager(getContext()));
                fragmentBinding.recyview.setAdapter(adapter);

            }
            @Override
            public void onFault() {

            }
        });

    }
}
