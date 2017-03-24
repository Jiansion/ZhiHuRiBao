package com.qianjia.zhihuribao.ui.fragment;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianjia.basemodel.util.ToastUtil;
import com.qianjia.basemodel.view.BaseView;
import com.qianjia.statuslayout.StatusLayout;
import com.qianjia.swiperefresh.SwipeRefreshLayout;
import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.adapter.ThemesCountAdapter;
import com.qianjia.zhihuribao.base.BaseFragment;
import com.qianjia.zhihuribao.bean.ThemesCount;
import com.qianjia.zhihuribao.presenter.ThemesContentPresenter;
import com.qianjia.zhihuribao.util.ImageLoaderUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Jiansion on 2017/3/20.
 * 各类栏目
 */

public class ThemesFragment extends BaseFragment implements BaseView<ThemesCount> {

    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.mSwipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;

    @BindView(R.id.mStatusLayout)
    StatusLayout mStatusLayout;

    private View heardView;
    //栏目主题图片
    private ImageView imPoster;
    //栏目说明
    private TextView tvCaption;

    private ThemesCountAdapter adapter;

    private final static String THEME_KEY = "theme_key";

    private ThemesContentPresenter presenter;

    //栏目ID
    private int id;

    private int lastItemId = 0;

    /**
     * 传入列表参数获取对象的Fragment
     *
     * @param id 栏目对应的Id
     * @return fragment
     */
    public static ThemesFragment startFragment(int id) {
        ThemesFragment fragment = new ThemesFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(THEME_KEY, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_themes;
    }

    @Override
    protected void initViews() {
        mStatusLayout.showLoading();

        heardView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_themes_header, mRecyclerView, false);
        imPoster = (ImageView) heardView.findViewById(R.id.imPoster);
        tvCaption = (TextView) heardView.findViewById(R.id.tvCaption);

        mSwipeRefresh.setMode(SwipeRefreshLayout.Mode.BOTH);
        mSwipeRefresh.setOnRefreshListener(() -> {
            lastItemId = 0;
            presenter.onGetThemeCount(id, lastItemId);
        });
        mSwipeRefresh.setOnPullUpRefreshListener(() ->
        {
            if (lastItemId != 0) {
                presenter.onGetThemeCount(id, lastItemId);
            } else {
                mSwipeRefresh.setPullUpRefreshing(false);
            }
        });
    }

    @Override
    protected void initData() {
        id = getArguments().getInt(THEME_KEY, 0);
        presenter = new ThemesContentPresenter(this);
        presenter.onGetThemeCount(id, 0);

        adapter = new ThemesCountAdapter(mActivity, new ArrayList<>());
        mRecyclerView.setAdapter(adapter);
        adapter.setHeadView(heardView);

    }


    @Override
    public void onSuccess(ThemesCount themesCount) {
        mSwipeRefresh.setRefreshing(false);
        mSwipeRefresh.setPullUpRefreshing(false);
        List<ThemesCount.StoriesBean> stories = themesCount.getStories();
        if (lastItemId == 0) {
            String background = themesCount.getBackground();
            ImageLoaderUtil.loadImage(mActivity, background, imPoster);
            String description = themesCount.getDescription();
            tvCaption.setText(description);
            mStatusLayout.showContent();
            adapter.updateItems(stories);
        } else {
            adapter.addItems(stories);
        }

        lastItemId = stories.get(stories.size() - 1).getId();

    }

    @Override
    public void onError(ErrorType type) {
        mSwipeRefresh.setRefreshing(false);
        mSwipeRefresh.setPullUpRefreshing(false);
        if (lastItemId == 0) {
            mStatusLayout.showError("网络链接异常", v -> {
                mStatusLayout.showLoading();
                presenter.onGetThemeCount(id, lastItemId);
            });
        } else {
            ToastUtil.showToast(mActivity, "网络异常");
        }
    }

}
