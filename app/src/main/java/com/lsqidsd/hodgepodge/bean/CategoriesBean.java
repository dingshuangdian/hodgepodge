package com.lsqidsd.hodgepodge.bean;

import java.io.Serializable;

public class CategoriesBean implements Serializable {
    private String title;
    public CategoriesBean(String title) {
        this.title = title;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
}
