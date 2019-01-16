package com.lsqidsd.hodgepodge.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsItem {
    private int code;
    private String msg;
    private int datanum;
    private String seq;
    private int biz;
    private String s_group;
    private Object other;
    private List<DataBean> data;
    public int getCode() {
        return code;
    }
    public void setCode(int code) {
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public int getDatanum() {
        return datanum;
    }
    public void setDatanum(int datanum) {
        this.datanum = datanum;
    }
    public String getSeq() {
        return seq;
    }
    public void setSeq(String seq) {
        this.seq = seq;
    }
    public int getBiz() {
        return biz;
    }
    public void setBiz(int biz) {
        this.biz = biz;
    }
    public String getS_group() {
        return s_group;
    }
    public void setS_group(String s_group) {
        this.s_group = s_group;
    }
    public Object getOther() {
        return other;
    }
    public void setOther(Object other) {
        this.other = other;
    }
    public List<DataBean> getData() {
        return data;
    }
    public void setData(List<DataBean> data) {
        this.data = data;
    }
    public static class DataBean {
        private String app_id;
        private int article_type;
        private String bimg;
        private String category;
        private String category1_chn;
        private String category1_id;
        private String category2_chn;
        private String category2_id;
        private String category_chn;
        private String category_id;
        private String comment_id;
        private int comment_num;
        private int duration;
        private ExtBean ext;
        private int flag;
        private String fm_url;
        private String from;
        private String id;
        private String img;
        private int img_count;
        private ImgsBean imgs;
        private String intro;
        private IrsImgsBean irs_imgs;
        private String keywords;
        private String media_icon;
        private String mini_img;
        private int news_level;
        private String play_url_high;
        private String play_url_medium;
        private String play_url_small;
        private String pool_type;
        private String publish_time;
        private String report;
        private int s_group;
        private String source;
        private int source_fans;
        private String source_id;
        private String source_logo;
        private int strategy;
        private String surl;
        private String tags;
        private String title;
        private int ts;
        private String update_time;
        private String url;
        private int view_count;
        private String vurl;
        private List<String> multi_imgs;
        private List<List<String>> tag_label;

        public String getApp_id() {
            return app_id;
        }

        public void setApp_id(String app_id) {
            this.app_id = app_id;
        }

        public int getArticle_type() {
            return article_type;
        }

        public void setArticle_type(int article_type) {
            this.article_type = article_type;
        }

        public String getBimg() {
            return bimg;
        }

        public void setBimg(String bimg) {
            this.bimg = bimg;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getCategory1_chn() {
            return category1_chn;
        }

        public void setCategory1_chn(String category1_chn) {
            this.category1_chn = category1_chn;
        }

        public String getCategory1_id() {
            return category1_id;
        }

        public void setCategory1_id(String category1_id) {
            this.category1_id = category1_id;
        }

        public String getCategory2_chn() {
            return category2_chn;
        }

        public void setCategory2_chn(String category2_chn) {
            this.category2_chn = category2_chn;
        }

        public String getCategory2_id() {
            return category2_id;
        }

        public void setCategory2_id(String category2_id) {
            this.category2_id = category2_id;
        }

        public String getCategory_chn() {
            return category_chn;
        }

        public void setCategory_chn(String category_chn) {
            this.category_chn = category_chn;
        }

        public String getCategory_id() {
            return category_id;
        }

        public void setCategory_id(String category_id) {
            this.category_id = category_id;
        }

        public String getComment_id() {
            return comment_id;
        }

        public void setComment_id(String comment_id) {
            this.comment_id = comment_id;
        }

        public int getComment_num() {
            return comment_num;
        }

        public void setComment_num(int comment_num) {
            this.comment_num = comment_num;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public ExtBean getExt() {
            return ext;
        }

        public void setExt(ExtBean ext) {
            this.ext = ext;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public String getFm_url() {
            return fm_url;
        }

        public void setFm_url(String fm_url) {
            this.fm_url = fm_url;
        }

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public int getImg_count() {
            return img_count;
        }

        public void setImg_count(int img_count) {
            this.img_count = img_count;
        }

        public ImgsBean getImgs() {
            return imgs;
        }

        public void setImgs(ImgsBean imgs) {
            this.imgs = imgs;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }

        public IrsImgsBean getIrs_imgs() {
            return irs_imgs;
        }

        public void setIrs_imgs(IrsImgsBean irs_imgs) {
            this.irs_imgs = irs_imgs;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getMedia_icon() {
            return media_icon;
        }

        public void setMedia_icon(String media_icon) {
            this.media_icon = media_icon;
        }

        public String getMini_img() {
            return mini_img;
        }

        public void setMini_img(String mini_img) {
            this.mini_img = mini_img;
        }

        public int getNews_level() {
            return news_level;
        }

        public void setNews_level(int news_level) {
            this.news_level = news_level;
        }

        public String getPlay_url_high() {
            return play_url_high;
        }

        public void setPlay_url_high(String play_url_high) {
            this.play_url_high = play_url_high;
        }

        public String getPlay_url_medium() {
            return play_url_medium;
        }

        public void setPlay_url_medium(String play_url_medium) {
            this.play_url_medium = play_url_medium;
        }

        public String getPlay_url_small() {
            return play_url_small;
        }

        public void setPlay_url_small(String play_url_small) {
            this.play_url_small = play_url_small;
        }

        public String getPool_type() {
            return pool_type;
        }

        public void setPool_type(String pool_type) {
            this.pool_type = pool_type;
        }

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
        }

        public String getReport() {
            return report;
        }

        public void setReport(String report) {
            this.report = report;
        }

        public int getS_group() {
            return s_group;
        }

        public void setS_group(int s_group) {
            this.s_group = s_group;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public int getSource_fans() {
            return source_fans;
        }

        public void setSource_fans(int source_fans) {
            this.source_fans = source_fans;
        }

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        public String getSource_logo() {
            return source_logo;
        }

        public void setSource_logo(String source_logo) {
            this.source_logo = source_logo;
        }

        public int getStrategy() {
            return strategy;
        }

        public void setStrategy(int strategy) {
            this.strategy = strategy;
        }

        public String getSurl() {
            return surl;
        }

        public void setSurl(String surl) {
            this.surl = surl;
        }

        public String getTags() {
            return tags;
        }

        public void setTags(String tags) {
            this.tags = tags;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getTs() {
            return ts;
        }

        public void setTs(int ts) {
            this.ts = ts;
        }

        public String getUpdate_time() {
            return update_time;
        }

        public void setUpdate_time(String update_time) {
            this.update_time = update_time;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getView_count() {
            return view_count;
        }

        public void setView_count(int view_count) {
            this.view_count = view_count;
        }

        public String getVurl() {
            return vurl;
        }

        public void setVurl(String vurl) {
            this.vurl = vurl;
        }

        public List<String> getMulti_imgs() {
            return multi_imgs;
        }

        public void setMulti_imgs(List<String> multi_imgs) {
            this.multi_imgs = multi_imgs;
        }

        public List<List<String>> getTag_label() {
            return tag_label;
        }

        public void setTag_label(List<List<String>> tag_label) {
            this.tag_label = tag_label;
        }
        public static class ExtBean {
            private int tonality_score;
            private int news_score;
            private int img_type;
            private int video_count;
            public int getTonality_score() {
                return tonality_score;
            }

            public void setTonality_score(int tonality_score) {
                this.tonality_score = tonality_score;
            }

            public int getNews_score() {
                return news_score;
            }

            public void setNews_score(int news_score) {
                this.news_score = news_score;
            }

            public int getImg_type() {
                return img_type;
            }

            public void setImg_type(int img_type) {
                this.img_type = img_type;
            }

            public int getVideo_count() {
                return video_count;
            }

            public void setVideo_count(int video_count) {
                this.video_count = video_count;
            }
        }

        public static class ImgsBean {
            @SerializedName("227X148")
            private String _$227X148;
            @SerializedName("273X145")
            private String _$273X145;
            @SerializedName("294X195")
            private String _$294X195;
            @SerializedName("580X328")
            private String _$580X328;
            @SerializedName("640X330")
            private String _$640X330;
            @SerializedName("640X470")
            private String _$640X470;
            @SerializedName("870X492")
            private String _$870X492;
            @SerializedName("966X604")
            private String _$966X604;

            public String get_$227X148() {
                return _$227X148;
            }

            public void set_$227X148(String _$227X148) {
                this._$227X148 = _$227X148;
            }

            public String get_$273X145() {
                return _$273X145;
            }

            public void set_$273X145(String _$273X145) {
                this._$273X145 = _$273X145;
            }

            public String get_$294X195() {
                return _$294X195;
            }

            public void set_$294X195(String _$294X195) {
                this._$294X195 = _$294X195;
            }

            public String get_$580X328() {
                return _$580X328;
            }

            public void set_$580X328(String _$580X328) {
                this._$580X328 = _$580X328;
            }

            public String get_$640X330() {
                return _$640X330;
            }

            public void set_$640X330(String _$640X330) {
                this._$640X330 = _$640X330;
            }

            public String get_$640X470() {
                return _$640X470;
            }

            public void set_$640X470(String _$640X470) {
                this._$640X470 = _$640X470;
            }

            public String get_$870X492() {
                return _$870X492;
            }

            public void set_$870X492(String _$870X492) {
                this._$870X492 = _$870X492;
            }

            public String get_$966X604() {
                return _$966X604;
            }

            public void set_$966X604(String _$966X604) {
                this._$966X604 = _$966X604;
            }
        }

        public static class IrsImgsBean {
            @SerializedName("227X148")
            private List<String> _$227X148;
            @SerializedName("273X145")
            private List<String> _$273X145;
            @SerializedName("294X195")
            private List<String> _$294X195;
            @SerializedName("580X328")
            private List<String> _$580X328;
            @SerializedName("640X330")
            private List<String> _$640X330;
            @SerializedName("640X470")
            private List<String> _$640X470;
            @SerializedName("870X492")
            private List<String> _$870X492;
            @SerializedName("966X604")
            private List<String> _$966X604;

            public List<String> get_$227X148() {
                return _$227X148;
            }

            public void set_$227X148(List<String> _$227X148) {
                this._$227X148 = _$227X148;
            }

            public List<String> get_$273X145() {
                return _$273X145;
            }

            public void set_$273X145(List<String> _$273X145) {
                this._$273X145 = _$273X145;
            }

            public List<String> get_$294X195() {
                return _$294X195;
            }

            public void set_$294X195(List<String> _$294X195) {
                this._$294X195 = _$294X195;
            }

            public List<String> get_$580X328() {
                return _$580X328;
            }

            public void set_$580X328(List<String> _$580X328) {
                this._$580X328 = _$580X328;
            }

            public List<String> get_$640X330() {
                return _$640X330;
            }

            public void set_$640X330(List<String> _$640X330) {
                this._$640X330 = _$640X330;
            }

            public List<String> get_$640X470() {
                return _$640X470;
            }

            public void set_$640X470(List<String> _$640X470) {
                this._$640X470 = _$640X470;
            }

            public List<String> get_$870X492() {
                return _$870X492;
            }

            public void set_$870X492(List<String> _$870X492) {
                this._$870X492 = _$870X492;
            }

            public List<String> get_$966X604() {
                return _$966X604;
            }

            public void set_$966X604(List<String> _$966X604) {
                this._$966X604 = _$966X604;
            }
        }
    }
}
