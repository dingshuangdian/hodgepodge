package com.lsqidsd.hodgepodge.http;

import com.lsqidsd.hodgepodge.bean.NewsItem;

/**
 * Created by 眼神 on 2018/3/27.
 */
public interface OnSuccessAndFaultListener {
    void onSuccess(NewsItem result);

    void onFault(String errorMsg);
}
