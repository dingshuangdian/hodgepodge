package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;
import android.util.Log;
import com.lsqidsd.hodgepodge.http.download.HttpDownOnNextListener;
import com.lsqidsd.hodgepodge.http.download.Info;

public class MineViewModule {
    private Info info;
    private Context context;


    public MineViewModule(Info info, Context context) {
        this.info = info;
        this.context = context;
        info.setListener(listener);
    }

    public int getMax() {
        return (int) info.getCountLength();
    }

    public int getProgress() {
        return (int) info.getReadLength();
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
        public void updateProgress(long readLength, long countLength) {
            Log.e("readLength", readLength + "");
            Log.e("countLength", countLength + "");
        }
    };


}
