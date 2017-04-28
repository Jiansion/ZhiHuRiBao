package com.qianjia.zhihuribao.presenter;

import com.qianjia.basemodel.listener.ModelCallBack;
import com.qianjia.basemodel.view.BaseView;
import com.qianjia.zhihuribao.bean.ThemesCount;
import com.qianjia.zhihuribao.model.ZhiHuRequest;

/**
 * Created by Jiansion on 2017/3/20.
 * 加载其他栏目的链接层
 */

public class ThemesContentPresenter implements ModelCallBack<ThemesCount> {

    private BaseView<ThemesCount> view;

    public ThemesContentPresenter(BaseView<ThemesCount> view) {
        this.view = view;
    }

    public void onGetThemeCount(int id, int lastItemId) {
        if (view != null)
            if (lastItemId == 0) {
                ZhiHuRequest.getThemesContent(id, this);
            } else {
                ZhiHuRequest.getThemesCountent(id, lastItemId, this);
            }
    }

    @Override
    public void onRequestSuccess(ThemesCount themesCount) {
        if (view != null) {
            view.onSuccess(themesCount);
        }
    }

    @Override
    public void onRequestError(BaseView.ErrorType type) {
        if (view != null) {
            view.onError(type);
        }
    }
}
