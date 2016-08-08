package com.f8boss.zhihuribao.fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.adapter.EditAdapter;
import com.f8boss.zhihuribao.adapter.ThemItmeAdapter;
import com.f8boss.zhihuribao.bean.ThemBean;
import com.f8boss.zhihuribao.util.LoaderImageUtil;
import com.f8boss.zhihuribao.util.LogUtil;
import com.f8boss.zhihuribao.util.Urls;
import com.f8boss.zhihuribao.util.Utils;
import com.f8boss.zhihuribao.widget.RecycScrollListener;
import com.google.gson.Gson;
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
 * Created by jiansion on 2016/5/30.
 * 日常心理学
 */
public class ThemFragment extends BaseFragment {
    private TextView tvColumns; //栏目标题
    private ImageView imageHeader;  //栏目图片
    private RecyclerView mRecyclerIcon;//用于放置主编的头像列表
    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout; //最外层的SwipeRefreshLayout,用于下拉刷新数据
    private ThemItmeAdapter themItmeAdapter;
    private Boolean isBottom = true;
    private String url;
    private Gson gson;
    private List<ThemBean.StoriesBean> mList;
    private String TAG = "ThemFragment";

    private String type;


    @SuppressLint("ValidFragment")
    public ThemFragment(String type) {
        this.type = type;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_psychology;
    }

    @Override
    public void initViews(View view, Bundle savedInstanceState) {
        ButterKnife.bind(this, view);
        initSwipeRefreshLayout();
        initObject();
        initRecyclerView();
        initHeaderView();
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        //下载初次数据
        downLoadFirstData();
    }

    //分页
    private void initRecyclerView() {

        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        mRecyclerView.setLayoutManager(manager);
        themItmeAdapter = new ThemItmeAdapter(mActivity, mList);
        mRecyclerView.setAdapter(themItmeAdapter);

        mRecyclerView.addOnScrollListener(new RecycScrollListener() {
            @Override
            public void onBottom() {
                if (isBottom) {
                    int id = mList.get(mList.size() - 1).getId();
                    String urls = url + "/before/" + id;
                    loadLastData(urls);
                }
            }

            @Override
            public void onScrollIdle() {
                Picasso.with(mActivity).resumeTag("ThemImage");
            }

            @Override
            public void onScrollIn() {
                Picasso.with(mActivity).pauseTag("ThemImage");
            }
        });


    }


    private void loadLastData(String urls) {
        OkHttpUtils
                .get(urls)
                .tag(this)
//                .cacheKey("theme_13")
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        LogUtil.e(TAG, "loadLastData:" + s);
                        if (response.equals("{\"stories\":[]}")) {
                            Toast.makeText(mActivity, "已经没有跟多内容了", Toast.LENGTH_SHORT).show();
                            isBottom = false;
                            return;
                        }
                        ThemBean themBean = gson.fromJson(s, ThemBean.class);
                        mList.addAll(themBean.getStories());
                        themItmeAdapter.notifyDataSetChanged();
                    }

                });
    }


    //下载最新数据
    private void downLoadFirstData() {
        OkHttpUtils
                .get(url)
                .tag(this)
//                .cacheKey("theme_13_heade")
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        LogUtil.e(TAG, s);
                        ThemBean themBean = gson.fromJson(s, ThemBean.class);
                        mList.addAll(themBean.getStories());

                        String description = themBean.getDescription();
                        tvColumns.setText(description);
                        String image = themBean.getImage();
                        List<ThemBean.EditorsBean> editors = themBean.getEditors();
                        mRecyclerIcon.setAdapter(new EditAdapter(mActivity, editors));
                        LoaderImageUtil.downLoadImage(mActivity, image, imageHeader);
                        themItmeAdapter.notifyDataSetChanged();

                    }

                });
    }

    private void initObject() {
        url = Utils.getReplaceFormat(Urls.THEM, "$", type);
        gson = new Gson();
        mList = new ArrayList<>();
    }

    private void initHeaderView() {
        //初始化头布局,并添加头布局
        View view = LayoutInflater.from(mActivity).inflate(R.layout.fragment_other_header, mRecyclerView, false);
        mRecyclerIcon = (RecyclerView) view.findViewById(R.id.mRecyclerIcon);
        imageHeader = (ImageView) view.findViewById(R.id.imageHeader);
        tvColumns = (TextView) view.findViewById(R.id.tvColumns);
        LinearLayoutManager iconManage = new LinearLayoutManager(mActivity, LinearLayoutManager.HORIZONTAL, false) {
            @Override//设置布局管理器不能水平滚动
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        mRecyclerIcon.setLayoutManager(iconManage);

        themItmeAdapter.setHeaderView(view);
    }

    private void initSwipeRefreshLayout() {
        //设置一组刷新过程中的颜色
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downLoadFirstData();
                //更新结束
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
