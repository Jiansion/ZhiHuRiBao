package com.qianjia.zhihuribao.ui.view;

/**
 * Created by Jiansion on 2017/4/28.
 */

public interface BaseView<T> {
    void onSuccess(T t);

    void onFail(String string);
}
