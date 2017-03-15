package com.qianjia.basemodel.widget;

import android.support.annotation.ColorRes;
import android.support.v4.widget.SwipeRefreshLayout;


/**
 * Created by jiansion on 2017/1/12.
 */

public class SwipeRefreshAction {

    private SwipeRefreshAction() {
    }

    public static Runnable setSwipefreshAction(final SwipeRefreshLayout layout, @ColorRes final int... colorRes) {
        return new Runnable() {
            @Override
            public void run() {
                layout.setColorSchemeResources(colorRes);
                layout.setRefreshing(true);
            }
        };
    }
}
