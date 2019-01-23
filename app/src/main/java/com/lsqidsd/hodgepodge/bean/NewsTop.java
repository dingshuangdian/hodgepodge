package com.lsqidsd.hodgepodge.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsTop {


    /**
     * biz : 7010
     * code : 0
     * data : [{"abstract":"央视网消息：2019年1月23日，外交部发言人华春莹主持例行记者会。　　记者问：美国刚刚发布《国家情报战略》报告，称俄罗斯、中国是美国的主要威胁，并对中国推进军事现代化、追求地区经济和领土主导权的做法表示关切，中方有何评论？外交部发言人华春莹　　外交部发言人华春莹：我们也注意到这份报告。近期美国出台了一系...","articletype":"0","comment":"","commentid":"3678103136","comments":1,"ext_data":{"ext_action":{"Fimgurl32":"http://inews.gtimg.com/newsapp_ls/0/7414735225_294195/0","Fimgurl5":""}},"flag":"","graphicLiveID":"","id":"20190123A17T7F00","imagecount":0,"origUrl":"","qishu":"","source":"央视网新闻","surl":"http://kuaibao.qq.com/s/20190123A17T7F00","tag":[],"thumbnails":["http://inews.gtimg.com/newsapp_ls/0/7414735225_240180/0"],"thumbnails_qqnews":["http://inews.gtimg.com/newsapp_ls/0/7414735225_150120/0"],"time":"","timestamp":1548245695,"title":"美国的\u201c中俄威胁论\u201d又来了 华春莹：敦促美放弃过时观念","uinname":"5278151","uinnick":"央视网新闻","url":"https://xw.qq.com/cmsid/20190123A17T7F/20190123A17T7F00","videoTotalTime":"","voteId":"","voteNum":"","vurl":"https://new.qq.com/omn/20190123/20190123A17T7F.html","weiboid":""}]
     * mediainfo : {"mid":"5278151","name":"央视网新闻","icon":"http://inews.gtimg.com/newsapp_ls/0/511182887_200200/0","intro":"央视网新闻频道官方账号，内容包含：国内，国际，经济，军事，社会，法制类热点新闻，独家原创热点解析。"}
     * msg : ok
     * seq : 20190123222358-vMfW5tr024zt8PRY
     */

    private int biz;
    private int code;
    private MediainfoBean mediainfo;
    private String msg;
    private String seq;
    private List<DataBean> data;

    public int getBiz() {
        return biz;
    }

    public void setBiz(int biz) {
        this.biz = biz;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public MediainfoBean getMediainfo() {
        return mediainfo;
    }

    public void setMediainfo(MediainfoBean mediainfo) {
        this.mediainfo = mediainfo;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class MediainfoBean {
        /**
         * mid : 5278151
         * name : 央视网新闻
         * icon : http://inews.gtimg.com/newsapp_ls/0/511182887_200200/0
         * intro : 央视网新闻频道官方账号，内容包含：国内，国际，经济，军事，社会，法制类热点新闻，独家原创热点解析。
         */

        private String mid;
        private String name;
        private String icon;
        private String intro;

        public String getMid() {
            return mid;
        }

        public void setMid(String mid) {
            this.mid = mid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIntro() {
            return intro;
        }

        public void setIntro(String intro) {
            this.intro = intro;
        }
    }

    public static class DataBean {
        /**
         * abstract : 央视网消息：2019年1月23日，外交部发言人华春莹主持例行记者会。　　记者问：美国刚刚发布《国家情报战略》报告，称俄罗斯、中国是美国的主要威胁，并对中国推进军事现代化、追求地区经济和领土主导权的做法表示关切，中方有何评论？外交部发言人华春莹　　外交部发言人华春莹：我们也注意到这份报告。近期美国出台了一系...
         * articletype : 0
         * comment :
         * commentid : 3678103136
         * comments : 1
         * ext_data : {"ext_action":{"Fimgurl32":"http://inews.gtimg.com/newsapp_ls/0/7414735225_294195/0","Fimgurl5":""}}
         * flag :
         * graphicLiveID :
         * id : 20190123A17T7F00
         * imagecount : 0
         * origUrl :
         * qishu :
         * source : 央视网新闻
         * surl : http://kuaibao.qq.com/s/20190123A17T7F00
         * tag : []
         * thumbnails : ["http://inews.gtimg.com/newsapp_ls/0/7414735225_240180/0"]
         * thumbnails_qqnews : ["http://inews.gtimg.com/newsapp_ls/0/7414735225_150120/0"]
         * time :
         * timestamp : 1548245695
         * title : 美国的“中俄威胁论”又来了 华春莹：敦促美放弃过时观念
         * uinname : 5278151
         * uinnick : 央视网新闻
         * url : https://xw.qq.com/cmsid/20190123A17T7F/20190123A17T7F00
         * videoTotalTime :
         * voteId :
         * voteNum :
         * vurl : https://new.qq.com/omn/20190123/20190123A17T7F.html
         * weiboid :
         */

        @SerializedName("abstract")
        private String abstractX;
        private String articletype;
        private String comment;
        private String commentid;
        private int comments;
        private ExtDataBean ext_data;
        private String flag;
        private String graphicLiveID;
        private String id;
        private int imagecount;
        private String origUrl;
        private String qishu;
        private String source;
        private String surl;
        private String time;
        private int timestamp;
        private String title;
        private String uinname;
        private String uinnick;
        private String url;
        private String videoTotalTime;
        private String voteId;
        private String voteNum;
        private String vurl;
        private String weiboid;
        private List<?> tag;
        private List<String> thumbnails;
        private List<String> thumbnails_qqnews;

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getArticletype() {
            return articletype;
        }

        public void setArticletype(String articletype) {
            this.articletype = articletype;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCommentid() {
            return commentid;
        }

        public void setCommentid(String commentid) {
            this.commentid = commentid;
        }

        public int getComments() {
            return comments;
        }

        public void setComments(int comments) {
            this.comments = comments;
        }

        public ExtDataBean getExt_data() {
            return ext_data;
        }

        public void setExt_data(ExtDataBean ext_data) {
            this.ext_data = ext_data;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getGraphicLiveID() {
            return graphicLiveID;
        }

        public void setGraphicLiveID(String graphicLiveID) {
            this.graphicLiveID = graphicLiveID;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getImagecount() {
            return imagecount;
        }

        public void setImagecount(int imagecount) {
            this.imagecount = imagecount;
        }

        public String getOrigUrl() {
            return origUrl;
        }

        public void setOrigUrl(String origUrl) {
            this.origUrl = origUrl;
        }

        public String getQishu() {
            return qishu;
        }

        public void setQishu(String qishu) {
            this.qishu = qishu;
        }

        public String getSource() {
            return source;
        }

        public void setSource(String source) {
            this.source = source;
        }

        public String getSurl() {
            return surl;
        }

        public void setSurl(String surl) {
            this.surl = surl;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public int getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(int timestamp) {
            this.timestamp = timestamp;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUinname() {
            return uinname;
        }

        public void setUinname(String uinname) {
            this.uinname = uinname;
        }

        public String getUinnick() {
            return uinnick;
        }

        public void setUinnick(String uinnick) {
            this.uinnick = uinnick;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getVideoTotalTime() {
            return videoTotalTime;
        }

        public void setVideoTotalTime(String videoTotalTime) {
            this.videoTotalTime = videoTotalTime;
        }

        public String getVoteId() {
            return voteId;
        }

        public void setVoteId(String voteId) {
            this.voteId = voteId;
        }

        public String getVoteNum() {
            return voteNum;
        }

        public void setVoteNum(String voteNum) {
            this.voteNum = voteNum;
        }

        public String getVurl() {
            return vurl;
        }

        public void setVurl(String vurl) {
            this.vurl = vurl;
        }

        public String getWeiboid() {
            return weiboid;
        }

        public void setWeiboid(String weiboid) {
            this.weiboid = weiboid;
        }

        public List<?> getTag() {
            return tag;
        }

        public void setTag(List<?> tag) {
            this.tag = tag;
        }

        public List<String> getThumbnails() {
            return thumbnails;
        }

        public void setThumbnails(List<String> thumbnails) {
            this.thumbnails = thumbnails;
        }

        public List<String> getThumbnails_qqnews() {
            return thumbnails_qqnews;
        }

        public void setThumbnails_qqnews(List<String> thumbnails_qqnews) {
            this.thumbnails_qqnews = thumbnails_qqnews;
        }

        public static class ExtDataBean {
            /**
             * ext_action : {"Fimgurl32":"http://inews.gtimg.com/newsapp_ls/0/7414735225_294195/0","Fimgurl5":""}
             */

            private ExtActionBean ext_action;

            public ExtActionBean getExt_action() {
                return ext_action;
            }

            public void setExt_action(ExtActionBean ext_action) {
                this.ext_action = ext_action;
            }

            public static class ExtActionBean {
                /**
                 * Fimgurl32 : http://inews.gtimg.com/newsapp_ls/0/7414735225_294195/0
                 * Fimgurl5 :
                 */

                private String Fimgurl32;
                private String Fimgurl5;

                public String getFimgurl32() {
                    return Fimgurl32;
                }

                public void setFimgurl32(String Fimgurl32) {
                    this.Fimgurl32 = Fimgurl32;
                }

                public String getFimgurl5() {
                    return Fimgurl5;
                }

                public void setFimgurl5(String Fimgurl5) {
                    this.Fimgurl5 = Fimgurl5;
                }
            }
        }
    }
}
