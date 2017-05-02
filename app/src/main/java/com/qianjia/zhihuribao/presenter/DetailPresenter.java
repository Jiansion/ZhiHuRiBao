package com.qianjia.zhihuribao.presenter;

import com.qianjia.zhihuribao.bean.Detail;
import com.qianjia.zhihuribao.model.RequestListener;
import com.qianjia.zhihuribao.model.ZhiHuRequest;
import com.qianjia.zhihuribao.ui.view.BaseView;

/**
 * Created by Jiansion on 2017/3/15.
 */

public class DetailPresenter extends BasePresenter<BaseView<Detail>> implements RequestListener<Detail> {


    public DetailPresenter(BaseView<Detail> mView) {
        super(mView);
    }

    public void onGetDetailData(int id) {
        if (mView != null) {
            ZhiHuRequest.getZhiHuDetail(id, this);
        }
    }

    @Override
    public void onRequestSuccess(Detail detail) {
        if (mView != null) {
            mView.onSuccess(detail);
        }

    }

    @Override
    public void onRequestFail(String msg) {
        if (mView != null) {
            mView.onFail(msg);
        }
    }
}
