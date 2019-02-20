package com.lsqidsd.hodgepodge.bean;


import java.util.List;

public class AdRelVideos {
    private List<AdVideos.ItemListBean.DataBean> list;

    public List<AdVideos.ItemListBean.DataBean> getList() {
        return list;
    }

    public void setList(List<AdVideos.ItemListBean.DataBean> list) {
        this.list = list;
    }

    private AdVideos.ItemListBean.DataBean dataBean;

    public AdVideos.ItemListBean.DataBean getDataBean() {
        return dataBean;
    }

    public void setDataBean(AdVideos.ItemListBean.DataBean dataBean) {
        this.dataBean = dataBean;
    }
}
