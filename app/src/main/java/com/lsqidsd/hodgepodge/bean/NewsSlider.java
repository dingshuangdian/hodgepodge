package com.lsqidsd.hodgepodge.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NewsSlider {
    @Id(autoincrement = true)
    private Long id;
    @Unique
    private String href;
    @Unique
    private String img_url;
    @Unique
    private String img_title;
    @Generated(hash = 2078464698)
    public NewsSlider(Long id, String href, String img_url, String img_title) {
        this.id = id;
        this.href = href;
        this.img_url = img_url;
        this.img_title = img_title;
    }
    @Generated(hash = 655838571)
    public NewsSlider() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getHref() {
        return this.href;
    }
    public void setHref(String href) {
        this.href = href;
    }
    public String getImg_url() {
        return this.img_url;
    }
    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
    public String getImg_title() {
        return this.img_title;
    }
    public void setImg_title(String img_title) {
        this.img_title = img_title;
    }

}
