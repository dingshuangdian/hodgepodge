package com.lsqidsd.hodgepodge.base;


import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public abstract class BaseLazyFragment<T extends ViewDataBinding> extends Fragment {
    /**
     * @param isVisibleToUser
     */
    public boolean inInit = false;
    public boolean isLoad = false;
    private LayoutInflater mInflater;
    private ViewGroup mContainer;
    private T t;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        mContainer = container;
        if (t != null && t.getRoot() != null) {
            return t.getRoot();
        } else {
            initFragment();
            return t.getRoot();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        inInit = true;
        initData();
        isCanLoadData();
    }
    public T setBinding(T k) {
        k = DataBindingUtil.inflate(mInflater, setContentView(), mContainer, false);
        t = k;
        return k;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    public abstract void initFragment();
    public abstract void initData();

    private void isCanLoadData() {
        if (!inInit) {
            return;
        }
        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        inInit = false;
        isLoad = false;
    }

    public abstract int setContentView();

    public abstract void lazyLoad();

    public void stopLoad() {

    }
}
