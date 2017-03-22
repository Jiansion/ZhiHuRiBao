package com.qianjia.zhihuribao.bean;

import java.util.List;

/**
 * Created by Jiansion on 2017/3/15.
 * 日报详情
 */

public class Detail {

    /**
     * body :  <div> </div>
     * title : 中国城市交通真的需要那么多共享单车吗？
     * recommenders : [{"avatar":"http://pic2.zhimg.com/d3b31fa32_m.jpg"}]
     * image_source : Henry Söderlund / CC BY
     * image : http://pic4.zhimg.com/1b0f8ec016024f4c0622051608517263.jpg
     * share_url : http://daily.zhihu.com/story/9286626
     * js : []
     * ga_prefix : 031511
     * images : ["http://pic3.zhimg.com/8bd334f017b6f10773bbd7ca414c29fe.jpg"]
     * type : 0
     * id : 9286626
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     * theme : {"thumbnail":"http://pic3.zhimg.com/00eba01080138a5ac861d581a64ff9bd.jpg","id":3,"name":"电影日报"}
     */

    private String body;
    private String image_source;
    private String title;
    private String image;
    private String share_url;
    private int id;
    private List<RecommendersBean> recommenders;
    private List<String> js;
    private List<String> images;
    private List<String> css;
    private ThemeBean theme;

    public List<RecommendersBean> getRecommenders() {
        return recommenders;
    }

    public void setRecommenders(List<RecommendersBean> recommenders) {
        this.recommenders = recommenders;
    }


    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage_source() {
        return image_source;
    }

    public void setImage_source(String image_source) {
        this.image_source = image_source;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getJs() {
        return js;
    }

    public void setJs(List<String> js) {
        this.js = js;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<String> getCss() {
        return css;
    }

    public void setCss(List<String> css) {
        this.css = css;
    }

    public static class RecommendersBean {
        /**
         * avatar : http://pic2.zhimg.com/d3b31fa32_m.jpg
         */

        private String avatar;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }
    }

    public static class ThemeBean {
        /**
         * thumbnail : http://pic3.zhimg.com/00eba01080138a5ac861d581a64ff9bd.jpg
         * id : 3
         * name : 电影日报
         */

        private String thumbnail;
        private int id;
        private String name;

        public String getThumbnail() {
            return thumbnail;
        }

        public void setThumbnail(String thumbnail) {
            this.thumbnail = thumbnail;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
