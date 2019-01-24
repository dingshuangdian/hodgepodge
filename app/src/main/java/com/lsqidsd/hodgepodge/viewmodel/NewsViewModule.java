package com.lsqidsd.hodgepodge.viewmodel;

import android.content.Context;
import android.util.Log;

import com.lsqidsd.hodgepodge.base.BaseConstant;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
public class NewsViewModule {
    private Context context;
    private List<String> top = new ArrayList<>();
    public NewsViewModule(Context context) {
        this.context = context;
    }
    public void getHotKey(HasFinish finish) {
        try {
            Document document = Jsoup.connect(BaseConstant.SEARCH_URL).get();
            Elements elements = document.select("ol.news-recommend").first().select("li").select("a");
            for (Element element : elements) {
                top.add(element.text());
            }
            if (finish != null) {
                finish.hasFinish(top);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public interface HasFinish {
        void hasFinish(List<String> list);
    }

}
