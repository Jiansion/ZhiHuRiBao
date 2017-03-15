package com.qianjia.basemodel.listener;

import com.qianjia.basemodel.view.BaseView;

/**
 * Created by Jiansion on 2017/3/6.
 */

public interface ModelCallBack<T> {

    void onRequestSuccess(T t);

    void onRequestError(BaseView.ErrorType type);
}
