package com.qianjia.zhihuribao.presenter;


import com.qianjia.zhihuribao.bean.Theme;
import com.qianjia.zhihuribao.model.RequestListener;
import com.qianjia.zhihuribao.model.ZhiHuRequest;
import com.qianjia.zhihuribao.ui.view.BaseView;

/**
 * Created by Jiansion on 2017/3/20.
 * 新闻栏目列表
 */

public class ThemesPresenter extends BasePresenter<BaseView<Theme>> implements RequestListener<Theme> {


    public ThemesPresenter(BaseView<Theme> mView) {
        super(mView);
    }

    public void onGetThemes() {
        if (mView != null)
            ZhiHuRequest.getThemesList(this);

    }

    @Override
    public void onRequestSuccess(Theme theme) {
        if (mView != null)
            mView.onSuccess(theme);

    }

    @Override
    public void onRequestFail(String msg) {
        if (mView != null)
            mView.onFail(msg);
    }


}
