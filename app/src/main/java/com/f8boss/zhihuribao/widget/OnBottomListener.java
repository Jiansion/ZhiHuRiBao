package com.f8boss.zhihuribao.widget;

/**
 * Created by jiasion on 2016/8/4.
 * 滑动状态的监听及回调
 */
public interface OnBottomListener {
    //滑动到底部
    void onBottom();

    //处于滑动状态
    void onScrollIn();

    //处于滑动静止状态
    void onScrollIdle();
}
