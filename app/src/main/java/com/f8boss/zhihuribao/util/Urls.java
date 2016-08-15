package com.f8boss.zhihuribao.util;

/**
 * Created by jiansion on 2016/5/25.
 */
public class Urls {

    //知乎日报最新新闻
    public static final String ZHIHUNEWS = "http://news-at.zhihu.com/api/4/news/latest";

    //获取过往内容
    public static final String ZHIHU_BEFORE = "http://news.at.zhihu.com/api/4/news/before/";

    //获取内容详情
    public static final String NEW_CONTENT = "http://news-at.zhihu.com/api/4/news/";

    //其他的栏目 13为:日常心理学   12:为用户推荐  2:为开始游戏 9:为动漫那日报 3:电影日报
    public static final String THEM = "http://news-at.zhihu.com/api/4/theme/$";

    //获取某栏目的过往消息,其中 $ 为栏目Id,# 为列表中最后一项Itme的Id
    public static final String THEM_BEFORE = "https://news-at.zhihu.com/api/4/theme/$/before/#";

    //知乎其他栏目的内容的Url
    public static final String THEM_CONTENT = "http://news-at.zhihu.com/api/4/story/";


    //App闪屏页动画 1080*1776,,720*1184
    public static final String SPLASH_IMAGEURL = "http://news-at.zhihu.com/api/4/start-image/1080*1776";

    //栏目主编的个人资料API
    public static final String EDIT_PAGE = "http://news-at.zhihu.com/api/4/editor/$/profile-page/android";


}
