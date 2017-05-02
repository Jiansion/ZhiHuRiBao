package com.qianjia.zhihuribao.presenter;

import android.support.annotation.Nullable;

import com.qianjia.zhihuribao.bean.IndexList;
import com.qianjia.zhihuribao.model.RequestListener;
import com.qianjia.zhihuribao.model.ZhiHuRequest;
import com.qianjia.zhihuribao.ui.view.BaseView;

/**
 * Created by Jiansion on 2017/3/14.
 * 知乎日报首页数据
 */

public class IndexPresenter extends BasePresenter<BaseView<IndexList>> implements RequestListener<IndexList> {


    public IndexPresenter(BaseView<IndexList> mView) {
        super(mView);
    }


    public void requestIndexData(@Nullable String date) {
        if (mView != null && date == null) {
            ZhiHuRequest.getZhiHuData(this);
        } else if (mView != null) {
            ZhiHuRequest.getZhiHuData(date, this);
        }
    }


    @Override
    public void onRequestSuccess(IndexList indexList) {
        if (indexList != null) {
            mView.onSuccess(indexList);
        } else {
            mView.onFail("请求失败");
        }

    }

    @Override
    public void onRequestFail(String msg) {
        mView.onFail(msg);
    }
}
