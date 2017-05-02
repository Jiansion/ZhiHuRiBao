package com.qianjia.zhihuribao.ui.view;

/**
 * Created by Jiansion on 2017/4/28.
 */

public interface ProgressView<T> extends BaseView<T> {
    void onShowProgress();

    void onHintProgress();
}
