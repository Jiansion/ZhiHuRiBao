package com.qianjia.zhihuribao.api;

import com.qianjia.zhihuribao.bean.Detail;
import com.qianjia.zhihuribao.bean.IndexList;
import com.qianjia.zhihuribao.bean.Theme;
import com.qianjia.zhihuribao.bean.ThemesCount;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Jiansion on 2017/3/6.
 */

public interface ZhiHuApi {

    //获取首页最新消息
    @GET("news/latest")
    Call<IndexList> getIndexData();

    //获取首页过往消息
    @GET("news/before/{date}")
    Call<IndexList> getIndexMoreData(@Path("date") String date);

    //获取详情
    @GET("news/{id}")
    Call<Detail> getDetailData(@Path("id") int id);

    //获取主题栏目列表
    @GET("themes")
    Call<Theme> getThemes();

    @GET("theme/{id}")
    Call<ThemesCount> getThemesCount(@Path("id") int id);
}
