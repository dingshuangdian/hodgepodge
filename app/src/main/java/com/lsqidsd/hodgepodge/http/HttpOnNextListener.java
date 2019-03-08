package com.lsqidsd.hodgepodge.http;
public interface HttpOnNextListener<T> {
    /**
     * 成功后回调方法
     *
     * @param t
     */
    void onSuccess(T t);

    void onFail(String e);

}
