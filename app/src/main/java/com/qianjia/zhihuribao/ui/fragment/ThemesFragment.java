package com.qianjia.zhihuribao.ui.fragment;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianjia.basemodel.view.ProgressView;
import com.qianjia.statuslayout.StatusLayout;
import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.adapter.ThemesCountAdapter;
import com.qianjia.zhihuribao.base.BaseFragment;
import com.qianjia.zhihuribao.bean.ThemesCount;
import com.qianjia.zhihuribao.presenter.ThemesContentPresenter;
import com.qianjia.zhihuribao.util.ImageLoaderUtil;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Jiansion on 2017/3/20.
 * 各类栏目
 */

public class ThemesFragment extends BaseFragment implements ProgressView<ThemesCount> {

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

//    private Handler handler = new Handler();


    private ThemesCountAdapter adapter;

    private final static String THEME_KEY = "theme_key";

    private ThemesContentPresenter presenter;

    private int id;

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
        mSwipeRefresh.setOnRefreshListener(() -> presenter.onGetThemeCount(id));
        heardView = LayoutInflater.from(mActivity).inflate(R.layout.fragment_themes_header, mRecyclerView, false);
        imPoster = (ImageView) heardView.findViewById(R.id.imPoster);
        tvCaption = (TextView) heardView.findViewById(R.id.tvCaption);


    }

    @Override
    protected void initData() {
        id = getArguments().getInt(THEME_KEY, 0);
        presenter = new ThemesContentPresenter(this);
        presenter.onGetThemeCount(id);

        adapter = new ThemesCountAdapter(mActivity, new ArrayList<>());
        mRecyclerView.setAdapter(adapter);
        adapter.setHeadView(heardView);

    }


    @Override
    public void onSuccess(ThemesCount themesCount) {
        String background = themesCount.getBackground();
//        String image = themesCount.getImage();

        ImageLoaderUtil.loadImage(mActivity, background, imPoster);
//        new Thread(() -> {
//            Bitmap bitmap = ImageLoaderUtil.loadBitmap(mActivity, background);
//            handler.post(() -> imPoster.setBackground(new BitmapDrawable(bitmap)));
//        }).start();

        String description = themesCount.getDescription();
        tvCaption.setText(description);

        adapter.addItems(themesCount.getStories());
        mStatusLayout.showContent();
    }

    @Override
    public void onError(ErrorType type) {
        mStatusLayout.showError("网络链接异常", v -> {
            mStatusLayout.showLoading();
            presenter.onGetThemeCount(id);
        });
    }

    @Override
    public void onShowProgress() {

    }

    @Override
    public void onHindProgress() {
        if (mSwipeRefresh.isRefreshing()) {
            mSwipeRefresh.setRefreshing(false);
        }
    }
}
