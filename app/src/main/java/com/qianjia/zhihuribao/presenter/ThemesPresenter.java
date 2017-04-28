package com.qianjia.zhihuribao.presenter;

import com.qianjia.basemodel.listener.ModelCallBack;
import com.qianjia.basemodel.view.BaseView;
import com.qianjia.zhihuribao.bean.Theme;
import com.qianjia.zhihuribao.model.ZhiHuRequest;

/**
 * Created by Jiansion on 2017/3/20.
 */

public class ThemesPresenter implements ModelCallBack<Theme> {

    private BaseView<Theme> view;

    public ThemesPresenter(BaseView<Theme> view) {
        this.view = view;
    }

    public void onGetThemes() {
        if (view != null)
            ZhiHuRequest.getThemesList(this);

    }

    @Override
    public void onRequestSuccess(Theme theme) {
        if (view != null)
            view.onSuccess(theme);

    }

    @Override
    public void onRequestError(BaseView.ErrorType type) {
        if (view != null)
            view.onError(type);
    }
}
