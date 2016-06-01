package com.f8boss.zhihuribao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f8boss.zhihuribao.R;
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
    LinearLayout linearEditor;//用于放置栏目主编的头像

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @Bind(R.id.mBorderScrollView)
    BorderScrollView mBorderScrollView;//外层的ScrollView,主要用于判断滚动到底部是加载跟多的数据

    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout; //最外层的SwipeRefreshLayout,用于下拉刷新数据

    private ThemItmeAdapter themItmeAdapter;

    private String url;

    private Gson gson;
    private List<ThemBean.StoriesBean> mList;

    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_psychology, container, false);
        ButterKnife.bind(this, view);
        initSwipeRefreshLayout();
        url = OkHttpUtils.getReplaceFormat(Urls.THEM, "$", "13");
        initObject();
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
                ThemBean themBean = gson.fromJson(data, ThemBean.class);
                mList.addAll(themBean.getStories());

                String background = themBean.getBackground();
                String image = themBean.getImage();
                String image_source = themBean.getImage_source();
                List<ThemBean.EditorsBean> editors = themBean.getEditors();

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
    }

    private void initSwipeRefreshLayout() {
        //设置一组刷新过程中的颜色
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                downFirstData();
                //更新结束
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }


}
