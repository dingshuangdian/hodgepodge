package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;

import com.lsqidsd.hodgepodge.databinding.MineFragmentBinding;
import com.lsqidsd.hodgepodge.http.RxHttpManager;
import com.lsqidsd.hodgepodge.http.download.HttpDownOnNextListener;
import com.lsqidsd.hodgepodge.http.download.Info;

public class MineViewModule {
    private Info info;
    private Context context;
    private RxHttpManager rxHttpManager;
    private MineFragmentBinding fragmentBinding;


    public MineViewModule(Info info, Context context, MineFragmentBinding binding) {
        this.info = info;
        this.context = context;
        rxHttpManager = RxHttpManager.getInstance();
        fragmentBinding = binding;
        fragmentBinding.numberProgressBar.setMax((int) info.getCountLength());
        fragmentBinding.numberProgressBar.setProgress((int) info.getReadLength());
        info.setListener(listener);
    }

    HttpDownOnNextListener<Info> listener = new HttpDownOnNextListener<Info>() {
        @Override
        public void onNext(Info o) {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onComplete() {

        }

        @Override
        public void onPause() {
            super.onPause();
            RxHttpManager.getInstance().pause(info);
        }

        @Override
        public void updateProgress(long readLength, long countLength) {
            fragmentBinding.numberProgressBar.setProgress((int) readLength);
            fragmentBinding.numberProgressBar.setMax((int) countLength);
        }
    };
}
