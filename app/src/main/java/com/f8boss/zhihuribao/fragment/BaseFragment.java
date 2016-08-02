package com.f8boss.zhihuribao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.f8boss.zhihuribao.activity.BaseActivity;

/**
 * Created by jiansion on 2016/5/25.
 * 基类Fragment
 */
public abstract class BaseFragment extends Fragment {
    public BaseActivity mActivity;
    private View view;

    // fragment创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (BaseActivity) getActivity();
    }

    // 处理fragment的布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(mActivity).inflate(getLayoutId(), container, false);
            initViews(view, savedInstanceState);
        }
        return view;
    }


    // 依附的activity创建完成
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    /**
     * 该抽象方法就是 onCreateView中需要的layoutID
     *
     * @return Fragment的布局
     */
    protected abstract int getLayoutId();

    /**
     * 该抽象方法就是 初始化view
     *
     * @param container          布局容器
     * @param savedInstanceState savedInstanceState
     */
    protected abstract void initViews(View container, Bundle savedInstanceState);

    /**
     * 初始化数据
     * 可直接在此加载数据
     */
    protected abstract void initData(Bundle savedInstanceState);


}
