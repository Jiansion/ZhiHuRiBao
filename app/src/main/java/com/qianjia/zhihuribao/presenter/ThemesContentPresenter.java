package com.qianjia.zhihuribao.presenter;

import com.qianjia.basemodel.listener.ModelCallBack;
import com.qianjia.basemodel.view.BaseView;
import com.qianjia.basemodel.view.ProgressView;
import com.qianjia.zhihuribao.bean.ThemesCount;
import com.qianjia.zhihuribao.model.RetrofitHelp;

/**
 * Created by Jiansion on 2017/3/20.
 */

public class ThemesContentPresenter implements ModelCallBack<ThemesCount> {

    private ProgressView<ThemesCount> view;

    public ThemesContentPresenter(ProgressView<ThemesCount> view) {
        this.view = view;
    }

    public void onGetThemeCount(int id) {
        if (view != null)
            view.onShowProgress();
        RetrofitHelp.getThemesContnet(id, this);
    }

    @Override
    public void onRequestSuccess(ThemesCount themesCount) {
        if (view != null) {
            view.onHindProgress();
            view.onSuccess(themesCount);
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
