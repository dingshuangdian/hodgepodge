package com.lsqidsd.hodgepodge.bean;

public class NewsTop {
    private int comment_num;
    private String source;
    private String title;
    private String publish_time;
    private String url;
    private String bimg;


    public NewsTop(int comment_num, String source, String title, String publish_time, String url, String bimg) {
        this.comment_num = comment_num;
        this.source = source;
        this.title = title;
        this.publish_time = publish_time;
        this.url = url;
        this.bimg = bimg;
    }

    public int getComment_num() {
        return comment_num;
    }

    public void setComment_num(int comment_num) {
        this.comment_num = comment_num;
    }

    public String getBimg() {
        return bimg;
    }

    public void setBimg(String bimg) {
        this.bimg = bimg;
    }
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublish_time() {
        return publish_time;
    }

    public void setPublish_time(String publish_time) {
        this.publish_time = publish_time;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
