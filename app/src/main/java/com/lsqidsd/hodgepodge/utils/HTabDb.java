package com.lsqidsd.hodgepodge.utils;

import com.lsqidsd.hodgepodge.bean.HTab;

import java.util.ArrayList;
import java.util.List;

public class HTabDb {
    private static final List<HTab> topTip = new ArrayList<>();

    static {
        topTip.add(new HTab("要闻"));
        topTip.add(new HTab("视频"));
        topTip.add(new HTab("推荐"));
        topTip.add(new HTab("娱乐"));
        topTip.add(new HTab("军事"));
        topTip.add(new HTab("体育"));
        topTip.add(new HTab("法制"));
        topTip.add(new HTab("国际"));

    }


    public static List<HTab> getTopTip() {
        return topTip;
    }


}
