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
import android.widget.ProgressBar;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.adapter.RecyclerAdapter;
import com.f8boss.zhihuribao.adapter.RollPageViewAdapter;
import com.f8boss.zhihuribao.bean.IndextItemBean;
import com.f8boss.zhihuribao.util.Urls;
import com.f8boss.zhihuribao.widget.RecycScrollListener;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.lzy.okhttputils.request.BaseRequest;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiansion on 2016/5/25.
 * 首页
 */
public class FragmentIndex extends BaseFragment {

    @BindView(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;

    private RollPagerView mRollpageView;

    private Gson gson;

    private int beforeData;


    //用于存放首页数据的个Item项
    private List<IndextItemBean.StoriesBean> itemList;
    //轮播的图片数据
    private List<IndextItemBean.TopStoriesBean> headerList;

    private final static String TAG = "FragmentIndex";
    private RecyclerAdapter recyclerAdapter;
    private RollPageViewAdapter rollPageViewAdapter;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_index;
    }

    //初始化视图
    @Override
    public void initViews(View container, Bundle savedInstanceState) {
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
        mSwipeRefreshLayout.setRefreshing(true);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downFirstData();
            }
        });

    }

    //用于分页加载跟多数据
    private void pageMore() {
        OkHttpUtils
                .get(Urls.ZHIHU_BEFORE + (beforeData - 1))
                .tag(this)
                .cacheKey(Urls.ZHIHU_BEFORE + (beforeData - 1))
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        mProgressBar.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        IndextItemBean indextItemBean = gson.fromJson(s, IndextItemBean.class);
                        beforeData = Integer.valueOf(indextItemBean.getDate());
                        itemList.addAll(indextItemBean.getStories());
                        recyclerAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onAfter(boolean isFromCache, @Nullable String s, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onAfter(isFromCache, s, call, response, e);
                        mProgressBar.setVisibility(View.GONE);
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
                pageMore();
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
        mRollpageView.setHintView(new ColorPointHintView(mActivity, Color.WHITE, Color.GRAY));
        mRecyclerView.setAdapter(recyclerAdapter);

    }


    //下载首页的数据,或刷新数据
    private void downFirstData() {
        OkHttpUtils
                .get(Urls.ZHIHUNEWS)
                .tag(this)
                .cacheKey(Urls.ZHIHUNEWS)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onBefore(BaseRequest request) {
                        super.onBefore(request);
                        mSwipeRefreshLayout.post(new Runnable() {//开启首次进来自动刷新动画
                            @Override
                            public void run() {
                                mSwipeRefreshLayout.setRefreshing(true);
                            }
                        });
                    }

                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        IndextItemBean indextItemBean = gson.fromJson(s, IndextItemBean.class);
                        String date = indextItemBean.getDate();
                        beforeData = Integer.valueOf(date);
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

                    @Override
                    public void onAfter(boolean isFromCache, @Nullable String s, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onAfter(isFromCache, s, call, response, e);
                        //更新结束
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
    }


}
