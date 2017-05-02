package com.qianjia.zhihuribao.presenter;

import com.qianjia.zhihuribao.ui.view.BaseView;

/**
 * Created by Jiansion on 2017/4/28.
 */

public class BasePresenter<V extends BaseView> {
    protected V mView;

    public BasePresenter(V mView) {
        this.mView = mView;
    }

    /**
     * 销毁资源
     */
    public void detach() {
        if (mView != null) mView = null;
    }
}
