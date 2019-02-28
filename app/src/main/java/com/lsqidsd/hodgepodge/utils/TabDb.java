package com.lsqidsd.hodgepodge.utils;

import com.lsqidsd.hodgepodge.R;
import com.lsqidsd.hodgepodge.fragment.FriendsFragment;
import com.lsqidsd.hodgepodge.fragment.MineFragment;
import com.lsqidsd.hodgepodge.fragment.NewsFragment;
import com.lsqidsd.hodgepodge.fragment.VideosFragment;

public class TabDb {
    /**
     * 获得底部所有项
     */

    public static String[] getTabsTxt() {
        String[] tabs = {"首页", "视频", "广场", "我"};
        return tabs;
    }

    public static String[] getTopsTxt() {
        String[] tabs = {"要闻", "视频", "推荐", "娱乐", "军事", "历史", "国际", "财经", "文化"};
        return tabs;
    }

    public static String[] getTopsVideoTxt() {
        String[] tabs = {"每日精选", "发现更多", "热门排行"};
        return tabs;
    }

    /**
     * 获得所有碎片
     */
    public static Class[] getFragment() {
        Class[] cls = {NewsFragment.class, VideosFragment.class, FriendsFragment.class, MineFragment.class};
        return cls;
    }

    /**
     * 获取默认图片
     */
    public static int[] getTabsImg() {
        int[] img = {R.mipmap.news_n, R.mipmap.video_n, R.mipmap.friend_n, R.mipmap.mine_n};
        return img;
    }

    /**
     * 获取选中图片
     */
    public static int[] getTabsLightImg() {
        int[] img = {R.mipmap.news_s, R.mipmap.video_s, R.mipmap.friend_s, R.mipmap.mine_s};
        return img;
    }
}
