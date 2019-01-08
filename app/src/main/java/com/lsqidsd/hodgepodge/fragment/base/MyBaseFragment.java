package com.lsqidsd.hodgepodge.fragment.base;

import android.support.v4.app.Fragment;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.databinding.BaseFragmentBinding;
import com.lsqidsd.hodgepodge.viewmodel.basemodel.BaseFragmentModel;

public class MyBaseFragment extends Fragment {
    private BaseFragmentBinding fragmentBinding;
    private BaseFragmentModel baseFragmentModel;
   public static MyBaseFragment newInstance(String title) {
        MyBaseFragment myBaseFragment = new MyBaseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title", title);
        myBaseFragment.setArguments(bundle);
        return myBaseFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        fragmentBinding = DataBindingUtil.inflate(inflater, R.layout.base_fragment, container, false);
        baseFragmentModel = new BaseFragmentModel(getActivity().getApplicationContext());
        fragmentBinding.setBasefragmentview(baseFragmentModel);

        return fragmentBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        fragmentBinding.tv.setText(bundle.getString("title"));
    }
}
