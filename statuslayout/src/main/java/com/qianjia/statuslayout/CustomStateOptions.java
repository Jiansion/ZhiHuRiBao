package com.qianjia.statuslayout;

import android.support.annotation.DrawableRes;
import android.view.View;

/**
 * Created by Jiansion on 2017/3/8.
 * 设置 StatusLayout 的各种视图参数
 */

class CustomStateOptions {

    @DrawableRes
    private int imageRes;
    private boolean isLoading;
    private String message;
    private String buttonText;

    private View.OnClickListener btnClickListener;

    CustomStateOptions image(@DrawableRes int val) {
        imageRes = val;
        return this;
    }

    CustomStateOptions loading() {
        isLoading = true;
        return this;
    }

    CustomStateOptions message(String val) {
        message = val;
        return this;
    }

    CustomStateOptions buttonText(String val) {
        buttonText = val;
        return this;
    }

    CustomStateOptions buttonClickListener(View.OnClickListener listener) {
        btnClickListener = listener;
        return this;
    }

    int getImageRes() {
        return imageRes;
    }

    boolean isLoading() {
        return isLoading;
    }

    String getMessage() {
        return message;
    }

    String getButtonText() {
        return buttonText;
    }

    View.OnClickListener getBtnClickListener() {
        return btnClickListener;
    }
}
