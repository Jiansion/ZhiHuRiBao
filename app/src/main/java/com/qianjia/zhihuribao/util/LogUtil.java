package com.qianjia.zhihuribao.util;

import android.util.Log;

import com.qianjia.zhihuribao.BuildConfig;


/**
 * Created by Jiansion on 2017/2/17.
 * 日志工具类,当项目正式打包后不再输入log
 */

public class LogUtil {
    private final static boolean isDebug = BuildConfig.DEBUG;

    public static void v(String tag, String msg) {
        if (isDebug)
            Log.v(tag, msg);
    }

    public static void d(String tag, String msg) {
        if (isDebug)
            Log.d(tag, msg);

    }

    public static void i(String tag, String msg) {
        if (isDebug)
            Log.i(tag, msg);

    }

    public static void w(String tag, String msg) {
        if (isDebug)
            Log.w(tag, msg);

    }

    public static void e(String tag, String msg) {
        if (isDebug)
            Log.e(tag, msg);

    }
}
