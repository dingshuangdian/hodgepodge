package com.lsqidsd.hodgepodge.bean;

import java.util.List;

public class NewsVideoItem {


    /**
     * code : 0
     * msg :
     * data : [{"article_type":3,"aspect":"1.77","bimg":"http://puui.qpic.cn/qqvideo_ori/0/c0837yy1f97_496_280/0","category":"sports","category_chn":"体育","category_id":"1","comment_id":"3714360664","comment_num":0,"duration":75,"flag":3,"from":"cms_video","id":"c0837yy1f97","img":"http://puui.qpic.cn/qqvideo_ori/0/c0837yy1f97_496_280/0","img_count":1,"img_type":2,"imgs":{"228X128":"http://puui.qpic.cn/qqvideo_ori/0/c0837yy1f97_228_128/0","496X280":"http://puui.qpic.cn/qqvideo_ori/0/c0837yy1f97_496_280/0"},"intro":"CBA第一高薪换人了，易建联2000万年薪被秒杀，网友：与实力相符！","irs_imgs":{},"keywords":"CBA;广东东莞银行;易建联;周琦","media_icon":"http://inews.gtimg.com/newsapp_ls/0/7101466336_200200/0","mini_img":"http://puui.qpic.cn/qqvideo/0/c0837yy1f97/0","multi_imgs":[],"publish_time":"2019-02-11 15:47:44","s_group":1,"source":"视说体育","source_id":"5841870","strategy":25,"tags":"","title":"CBA第一高薪换人了，易建联2000万年薪被秒杀，网友：与实力相符！","title_simhash":162385853294701096,"ts":1549871264,"update_time":"2019-02-11 17:39:23","url":"http://new.qq.com/omv/video/c0837yy1f97","view_count":3638,"vurl":"https://new.qq.com/omv/video/c0837yy1f97"}]
     * datanum : 15
     * seq : 20190212101959-4ZILzlgyRRZsTAek
     * biz : 2006
     * s_group : 1
     * other : null
     */

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
        /**
         * article_type : 3
         * aspect : 1.77
         * bimg : http://puui.qpic.cn/qqvideo_ori/0/c0837yy1f97_496_280/0
         * category : sports
         * category_chn : 体育
         * category_id : 1
         * comment_id : 3714360664
         * comment_num : 0
         * duration : 75
         * flag : 3
         * from : cms_video
         * id : c0837yy1f97
         * img : http://puui.qpic.cn/qqvideo_ori/0/c0837yy1f97_496_280/0
         * img_count : 1
         * img_type : 2
         * imgs : {"228X128":"http://puui.qpic.cn/qqvideo_ori/0/c0837yy1f97_228_128/0","496X280":"http://puui.qpic.cn/qqvideo_ori/0/c0837yy1f97_496_280/0"}
         * intro : CBA第一高薪换人了，易建联2000万年薪被秒杀，网友：与实力相符！
         * irs_imgs : {}
         * keywords : CBA;广东东莞银行;易建联;周琦
         * media_icon : http://inews.gtimg.com/newsapp_ls/0/7101466336_200200/0
         * mini_img : http://puui.qpic.cn/qqvideo/0/c0837yy1f97/0
         * multi_imgs : []
         * publish_time : 2019-02-11 15:47:44
         * s_group : 1
         * source : 视说体育
         * source_id : 5841870
         * strategy : 25
         * tags :
         * title : CBA第一高薪换人了，易建联2000万年薪被秒杀，网友：与实力相符！
         * title_simhash : 162385853294701096
         * ts : 1549871264
         * update_time : 2019-02-11 17:39:23
         * url : http://new.qq.com/omv/video/c0837yy1f97
         * view_count : 3638
         * vurl : https://new.qq.com/omv/video/c0837yy1f97
         */

        private int article_type;
        private String aspect;
        private String bimg;
        private String category;
        private String category_chn;
        private String category_id;
        private String comment_id;
        private int comment_num;
        private int duration;
        private int flag;
        private String from;
        private String id;
        private String img;
        private int img_count;
        private int img_type;
        private ImgsBean imgs;
        private String intro;
        private IrsImgsBean irs_imgs;
        private String keywords;
        private String media_icon;
        private String mini_img;
        private String publish_time;
        private int s_group;
        private String source;
        private String source_id;
        private int strategy;
        private String tags;
        private String title;
        private long title_simhash;
        private int ts;
        private String update_time;
        private String url;
        private int view_count;
        private String vurl;
        private List<?> multi_imgs;

        public int getArticle_type() {
            return article_type;
        }

        public void setArticle_type(int article_type) {
            this.article_type = article_type;
        }

        public String getAspect() {
            return aspect;
        }

        public void setAspect(String aspect) {
            this.aspect = aspect;
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

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
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

        public int getImg_type() {
            return img_type;
        }

        public void setImg_type(int img_type) {
            this.img_type = img_type;
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

        public String getPublish_time() {
            return publish_time;
        }

        public void setPublish_time(String publish_time) {
            this.publish_time = publish_time;
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

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        public int getStrategy() {
            return strategy;
        }

        public void setStrategy(int strategy) {
            this.strategy = strategy;
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

        public long getTitle_simhash() {
            return title_simhash;
        }

        public void setTitle_simhash(long title_simhash) {
            this.title_simhash = title_simhash;
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

        public List<?> getMulti_imgs() {
            return multi_imgs;
        }

        public void setMulti_imgs(List<?> multi_imgs) {
            this.multi_imgs = multi_imgs;
        }

        public static class ImgsBean {
        }

        public static class IrsImgsBean {
        }
    }
}
