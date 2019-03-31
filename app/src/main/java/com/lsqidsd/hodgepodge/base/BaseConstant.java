package com.lsqidsd.hodgepodge.base;

import java.util.HashMap;

public class BaseConstant {
    public static String BASE_URL = "https://pacaio.match.qq.com/";
    public static String VIDEO_URL = "http://baobab.kaiyanapp.com/api/v2/feed";
    public static String UPDATA_URL = "https://imtt.dd.qq.com/";
    public static String DOWNLOAD_URL = " http://baobab.kaiyanapp.com/";
    public static HashMap<String, String> params = new HashMap<>();

    //军事
    public static HashMap<String, String> getMiliteParams() {
        params.put("cid", "58");
        params.put("token", "c232b098ee7611faeffc46409e836360");
        params.put("ext", "milite_pc");
        return params;
    }

    //娱乐
    public static HashMap<String, String> getEntParams() {
        params.put("cid", "146");
        params.put("token", "49cbb2154853ef1a74ff4e53723372ce");
        params.put("ext", "ent");
        return params;
    }

    //国际
    public static HashMap<String, String> getWorldParams() {
        params.put("cid", "146");
        params.put("token", "49cbb2154853ef1a74ff4e53723372ce");
        params.put("ext", "world");
        return params;
    }

    //财经
    public static HashMap<String, String> getFinanceParams() {
        params.put("cid", "146");
        params.put("token", "49cbb2154853ef1a74ff4e53723372ce");
        params.put("ext", "finance");
        return params;
    }

    //推荐
    public static HashMap<String, String> getRecommend() {
        params.put("cid", "5");
        params.put("token", "76c3bd898a05c21886b3552c49eaae3b");
        return params;
    }

    //历史
    public static HashMap<String, String> getHistory() {
        params.put("cid", "135");
        params.put("token", "6e92c215fb08afa901ac31eca115a34f");
        params.put("ext", "history");
        params.put("num", "15");
        return params;
    }
    //文化

    public static HashMap<String, String> getCul() {
        params.put("cid", "146");
        params.put("token", "49cbb2154853ef1a74ff4e53723372ce");
        params.put("ext", "cul");
        return params;
    }

    /**
     * Jsoup
     */
    public static String SEARCH_URL = "https://s.weibo.com/top/summary?cate=realtimehot";

}