package com.lsqidsd.hodgepodge.api;

/**
 * <br/>
 * 滑动过度的监听接口
 */
public interface OverScrollListener {

    /**
     * 滑动过度时调用的方法
     *
     * @param dy 每毫秒滑动的距离
     */
    void overScrollBy(int dy);

}
