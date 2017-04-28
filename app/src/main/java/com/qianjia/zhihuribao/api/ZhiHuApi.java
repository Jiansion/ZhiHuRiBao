package com.qianjia.zhihuribao.api;


import com.qianjia.zhihuribao.bean.Detail;
import com.qianjia.zhihuribao.bean.IndexList;
import com.qianjia.zhihuribao.bean.Theme;
import com.qianjia.zhihuribao.bean.ThemesCount;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Jiansion on 2017/3/6.
 * 请求Api
 */

public interface ZhiHuApi {

    //获取首页最新消息
    @GET("news/latest")
    Observable<IndexList> getIndexData();

    //获取首页过往消息
    @GET("news/before/{date}")
    Observable<IndexList> getIndexMoreData(@Path("date") String date);

    //获取详情
    @GET("news/{id}")
    Observable<Detail> getDetailData(@Path("id") int id);

    //获取主题栏目列表
    @GET("themes")
    Observable<Theme> getThemes();

    /**
     * 获取某栏目的最新内容
     *
     * @param id 栏目对应的ID
     * @return
     */
    @GET("theme/{id}")
    Observable<ThemesCount> getThemesCount(@Path("id") int id);

    /**
     * 获取某栏目的过往内容
     *
     * @param id     栏目对应的 ID
     * @param lastId 最后一项 Item 的id
     * @return
     */
    @GET("theme/{id}/before/{lastId}")
    Observable<ThemesCount> getThemesCount(@Path("id") int id, @Path("lastId") int lastId);
}
