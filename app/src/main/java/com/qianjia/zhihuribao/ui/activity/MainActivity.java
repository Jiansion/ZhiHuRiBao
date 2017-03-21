package com.qianjia.zhihuribao.ui.activity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.widget.FrameLayout;

import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.ui.fragment.IndexFragment;
import com.qianjia.zhihuribao.ui.fragment.ThemeMenuFragment;
import com.qianjia.zhihuribao.ui.fragment.ThemesFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity implements ThemeMenuFragment.ItemSelectListener {

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mFrameLayout)
    FrameLayout mFrameLayout;
    @BindView(R.id.mDrawerLayout)
    DrawerLayout mDrawerLayout;


    private ThemeMenuFragment themesFragment;

    private Map<Integer, Fragment> map;

    private int firstId = -1;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setTitle("首页");
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(mActivity, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name);
        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        themesFragment = (ThemeMenuFragment) getSupportFragmentManager().findFragmentById(R.id.menuFragment);
    }

    @SuppressLint("UseSparseArrays")
    @Override
    protected void initData() {
        map = new HashMap<>();
        IndexFragment indexFragment = new IndexFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.mFrameLayout, indexFragment)
                .commit();
        map.put(-1, indexFragment);
    }


    /**
     * 关闭侧滑菜单
     */
    public void closeMenu() {
        mDrawerLayout.closeDrawers();
    }

    //点击切换Item,首页的ID 为 :-1
    @Override
    public void onItemSelectListener(int id, String title) {
        if (firstId == id) {
            return;
        }
        mToolbar.setTitle(title);

        if (id != -1) {//切换其他页面
            ThemesFragment fragment = (ThemesFragment) map.get(id);
            if (fragment != null) {
                //不为空时直接切换
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .hide(map.get(firstId))
                        .show(fragment)
                        .commit();
            } else {
                //为空时,创建对象放入Map中,在进行切换
                ThemesFragment themesFragment = ThemesFragment.startFragment(id);
                map.put(id, themesFragment);
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                        .hide(map.get(firstId))
                        .add(R.id.mFrameLayout, themesFragment)
                        .commit();
            }

        } else {
            //切换为首页
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .hide(map.get(firstId))
                    .show(map.get(id))
                    .commit();
        }
        firstId = id;
    }

    @SuppressLint("RtlHardcoded")
    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT) || mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawers();
            return;
        }
        if (firstId == -1) {
            super.onBackPressed();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right)
                    .hide(map.get(firstId))
                    .show(map.get(-1))
                    .commit();
            firstId = -1;
            mToolbar.setTitle("首页");
            themesFragment.selectView.setBackgroundColor(0xFFFFFF);
        }
    }

}
