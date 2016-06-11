package com.f8boss.zhihuribao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.adapter.EditAdapter;
import com.f8boss.zhihuribao.adapter.ThemItmeAdapter;
import com.f8boss.zhihuribao.bean.ThemBean;
import com.f8boss.zhihuribao.util.ImageLoaderUtil;
import com.f8boss.zhihuribao.util.LogUtil;
import com.f8boss.zhihuribao.util.OkHttpUtils;
import com.f8boss.zhihuribao.util.Urls;
import com.f8boss.zhihuribao.widget.BorderScrollView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

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
    RelativeLayout linearEditor;

    @Bind(R.id.mRecyclerIcon)
    RecyclerView mRecyclerIcon;

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @Bind(R.id.mBorderScrollView)
    BorderScrollView mBorderScrollView;//外层的ScrollView,主要用于判断滚动到底部是加载跟多的数据

    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout; //最外层的SwipeRefreshLayout,用于下拉刷新数据

    private ThemItmeAdapter themItmeAdapter;

    private Boolean isBottom = true;

    private String url;

    private Gson gson;
    private List<ThemBean.StoriesBean> mList;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psychology, container, false);
        ButterKnife.bind(this, view);
        initSwipeRefreshLayout();
        initPaging();
        url = OkHttpUtils.getReplaceFormat(Urls.THEM, "$", "13");
        initObject();
        LogUtil.e(TAG, url);
        LogUtil.e(TAG, "进来了吗?");
        return view;
    }

    //分页
    private void initPaging() {
        mBorderScrollView.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom() {
                if (isBottom) {
                    int id = mList.get(mList.size() - 1).getId();
                    String urls = url + "/before/" + id;
                    loadLastData(urls);
                }
            }

            @Override
            public void onTop() {
            }
        });
    }

    private void loadLastData(String urls) {
        OkHttpUtils.onGetToDownLoadData(mActivity, urls, OkHttpUtils.TYPE_TEXT, new OkHttpUtils.OnCallBack() {
            @Override
            public void callBackUIString(String data) {
                LogUtil.e(TAG, "loadLastData:" + data);
                if (data.equals("{\"stories\":[]}")) {
                    Toast.makeText(mActivity, "已经没有跟多内容了", Toast.LENGTH_SHORT).show();
                    isBottom = false;
                    return;
                }
                ThemBean themBean = gson.fromJson(data, ThemBean.class);
                mList.addAll(themBean.getStories());
                themItmeAdapter.notifyDataSetChanged();
            }

            @Override
            public void callBackUIByte(byte[] datas) {

            }
        });
    }


    @Override
    public void initData(Bundle savedInstanceState) {
        //下载初次数据
        downLoadFirstData();

    }

    //下载最新数据
    private void downLoadFirstData() {
        OkHttpUtils.onGetToDownLoadData(mActivity, url, OkHttpUtils.TYPE_TEXT, new OkHttpUtils.OnCallBack() {

            @Override
            public void callBackUIString(String data) {
                LogUtil.e(TAG, data);
                ThemBean themBean = gson.fromJson(data, ThemBean.class);
                mList.addAll(themBean.getStories());


                String background = themBean.getBackground();
                String image_source = themBean.getImage_source();

                String image = themBean.getImage();

                List<ThemBean.EditorsBean> editors = themBean.getEditors();
                mRecyclerIcon.setAdapter(new EditAdapter(mActivity, editors));
                ImageLoaderUtil.displayImage(image, imageHeader);


                if (themItmeAdapter == null) {
                    themItmeAdapter = new ThemItmeAdapter(mActivity, mList);
                    mRecyclerView.setAdapter(themItmeAdapter);
                } else {
                    themItmeAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void callBackUIByte(byte[] datas) {

            }
        });
    }

    private void initObject() {
        gson = new Gson();
        mList = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(mActivity) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };
        mRecyclerView.setLayoutManager(manager);

        LinearLayoutManager iconManage = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false) {
            @Override
            public boolean canScrollHorizontally() {
                return false;
            }
        };
        mRecyclerIcon.setLayoutManager(iconManage);


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


}