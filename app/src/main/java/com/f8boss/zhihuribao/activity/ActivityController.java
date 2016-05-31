package com.f8boss.zhihuribao.activity;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lucy on 2016/5/6.
 * <p/>
 * 用于添加，删除， finish();Activity
 */
public class ActivityController {

    public static List<AppCompatActivity> activityList = new ArrayList<>();


    //往集合中添加Avctivity；
    public static void addActivity(AppCompatActivity activity) throws Exception {
        activityList.add(activity);
    }


    //在集合中删除Activity
    public static void removeActivity(AppCompatActivity activity) throws Exception {
        activityList.remove(activity);
    }


    //销毁所有活动，推出应用
    public static void finishAllActivity() {
        for (AppCompatActivity appCompatActivity : activityList) {
            if (!appCompatActivity.isFinishing()) {
                appCompatActivity.finish();
            }
        }

    }


}
