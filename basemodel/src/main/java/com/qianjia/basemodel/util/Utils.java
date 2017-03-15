package com.qianjia.basemodel.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.telephony.TelephonyManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by jiansion on 2016/4/14.
 * 工具类
 */
public class Utils {

    private Utils() {
    }


    /**
     * 将毫秒转换成日期,
     *
     * @param date Date 对象
     * @return 日期
     */
    public static String timeMethod(Date date) {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(date);
    }

    /**
     * 获取设备ID
     *
     * @param context context
     * @return s
     */
    public static String getDeviceMessage(Context context) {
        @SuppressLint("HardwareIds") String s = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE))
                .getDeviceId();
        return s;
    }

    /**
     * 手机号码的正则判断
     *
     * @param phone phone
     * @return true 正则匹配
     */
    public static boolean isMobilePhone(String phone) {
        Pattern pattern = Pattern
                .compile("^((13[0-9])|(14[5,7])|(15[^4,\\D])|(17[6-8])|(18[0-9]))\\d{8}$");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }

    /**
     * 正则判断 Email
     *
     * @param email email
     * @return true 正则匹配
     */
    public static boolean isEmail(String email) {
        Pattern pattern = Pattern.compile("^([a-z0-9A-Z]+[-|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
