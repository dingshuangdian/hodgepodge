package com.lsqidsd.hodgepodge.base;

import com.lsqidsd.hodgepodge.bean.NewsItem;

import java.util.List;

public interface OnWriteDataFinishListener {
    void onSuccess(List<NewsItem.DataBean> list);

    void onFault();
}
