package com.qianjia.zhihuribao.presenter;

import com.qianjia.zhihuribao.bean.ThemesCount;
import com.qianjia.zhihuribao.model.RequestListener;
import com.qianjia.zhihuribao.model.ZhiHuRequest;
import com.qianjia.zhihuribao.ui.view.BaseView;

/**
 * Created by Jiansion on 2017/3/20.
 * 加载其他栏目的链接层
 */

public class ThemesContentPresenter extends BasePresenter<BaseView<ThemesCount>> implements RequestListener<ThemesCount> {


    public ThemesContentPresenter(BaseView<ThemesCount> mView) {
        super(mView);
    }

    public void onGetThemeCount(int id, int lastItemId) {
        if (mView != null)
            if (lastItemId == 0) {
                ZhiHuRequest.getThemesContent(id, this);
            } else {
                ZhiHuRequest.getThemesCountent(id, lastItemId, this);
            }
    }

    @Override
    public void onRequestSuccess(ThemesCount themesCount) {
        if (mView != null) {
            mView.onSuccess(themesCount);
        }
    }

    @Override
    public void onRequestFail(String msg) {
        if (mView != null) {
            mView.onFail(msg);
        }

    }


}
