package com.qianjia.zhihuribao.presenter;

import com.qianjia.basemodel.listener.ModelCallBack;
import com.qianjia.basemodel.view.BaseView;
import com.qianjia.zhihuribao.bean.Detail;
import com.qianjia.zhihuribao.model.ZhiHuRequest;

/**
 * Created by Jiansion on 2017/3/15.
 */

public class DetailPresenter implements ModelCallBack<Detail> {

    private BaseView<Detail> view;

    public DetailPresenter(BaseView<Detail> view) {
        this.view = view;
    }

    public void onGetDatailData(int id) {
        if (view != null) {
            ZhiHuRequest.getZhiHuDetail(id, this);
        }
    }

    @Override
    public void onRequestSuccess(Detail detail) {
        if (view != null) {
            view.onSuccess(detail);
        }
    }

    @Override
    public void onRequestError(BaseView.ErrorType type) {
        if (view != null) {
            view.onError(type);
        }
    }
}
