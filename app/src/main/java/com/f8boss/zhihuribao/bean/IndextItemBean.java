package com.f8boss.zhihuribao.bean;

import java.util.List;

/**
 * Created by jiansion on 2016/5/25.
 * 首页数据列表实体类
 */
public class IndextItemBean {


    /**
     * date : 20160528
     * stories : [{"ga_prefix":"052814","id":8361786,"images":["http://pic4.zhimg.com/41634758a3cc94b26aeec1181034976b.jpg"],"multipic":true,"title":"看了这么一堆错觉图，我感觉需要做很久眼保健操","type":0},{"ga_prefix":"052813","id":8364779,"images":["http://pic3.zhimg.com/4916a7bb3714c97d2a627ba16687627e.jpg"],"title":"从预算规划开始，装修都有哪些要照顾的边边角角？","type":0},{"ga_prefix":"052812","id":8334944,"images":["http://pic4.zhimg.com/daa9cf3222aef6ba1d411583d0a2f83b.jpg"],"title":"大误 · 屠龙少年、枯骨、歪脖树的\u2026\u2026暖心故事们","type":0},{"ga_prefix":"052811","id":8342869,"images":["http://pic4.zhimg.com/4b1536a746ed59234839bb2acf70c4b3.jpg"],"title":"总结出一些小窍门，出去玩能省不少钱","type":0},{"ga_prefix":"052810","id":8362919,"images":["http://pic2.zhimg.com/d05cede0741a86c47943ab6c29b8ea9d.jpg"],"multipic":true,"title":"云南游不光是昆明、大理、丽江，但这条线也能玩得不一样","type":0},{"ga_prefix":"052809","id":8349131,"images":["http://pic3.zhimg.com/842a6a38b40790abe774088d037a8016.jpg"],"title":"练了半天累得不行，我能不能歇一会啊？","type":0},{"ga_prefix":"052808","id":8365189,"images":["http://pic1.zhimg.com/a7f4678dea8e6eb4b8a5cf0728939cf8.jpg"],"title":"非常实用的跳水指导，前提是你有一定基础","type":0},{"ga_prefix":"052807","id":8365176,"images":["http://pic2.zhimg.com/cfd37c3a50acf62fd6dd21d0999ee7b9.jpg"],"title":"欧冠决赛的矛盾对决：全欧最强火力 VS 超级门将","type":0},{"ga_prefix":"052807","id":8365668,"images":["http://pic3.zhimg.com/7bfa66c7232e511769f33fed69eb316a.jpg"],"multipic":true,"title":"韩国姑娘教你做泡菜，好像\u2026\u2026没法更正宗了","type":0},{"ga_prefix":"052807","id":8349782,"images":["http://pic3.zhimg.com/673eb7ae1acc6ab7d84d5192f61bef72.jpg"],"title":"一开始，漫威想让小李演美队，尼古拉斯凯奇演钢铁侠\u2026\u2026","type":0},{"ga_prefix":"052807","id":8365583,"images":["http://pic1.zhimg.com/89f9ab817ff21e834f26da559b0792dc.jpg"],"title":"读读日报 24 小时热门 TOP 5 · 可能是最有效的 iPhone 防干扰指南","type":0},{"ga_prefix":"052806","id":8360234,"images":["http://pic3.zhimg.com/e7e19a2ea302ba735a70e47cde74eaba.jpg"],"title":"瞎扯 · 如何正确地吐槽","type":0}]
     * top_stories : [{"ga_prefix":"052812","id":8334944,"image":"http://pic4.zhimg.com/27f87c9d104688d70ad8eaa2139e5e47.jpg","title":"大误 · 屠龙少年、枯骨、歪脖树的\u2026\u2026暖心故事们","type":0},{"ga_prefix":"052807","id":8365668,"image":"http://pic2.zhimg.com/f532c0f3e5dac2174401d379ce4cfbdd.jpg","title":"韩国姑娘教你做泡菜，好像\u2026\u2026没法更正宗了","type":0},{"ga_prefix":"052807","id":8349782,"image":"http://pic3.zhimg.com/37388b0a572b93a68ada71f52557fd76.jpg","title":"一开始，漫威想让小李演美队，尼古拉斯凯奇演钢铁侠\u2026\u2026","type":0},{"ga_prefix":"052811","id":8342869,"image":"http://pic3.zhimg.com/ca64f879d3a83eb349fb76f686cea5c2.jpg","title":"总结出一些小窍门，出去玩能省不少钱","type":0},{"ga_prefix":"052717","id":8359245,"image":"http://pic3.zhimg.com/f69dc98a129e9ea6afd917d659ad5c4a.jpg","title":"保护好你和他人间的边界，否则太远孤独，太近伤害","type":0}]
     */

    private String date;
    /**
     * ga_prefix : 052814
     * id : 8361786
     * images : ["http://pic4.zhimg.com/41634758a3cc94b26aeec1181034976b.jpg"]
     * multipic : true
     * title : 看了这么一堆错觉图，我感觉需要做很久眼保健操
     * type : 0
     */

    private List<StoriesBean> stories;
    /**
     * ga_prefix : 052812
     * id : 8334944
     * image : http://pic4.zhimg.com/27f87c9d104688d70ad8eaa2139e5e47.jpg
     * title : 大误 · 屠龙少年、枯骨、歪脖树的……暖心故事们
     * type : 0
     */

    private List<TopStoriesBean> top_stories;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public List<StoriesBean> getStories() {
        return stories;
    }

    public void setStories(List<StoriesBean> stories) {
        this.stories = stories;
    }

    public List<TopStoriesBean> getTop_stories() {
        return top_stories;
    }

    public void setTop_stories(List<TopStoriesBean> top_stories) {
        this.top_stories = top_stories;
    }

    public static class StoriesBean {
        private String ga_prefix;
        private int id;


        //这个标签用于表示该条新闻收有多张图片
        private boolean multipic;
        private String title;
        private int type;
        private List<String> images;

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public boolean isMultipic() {
            return multipic;
        }

        public void setMultipic(boolean multipic) {
            this.multipic = multipic;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public List<String> getImages() {
            return images;
        }

        public void setImages(List<String> images) {
            this.images = images;
        }
    }

    public static class TopStoriesBean {
        private String ga_prefix;
        private int id;
        private String image;
        private String title;
        private int type;

        public String getGa_prefix() {
            return ga_prefix;
        }

        public void setGa_prefix(String ga_prefix) {
            this.ga_prefix = ga_prefix;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
