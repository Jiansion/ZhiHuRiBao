package com.qianjia.zhihuribao;

import android.app.Application;

import com.blankj.utilcode.util.Utils;

/**
 * Created by Jiansion on 2017/3/7.
 * 自定义的 Application
 */

public class App extends Application {

    private static Application app;

    public static Application getInstance() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        Utils.init(this);
    }
}
