package com.lsqidsd.hodgepodge.utils;

import com.lsqidsd.hodgepodge.bean.CategoriesBean;

import java.util.ArrayList;
import java.util.List;

public class CategoriesUtils {
    private static List<CategoriesBean> categoriesBeans=null;
    public static List<CategoriesBean> getCategories() {
        categoriesBeans.add(new CategoriesBean("要闻"));
        categoriesBeans.add(new CategoriesBean("视频"));
        categoriesBeans.add(new CategoriesBean("推荐"));
        categoriesBeans.add(new CategoriesBean("娱乐"));
        categoriesBeans.add(new CategoriesBean("军事"));
        categoriesBeans.add(new CategoriesBean("体育"));
        categoriesBeans.add(new CategoriesBean("国际"));
        categoriesBeans.add(new CategoriesBean("财经"));
        return categoriesBeans;
    }
}
