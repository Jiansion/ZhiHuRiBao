package com.qianjia.zhihuribao.api;

import com.qianjia.zhihuribao.bean.Detail;
import com.qianjia.zhihuribao.bean.IndexList;

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

    @GET("news/before/{date}")
    Call<IndexList> getIndexMoreData(@Path("date") String date);

    @GET("news/{id}")
    Call<Detail> getDetailData(@Path("id") int id);
}
