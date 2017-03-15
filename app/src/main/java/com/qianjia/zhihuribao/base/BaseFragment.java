package com.qianjia.zhihuribao.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by jiansion on 2016/5/6.
 * Fragemen的基类
 */
public abstract class BaseFragment extends Fragment {

    public BaseActivity mActivity;

    protected Unbinder mUnbinder;

    protected abstract int getLayoutId();

    protected abstract void initViews();

    protected abstract void initData();


    // fragment创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    // 处理fragment的布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = LayoutInflater.from(mActivity).inflate(getLayoutId(), container, false);
        mUnbinder = ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    // 依附的activity创建完成
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();
    }


}
