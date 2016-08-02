package com.f8boss.zhihuribao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiansion on 2016/4/14.
 * 工具类
 */
public class Utils {


    /**
     * 将毫秒转换成日期,
     *
     * @param date Date 对象
     * @return 日期
     */
    public static String timeMethod(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }

    /**
     * 替换字符串
     *
     * @param url    拼接地址
     * @param format 表示占位符
     * @param page   表示 替换的内容
     * @return 拼接好的字符串
     */
    public static String getReplaceFormat(String url, String format, String page) {
        String urls = url.replace(format, page);
        return urls;
    }


}
