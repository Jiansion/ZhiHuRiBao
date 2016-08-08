package com.f8boss.zhihuribao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.adapter.RecyclerAdapter;
import com.f8boss.zhihuribao.adapter.RollPageViewAdapter;
import com.f8boss.zhihuribao.bean.IndextItemBean;
import com.f8boss.zhihuribao.util.LogUtil;
import com.f8boss.zhihuribao.util.Urls;
import com.f8boss.zhihuribao.widget.RecycScrollListener;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiansion on 2016/5/25.
 * 首页
 */
public class FragmentIndext extends BaseFragment {

    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;


    private RollPagerView mRollpageView;

    private Gson gson;

    private int beforeData;


    //用于存放首页数据的个Item项
    private List<IndextItemBean.StoriesBean> itemList;
    //轮播的图片数据
    private List<IndextItemBean.TopStoriesBean> headerList;

    private String TAG = "FragmentIndext";
    private RecyclerAdapter recyclerAdapter;
    private RollPageViewAdapter rollPageViewAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    //初始化视图
    @Override
    public void initViews(View container, Bundle savedInstanceState) {
        ButterKnife.bind(this, container);
        initRecyclerView();
        initObject();
        initSwipeRefreshLayout();
    }

    //初始化数据
    @Override
    public void initData(Bundle savedInstanceState) {
        downFirstData();
    }

    private void initSwipeRefreshLayout() {
        //设置一组刷新过程中的颜色
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downFirstData();
                //更新结束
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    //用于分页加载跟多数据
    private void page() {
        LogUtil.e(TAG, "滑动到了底部");
        OkHttpUtils
                .get(Urls.ZHIHU_BEFORE + (beforeData - 1))
                .tag(this)
                .cacheKey("beforeData")
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {

                        IndextItemBean indextItemBean = gson.fromJson(s, IndextItemBean.class);
                        beforeData = new Integer(indextItemBean.getDate());
                        itemList.addAll(indextItemBean.getStories());
                        recyclerAdapter.notifyDataSetChanged();
                    }
                });


    }

    //初始化RecyclerView,并设置RecyclerView的布局管理器
    private void initRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnScrollListener(new RecycScrollListener() {
            @Override
            public void onBottom() {
                page();
            }

            @Override
            public void onScrollIdle() {
                //滑动停止恢复加载图片
                Picasso.with(mActivity).resumeTag("indexImage");
            }

            @Override
            public void onScrollIn() {
                //列表滑动时停止加载图片
                Picasso.with(mActivity).pauseTag("indexImage");
            }
        });

    }

    //初始化各种对象
    private void initObject() {
        gson = new Gson();
        itemList = new ArrayList<>();
        headerList = new ArrayList<>();
        recyclerAdapter = new RecyclerAdapter(mActivity, itemList);
        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_index_header, mRecyclerView, false);
        mRollpageView = (RollPagerView) headerView.findViewById(R.id.mRollPageView);
        recyclerAdapter.setHeaderView(headerView);
        mRollpageView.setHintView(new ColorPointHintView(mActivity, Color.WHITE, mActivity.getResources().getColor(R.color.colorGray)));
        mRecyclerView.setAdapter(recyclerAdapter);

    }


    //下载首页的数据,或刷新数据
    private void downFirstData() {
        OkHttpUtils
                .get(Urls.ZHIHUNEWS)
                .tag(this)
                .cacheKey("latest")
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        IndextItemBean indextItemBean = gson.fromJson(s, IndextItemBean.class);
                        String date = indextItemBean.getDate();
                        beforeData = new Integer(date);
                        itemList.clear();
                        headerList.clear();
                        itemList.addAll(indextItemBean.getStories());
                        headerList.addAll(indextItemBean.getTop_stories());
                        if (rollPageViewAdapter == null) {
                            rollPageViewAdapter = new RollPageViewAdapter(mActivity, headerList);
                            mRollpageView.setAdapter(rollPageViewAdapter);
                        } else {
                            rollPageViewAdapter.notifyDataSetChanged();
                        }
                        recyclerAdapter.notifyDataSetChanged();
                    }

                });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
