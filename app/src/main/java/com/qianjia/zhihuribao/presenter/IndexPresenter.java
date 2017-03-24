package com.qianjia.zhihuribao.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.qianjia.basemodel.listener.ModelCallBack;
import com.qianjia.basemodel.view.BaseView;
import com.qianjia.basemodel.view.ProgressView;
import com.qianjia.zhihuribao.bean.IndexList;
import com.qianjia.zhihuribao.model.RetrofitHelp;

/**
 * Created by Jiansion on 2017/3/14.
 */

public class IndexPresenter implements ModelCallBack<IndexList> {

    private BaseView<IndexList> view;

    public IndexPresenter(BaseView<IndexList> view) {
        this.view = view;
    }

    public void requestIndexData(@Nullable String date) {
        if (view != null && date == null) {
            RetrofitHelp.getZhiHuData(this);
        } else if (view != null) {
            RetrofitHelp.getZhiHuData(date, this);
        }
    }

    @Override
    public void onRequestSuccess(IndexList indexList) {
        if (view != null) {
            view.onSuccess(indexList);
        }

    }

    @Override
    public void onRequestError(BaseView.ErrorType type) {
        if (view != null) {
            view.onError(type);
        }
    }
}
