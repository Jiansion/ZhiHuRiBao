package com.qianjia.statuslayout;

import android.support.annotation.DrawableRes;
import android.view.View;

import java.io.Serializable;

/**
 * Created by Jiansion on 2017/3/8.
 */

class CustomStateOptions implements Serializable {

    @DrawableRes
    private int imageRes;
    private boolean isLoading;
    private String message;
    private String buttonText;

    private View.OnClickListener btnClickListener;

    public CustomStateOptions image(@DrawableRes int val) {
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
