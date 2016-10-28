package com.f8boss.zhihuribao.util;

import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;

import com.f8boss.zhihuribao.activity.ShowWebImageActivity;

/**
 * Created by jiansion on 2016/10/28.
 */

public class JSInterface {
    private static final String TAG = JSInterface.class.getSimpleName();
    private Context context;

    public JSInterface(Context context) {
        this.context = context;
    }

    @JavascriptInterface
    public void openImage(String img) {
        Log.e(TAG, "openImage: " + img);
        ShowWebImageActivity.startShowImage(context, img);
    }
}
