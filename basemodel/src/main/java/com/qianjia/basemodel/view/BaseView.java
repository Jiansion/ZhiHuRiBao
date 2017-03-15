package com.qianjia.basemodel.view;

/**
 * Created by Jiansion on 2017/3/6.
 */

public interface BaseView<T> {

    public enum ErrorType {
        NETERROR, EXCEPTION;
    }

    void onSuccess(T t);

    void onError(ErrorType type);
}
