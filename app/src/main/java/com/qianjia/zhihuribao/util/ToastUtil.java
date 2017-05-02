package com.qianjia.zhihuribao.util;

import android.support.annotation.StringRes;
import android.widget.Toast;

import com.qianjia.zhihuribao.App;


/**
 * Created by jiansion on 2016/9/28.
 * ToastUtil
 */

public class ToastUtil {

    private static Toast toast;

    private ToastUtil() {
    }

    public static void showToast(String msg) {
        if (toast == null) {
            toast = Toast.makeText(App.getInstance(), msg, Toast.LENGTH_SHORT);
        } else {
            toast.setText(msg);
        }
        toast.show();
    }

    public static void showToast(@StringRes int res) {
        String msg = App.getInstance().getString(res);
        showToast(msg);
    }


}
