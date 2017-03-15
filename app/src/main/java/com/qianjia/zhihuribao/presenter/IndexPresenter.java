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

    private ProgressView<IndexList> view;

    public IndexPresenter(ProgressView<IndexList> view) {
        this.view = view;
    }

    public void requestIndexData(@Nullable String date) {
        if (view != null && date == null) {
            view.onShowProgress();
            RetrofitHelp.getZhiHuData(this);
        } else if (view != null) {
            view.onShowProgress();
            RetrofitHelp.getZhiHuData(date, this);
        }
    }

    @Override
    public void onRequestSuccess(IndexList indexList) {
        if (view != null) {
            view.onHindProgress();
            view.onSuccess(indexList);
        }

    }

    @Override
    public void onRequestError(BaseView.ErrorType type) {
        if (view != null) {
            view.onHindProgress();
            view.onError(type);
        }
    }
}
