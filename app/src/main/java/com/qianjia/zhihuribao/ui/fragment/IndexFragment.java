package com.qianjia.zhihuribao.ui.fragment;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;

import com.qianjia.basemodel.util.ToastUtil;
import com.qianjia.basemodel.view.BaseView;
import com.qianjia.statuslayout.StatusLayout;
import com.qianjia.swiperefresh.SwipeRefreshLayout;
import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.adapter.IndexAdapter;
import com.qianjia.zhihuribao.base.BaseFragment;
import com.qianjia.zhihuribao.bean.IndexList;
import com.qianjia.zhihuribao.presenter.IndexPresenter;
import com.qianjia.zhihuribao.ui.activity.DetailDefaultActivity;
import com.qianjia.zhihuribao.ui.activity.DetailOtherActivity;
import com.qianjia.zhihuribao.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Jiansion on 2017/3/7.
 * 首页
 */

public class IndexFragment extends BaseFragment implements BaseView<IndexList> {
    @BindView(R.id.mRecyclerView)
    RecyclerView mRecyclerView;
    @BindView(R.id.mSwipeRefresh)
    SwipeRefreshLayout mSwipeRefresh;
    @BindView(R.id.mStatusLayout)
    StatusLayout mStatusLayout;

    private Banner banner;

    private IndexAdapter adapter;

    private IndexPresenter presenter;

    private View headView;

    //用于加载以往的信息
    private String data;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_themes;
    }

    @Override
    protected void initViews() {
        mStatusLayout.showLoading();
        headView = LayoutInflater.from(mActivity).inflate(R.layout.item_roll_view, null);
        banner = (Banner) headView.findViewById(R.id.banner);
        banner.setImageLoader(new GlideImageLoader());


        mSwipeRefresh.setMode(SwipeRefreshLayout.Mode.BOTH);

        mSwipeRefresh.setOnRefreshListener(() -> {
            data = null;
            presenter.requestIndexData(null);
        });

        mSwipeRefresh.setOnPullUpRefreshListener(() -> {
            if (!TextUtils.isEmpty(data)) {
                presenter.requestIndexData(data);
            } else {
                mSwipeRefresh.setPullUpRefreshing(false);
            }
        });

    }

    @Override
    protected void initData() {
        List<IndexList.StoriesBean> lists = new ArrayList<>();
        adapter = new IndexAdapter(mActivity, lists);
        adapter.setHeadView(headView);
        mRecyclerView.setAdapter(adapter);
        presenter = new IndexPresenter(this);
        presenter.requestIndexData(null);
    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoPlay();
    }

    @Override
    public void onResume() {
        super.onResume();
        banner.startAutoPlay();
    }

    @Override
    public void onSuccess(IndexList indexList) {
        data = indexList.getDate();
        List<IndexList.TopStoriesBean> top_stories = indexList.getTop_stories();
        List<IndexList.StoriesBean> stories = indexList.getStories();
        if (top_stories != null) {
            setFirstPage(top_stories, stories);
        } else {
            int itemCount = adapter.getItemCount();
            adapter.addItems(stories);
            mRecyclerView.scrollToPosition(itemCount);
        }

        mSwipeRefresh.setRefreshing(false);
        mSwipeRefresh.setPullUpRefreshing(false);


    }

    private void setFirstPage(List<IndexList.TopStoriesBean> top_stories, List<IndexList.StoriesBean> stories) {
        if (stories.isEmpty()) {
            mStatusLayout.showEmpty();
        } else {
            mStatusLayout.showContent();
        }
        adapter.updateData(stories);
        List<String> imList = new ArrayList<>();
        List<String> titleList = new ArrayList<>();
        for (IndexList.TopStoriesBean bean : top_stories) {
            imList.add(bean.getImage());
            titleList.add(bean.getTitle());
        }
        banner.update(imList, titleList);
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        banner.start();
        banner.setOnBannerListener(position -> {
            int id = top_stories.get(position).getId();
            int type = top_stories.get(position).getType();
            if (type == 0) {
                DetailDefaultActivity.onToDetailPage(mActivity, id, banner);
            } else {
                DetailOtherActivity.onToDetailPage(mActivity, id, type);
            }
        });
    }

    @Override
    public void onError(ErrorType type) {
        mSwipeRefresh.setRefreshing(false);
        mSwipeRefresh.setPullUpRefreshing(false);

        if (TextUtils.isEmpty(data)) {
            mStatusLayout.showError(type == ErrorType.NETERROR ? "发生网络异常" : "发生未知错误", v -> {
                mStatusLayout.showLoading();
                presenter.requestIndexData(null);
            });
        } else {
            switch (type) {
                case NETERROR:
                    ToastUtil.showToast(mActivity, "网络异常");
                    break;
                case EXCEPTION:
                    ToastUtil.showToast(mActivity, "异常");
                    break;
            }
        }

    }
}
