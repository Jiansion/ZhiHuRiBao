package com.f8boss.zhihuribao.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jiansion on 2016/4/14.
 */
public class DateUtil {


    // 将毫秒转换成日期,
    public static String timeMethod(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }


}
