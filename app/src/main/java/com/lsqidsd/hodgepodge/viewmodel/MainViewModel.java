package com.lsqidsd.hodgepodge.viewmodel;
import com.lsqidsd.hodgepodge.DataManager.CategoryDataManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;

public class MainViewModel {
    CategoryDataManager categoryDataManager = new CategoryDataManager();

    public void getHtml1() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Document doc = null;
                try {
                    doc = Jsoup.connect("https://www.ifeng.com/").timeout(10000).get();
                    categoryDataManager.getCategoriesBeans(doc);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
