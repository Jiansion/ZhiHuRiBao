package com.qianjia.zhihuribao.util;

import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.widget.TextView;

/**
 * Created by Jiansion on 2017/4/26.
 * 搜索提亮关键字
 */

public class SearchHightLinght {

    /**
     * 提亮搜索关键字
     * @param itemText 全部字体
     * @param key 搜索关键字
     * @param tvView TextView
     */
    public static void hightLinghtSearchKey(String itemText, String key, TextView tvView) {
        if (!TextUtils.isEmpty(itemText) && itemText.contains(key)) {
            int index = itemText.indexOf(key);
            int len = key.length();
            Spanned temp = Html.fromHtml(itemText.substring(0, index)
                    + "<font color=#FF0000>"
                    + itemText.substring(index, index + len) + "</font>"
                    + itemText.substring(index + len, itemText.length()));

            tvView.setText(temp);
        } else {
            tvView.setText(itemText);
        }

    }
}
