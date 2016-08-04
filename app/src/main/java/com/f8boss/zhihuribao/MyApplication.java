package com.f8boss.zhihuribao;

import android.app.Application;


import com.lzy.okhttputils.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by jiansion on 2016/5/25.
 * 自定义Applcation
 */
public class MyApplication extends Application {

    //全局上下文
    private static MyApplication applicationContext;

    //通过 getInstance()得到全局上下文
    public static MyApplication getInstance() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        OkHttpUtils.init(applicationContext);
        //以下都不是必须的，根据需要自行选择
        OkHttpUtils.getInstance()//
//                .debug("OkHttpUtils")                                              //是否打开调试
                .setConnectTimeout(OkHttpUtils.DEFAULT_MILLISECONDS)               //全局的连接超时时间
                .setReadTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS)                  //全局的读取超时时间
                .setWriteTimeOut(OkHttpUtils.DEFAULT_MILLISECONDS);               //全局的写入超时时间
        //.setCookieStore(new MemoryCookieStore())                           //cookie使用内存缓存（app退出后，cookie消失）
        //.setCookieStore(new PersistentCookieStore())                       //cookie持久化存储，如果cookie不过期，则一直有效
//                .addCommonHeaders(headers)                                         //设置全局公共头
//                .addCommonParams(params);
    }
}
