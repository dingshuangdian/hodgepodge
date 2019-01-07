package com.lsqidsd.hodgepodge.fragment.base;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.databinding.BaseFragmentBinding;
import com.lsqidsd.hodgepodge.viewmodel.basemodel.BaseFragmentModel;

public class BaseFragment extends Fragment implements ViewPager.OnPageChangeListener {
    private BaseFragmentBinding fragmentBinding;
    private BaseFragmentModel baseFragmentModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.base_fragment, container, false);
        baseFragmentModel = new BaseFragmentModel(getActivity().getApplicationContext());
        fragmentBinding.setBasefragmentview(baseFragmentModel);
        fragmentBinding.rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                fragmentBinding.viewpager.setCurrentItem(i);
            }
        });

        return fragmentBinding;
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
