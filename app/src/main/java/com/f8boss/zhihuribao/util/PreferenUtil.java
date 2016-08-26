package com.f8boss.zhihuribao.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import com.f8boss.zhihuribao.MyApplication;

/**
 * Created by lucy on 2016/8/26.
 * 包装SharePreference
 */
public class PreferenUtil {

    //保存的名称
    public static final String PREF_NAME = "perferen_data";


    /**
     * 使用 apply（） 提交是个异步过程，修改成功或失败均无提示，但效率较高
     *
     * @param key   key
     * @param value value
     */
    public static void putString(String key, String value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putString(key, value).apply();
    }

    /**
     * 通过 key 获得内容
     *
     * @param key          key
     * @param defaultValue dv
     * @return string
     */
    public static String getSting(String key, @Nullable String defaultValue) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getString(key, defaultValue);
    }

    /**
     * 储存 Integet 类型数据
     * like {@link #putString(String, String)}
     *
     * @param key
     * @param value
     */
    public static void putInteger(String key, int value) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putInt(key, value).apply();
    }

    /**
     * like {@link #getSting(String, String)}
     *
     * @param key
     * @param defaultValue
     * @return
     */
    public static int getInteger(String key, int defaultValue) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, defaultValue);
    }


    /**
     * like {@link #putString(String, String)}
     *
     * @param key
     * @param b
     */
    public static void putBoolean(String key, boolean b) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        sp.edit().putBoolean(key, b).apply();
    }

    /**
     * {@link #getSting(String, String)}
     * 默认返回为false
     *
     * @param key
     * @return
     */
    public static boolean getBoolean(String key) {
        SharedPreferences sp = MyApplication.getInstance().getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, false);
    }

}
