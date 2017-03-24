package com.qianjia.zhihuribao.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qianjia.basemodel.listener.ModelCallBack;
import com.qianjia.basemodel.view.BaseView;
import com.qianjia.zhihuribao.api.ZhiHuApi;
import com.qianjia.zhihuribao.bean.Detail;
import com.qianjia.zhihuribao.bean.IndexList;
import com.qianjia.zhihuribao.bean.Theme;
import com.qianjia.zhihuribao.bean.ThemesCount;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jiansion on 2017/3/14.
 * 网络请求操作类
 */

public class RetrofitHelp {

    private static final Gson gson = new GsonBuilder()
            //配置你的Gson
            .setDateFormat("yyyy-MM-dd hh:mm:ss")
            .create();

    private static final Retrofit zhiHuRetrofit = new Retrofit.Builder()
            .baseUrl("http://news-at.zhihu.com/api/4/")
            //可以接收自定义的Gson，当然也可以不传
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();

    private static <T> T createApi(Class<T> clazz) {
        return zhiHuRetrofit.create(clazz);
    }


    /**
     * 请求网络获取首页数据
     *
     * @param modelCallBack
     */
    public static void getZhiHuData(ModelCallBack<IndexList> modelCallBack) {
        ZhiHuApi api = createApi(ZhiHuApi.class);
        Call<IndexList> message = api.getIndexData();
        message.enqueue(new Callback<IndexList>() {
            @Override
            public void onResponse(Call<IndexList> call, Response<IndexList> response) {
                IndexList body = response.body();
                modelCallBack.onRequestSuccess(body);
            }

            @Override
            public void onFailure(Call<IndexList> call, Throwable t) {
                modelCallBack.onRequestError(BaseView.ErrorType.NETERROR);
            }
        });
    }


    public static void getZhiHuData(String date, ModelCallBack<IndexList> modelCallBack) {
        ZhiHuApi zhiHuApi = createApi(ZhiHuApi.class);
        Call<IndexList> indexMoreData = zhiHuApi.getIndexMoreData(date);
        indexMoreData.enqueue(new Callback<IndexList>() {
            @Override
            public void onResponse(Call<IndexList> call, Response<IndexList> response) {
                IndexList body = response.body();
                modelCallBack.onRequestSuccess(body);
            }

            @Override
            public void onFailure(Call<IndexList> call, Throwable t) {
                modelCallBack.onRequestError(BaseView.ErrorType.NETERROR);
            }
        });
    }

    /**
     * 获取详情页的数据
     * type:0 or type:1
     *
     * @param id
     * @param modelCallBack
     */
    public static void getZhiHuDetail(int id, ModelCallBack<Detail> modelCallBack) {
        ZhiHuApi api = createApi(ZhiHuApi.class);
        Call<Detail> detailData = api.getDetailData(id);
        detailData.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                Detail detail = response.body();
                modelCallBack.onRequestSuccess(detail);
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {
                modelCallBack.onRequestError(BaseView.ErrorType.NETERROR);
            }
        });

    }

    public static void getZhiHuDetail(int id) {

    }


    /**
     * 获取全部栏目主题
     *
     * @param modelCallBack
     */
    public static void getThemesList(ModelCallBack<Theme> modelCallBack) {
        ZhiHuApi api = createApi(ZhiHuApi.class);
        Call<Theme> themes = api.getThemes();
        themes.enqueue(new Callback<Theme>() {
            @Override
            public void onResponse(Call<Theme> call, Response<Theme> response) {
                Theme body = response.body();
                modelCallBack.onRequestSuccess(body);
            }

            @Override
            public void onFailure(Call<Theme> call, Throwable t) {
                modelCallBack.onRequestError(BaseView.ErrorType.NETERROR);
            }
        });
    }


    /**
     * 获取栏目的内容
     *
     * @param id            栏目id
     * @param modelCallBack
     */
    public static void getThemesContent(int id, ModelCallBack<ThemesCount> modelCallBack) {
        ZhiHuApi api = createApi(ZhiHuApi.class);
        Call<ThemesCount> themesCount = api.getThemesCount(id);
        themesCount.enqueue(new Callback<ThemesCount>() {
            @Override
            public void onResponse(Call<ThemesCount> call, Response<ThemesCount> response) {
                ThemesCount body = response.body();
                modelCallBack.onRequestSuccess(body);
            }

            @Override
            public void onFailure(Call<ThemesCount> call, Throwable t) {
                modelCallBack.onRequestError(BaseView.ErrorType.NETERROR);
            }
        });
    }

    /**
     * 加载其他栏目的过往内容
     *
     * @param id            栏目ID
     * @param lastItemId    显示的最后一项内容的 ID
     * @param modelCallBack callback
     */
    public static void getThemesCoutent(int id, int lastItemId, ModelCallBack<ThemesCount> modelCallBack) {
        ZhiHuApi api = createApi(ZhiHuApi.class);
        Call<ThemesCount> themesCount = api.getThemesCount(id, lastItemId);
        themesCount.enqueue(new Callback<ThemesCount>() {
            @Override
            public void onResponse(Call<ThemesCount> call, Response<ThemesCount> response) {
                ThemesCount body = response.body();
                modelCallBack.onRequestSuccess(body);
            }

            @Override
            public void onFailure(Call<ThemesCount> call, Throwable t) {
                modelCallBack.onRequestError(BaseView.ErrorType.NETERROR);
            }
        });

    }

}
