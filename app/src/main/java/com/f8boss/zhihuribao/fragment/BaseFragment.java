package com.f8boss.zhihuribao.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by jiansion on 2016/5/25.
 */
public abstract class BaseFragment extends Fragment {


    public AppCompatActivity mActivity;

    private View view;

    // fragment创建
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = (AppCompatActivity) getActivity();
    }

    // 处理fragment的布局
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = initViews(inflater, container, savedInstanceState);
        }
        return view;
    }

    // 依附的activity创建完成
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData(savedInstanceState);
    }

    // 子类必须实现初始化布局的方法
    public abstract View initViews(LayoutInflater inflater, ViewGroup container,
                                   Bundle savedInstanceState);

    /**
     * 初始化数据
     * 可直接在此加载数据
     */
    public abstract void initData(Bundle savedInstanceState);


}
