package com.qianjia.zhihuribao.model;

/**
 * Created by Jiansion on 2017/4/28.
 */

public interface RequestListener<T> {
    void onRequestSuccess(T t);

    void onRequestFail(String msg);
}
