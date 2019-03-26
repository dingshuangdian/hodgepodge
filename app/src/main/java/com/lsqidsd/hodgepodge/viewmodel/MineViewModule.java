package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;

import com.lsqidsd.hodgepodge.databinding.MineFragmentBinding;
import com.lsqidsd.hodgepodge.http.RxHttpManager;


public class MineViewModule {

    private Context context;
    private RxHttpManager rxHttpManager;
    private MineFragmentBinding fragmentBinding;
    public MineViewModule(Context context, MineFragmentBinding binding) {
        this.context = context;
        rxHttpManager = RxHttpManager.getInstance();
        fragmentBinding = binding;
    }
}
