package com.qianjia.zhihuribao.ui.activity;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.qianjia.basemodel.util.ToastUtil;
import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.ui.fragment.IndexFragment;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mFrameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.mNavigationView)
    NavigationView mNavigationView;
    @BindView(R.id.mDrawerLayout)
    DrawerLayout mDrawerLayout;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("首页");
        mToolbar.setNavigationOnClickListener(v -> {
            if (!mDrawerLayout.isDrawerOpen(mNavigationView)) {
                mDrawerLayout.openDrawer(mNavigationView);
            }
        });
        ViewGroup.LayoutParams layoutParams = mNavigationView.getLayoutParams();
        layoutParams.width = (getResources().getDisplayMetrics().widthPixels / 4) * 3;
        mNavigationView.setLayoutParams(layoutParams);

        mNavigationView.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.page_index:
                    ToastUtil.showToast(mActivity, "首页");
                    break;
                case R.id.page_psychology://好奇心日报
                    ToastUtil.showToast(mActivity, item.getTitle().toString());
                    break;
                case R.id.page_movie://电影日报
                    ToastUtil.showToast(mActivity, item.getTitle().toString());
                    break;
                case R.id.page_bored://不许无聊日报
                    ToastUtil.showToast(mActivity, item.getTitle().toString());
                    break;
                case R.id.page_design:  //设计日报
                    ToastUtil.showToast(mActivity, item.getTitle().toString());
                    break;
                case R.id.page_company://大公司日报
                    ToastUtil.showToast(mActivity, item.getTitle().toString());
                    break;
                case R.id.page_finance://财经日报
                    ToastUtil.showToast(mActivity, item.getTitle().toString());
                    break;
            }
            if (mDrawerLayout.isDrawerOpen(mNavigationView)) {
                mDrawerLayout.closeDrawer(mDrawerLayout);
            }
            return true;
        });
    }

    @Override
    protected void initData() {
        IndexFragment indexFragment = new IndexFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mFrameLayout, indexFragment)
                .commit();

    }

}
