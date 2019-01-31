package com.lsqidsd.hodgepodge.viewmodel.basemodel;

import android.content.Context;

public class BaseModule {
    private Context context;
    public String title;

    public BaseModule(Context context) {
        this.context = context;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
}
