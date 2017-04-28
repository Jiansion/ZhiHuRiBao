package com.qianjia.zhihuribao.http;

import com.qianjia.zhihuribao.App;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Jiansion on 2017/4/28.
 * 获取 Retrofit 实例
 */

public class RetrofitHelper {

    private static OkHttpClient initHttpClient() {
        File cacheFile = new File(App.getInstance().getCacheDir(), "cache");

        //设置缓存为 10M
        int cacheSize = 1024 * 1024 * 10;

        Cache cache = new Cache(cacheFile, cacheSize);
        OkHttpClient client = new OkHttpClient
                .Builder()
                .addNetworkInterceptor(new CustomCacheInterceptor())
                .addInterceptor(new CustomCacheInterceptor())
                .cache(cache)
                .build();
        return client;
    }

    private static Retrofit getRetrofit(String baseUrl) {
        Retrofit retrofit = new Retrofit
                .Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(initHttpClient())
                .build();
        return retrofit;
    }


    /**
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createZhiHuApi(Class<T> clazz) {
        return RetrofitHelper
                .getRetrofit("http://news-at.zhihu.com/api/4/")
                .create(clazz);
    }
}
