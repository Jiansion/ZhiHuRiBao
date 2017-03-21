package com.qianjia.zhihuribao.bean;

import java.util.List;

/**
 * Created by Jiansion on 2017/3/21.
 */

public class DetailTypeTwo {

    /**
     * body : <div> </div>
     * title : 「新片」十月份，值得你走进影院的电影总会有一些
     * recommenders : [{"avatar":"http://pic2.zhimg.com/d3b31fa32_m.jpg"}]
     * share_url : http://daily.zhihu.com/story/7260659
     * js : []
     * theme : {"thumbnail":"http://pic3.zhimg.com/00eba01080138a5ac861d581a64ff9bd.jpg","id":3,"name":"电影日报"}
     * ga_prefix : 101016
     * images : ["http://pic4.zhimg.com/e43e1f7d8dfce546c4e95e944bcfb6cb_t.jpg"]
     * type : 2
     * id : 7260659
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

    private String body;
    private String title;
    private String share_url;
    private ThemeBean theme;
    private String ga_prefix;
    private int type;
    private int id;
    private List<RecommendersBean> recommenders;
    private List<?> js;
    private List<String> images;
    private List<String> css;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public ThemeBean getTheme() {
        return theme;
    }

    public void setTheme(ThemeBean theme) {
        this.theme = theme;
    }

    public String getGa_prefix() {
        return ga_prefix;
    }

    public void setGa_prefix(String ga_prefix) {
        this.ga_prefix = ga_prefix;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<RecommendersBean> getRecommenders() {
        return recommenders;
    }

    public void setRecommenders(List<RecommendersBean> recommenders) {
        this.recommenders = recommenders;
    }

    public List<?> getJs() {
        return js;
    }

    public void setJs(List<?> js) {
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
}
