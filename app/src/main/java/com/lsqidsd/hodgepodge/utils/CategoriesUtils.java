package com.lsqidsd.hodgepodge.utils;

import com.lsqidsd.hodgepodge.bean.CategoriesBean;

import java.util.ArrayList;
import java.util.List;

public class CategoriesUtils {
    private static List<CategoriesBean> categoriesBeans = new ArrayList<>();

    public static List<CategoriesBean> getCategories() {
        categoriesBeans.add(new CategoriesBean("要闻", "ch/milite/"));
        categoriesBeans.add(new CategoriesBean("国际", "ch/world/"));
        return categoriesBeans;
    }
}
