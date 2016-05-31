package com.f8boss.zhihuribao.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.util.LogUtil;
import com.f8boss.zhihuribao.util.OkHttpUtils;
import com.f8boss.zhihuribao.util.Urls;
import com.f8boss.zhihuribao.widget.BorderScrollView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiansion on 2016/5/30.
 * 日常心理学
 */
public class FragmentPsychology extends BaseFragment {

    private String TAG = "FragmentPsychology";

    @Bind(R.id.tvColumns)
    TextView tvColumns; //栏目标题

    @Bind(R.id.imageHeader)
    ImageView imageHeader;  //栏目图片


    @Bind(R.id.linearEditor)
    LinearLayout linearEditor;//用于放置栏目主编的头像

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.mBorderScrollView)
    BorderScrollView mBorderScrollView;//外层的ScrollView,主要用于判断滚动到底部是加载跟多的数据

    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout; //最外层的SwipeRefreshLayout,用于下拉刷新数据
    private String url;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psychology, container, false);
        ButterKnife.bind(this, view);
        url = OkHttpUtils.getReplaceFormat(Urls.THEM, "$", "13");
        LogUtil.e(TAG, url);
        LogUtil.e(TAG, "进来了吗?");
        return view;
    }

    @Override
    public void initData(Bundle savedInstanceState) {

        OkHttpUtils.onGetToDownLoadData(mActivity, url, OkHttpUtils.TYPE_TEXT, new OkHttpUtils.OnCallBack() {
            @Override
            public void callBackUIString(String data) {
                LogUtil.e(TAG, data);
            }

            @Override
            public void callBackUIByte(byte[] datas) {

            }
        });

    }


}
