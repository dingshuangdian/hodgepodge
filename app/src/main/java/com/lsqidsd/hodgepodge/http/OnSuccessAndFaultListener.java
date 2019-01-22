package com.lsqidsd.hodgepodge.http;


public interface OnSuccessAndFaultListener<T> {
    void onSuccess(T t);
    void onFault(String errorMsg);
}
