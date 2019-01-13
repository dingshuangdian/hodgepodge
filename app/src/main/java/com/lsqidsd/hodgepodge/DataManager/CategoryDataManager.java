package com.lsqidsd.hodgepodge.DataManager;

import android.util.Log;

import com.lsqidsd.hodgepodge.bean.CategoriesBean;
import com.lsqidsd.hodgepodge.utils.BaseDataDao;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class CategoryDataManager {
    public static void getCategoriesBeans(Document document) {
        Elements elements = document.select("ul.clearfix").first().select("li").select("a");
        Log.e("elements", elements.toString());
        for (Element element : elements) {
            CategoriesBean categoriesBean = new CategoriesBean();
            categoriesBean.setUrl(element.attr("href"));
            categoriesBean.setTitle(element.text());
            BaseDataDao.insertData(categoriesBean);
        }
    }
}
