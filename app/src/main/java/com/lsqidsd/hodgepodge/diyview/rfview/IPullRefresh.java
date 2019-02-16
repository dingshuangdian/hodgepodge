package com.lsqidsd.hodgepodge.diyview.rfview;

/**
 * @author xing.hu
 * @since 2018/9/18, 下午6:21
 */
public interface IPullRefresh {
    void pullRefresh();
    void pullRefreshEnable(boolean enable);
    void pullRefreshEnd();

}
