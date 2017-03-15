package com.qianjia.basemodel.view;

/**
 * Created by Jiansion on 2017/3/6.
 */

public interface ProgressView<T> extends BaseView<T> {

    void onShowProgress();

    void onHindProgress();


}
