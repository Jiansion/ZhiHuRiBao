package com.f8boss.zhihuribao.activity;

import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.ArrayMap;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.fragment.BaseFragment;
import com.f8boss.zhihuribao.fragment.FragmentIndex;
import com.f8boss.zhihuribao.fragment.ThemFragment;
import com.f8boss.zhihuribao.util.ThemType;

import java.util.Map;

import butterknife.BindView;

public class MainActivity extends BaseActivity {

    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.navigation_menu)
    NavigationView navigationMenu;
    @BindView(R.id.mDrawerLayout)
    DrawerLayout mDrawerLayout;

    private View headerView;


    private Map<String, BaseFragment> fragmentMap;

    private String nowTag = "fragmentIndex";

    private boolean isBack;


    @Override
    protected int initLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        initBar();
        initHeaderView();
        initMenu();


    }

    @Override
    protected void initData() {
        fragmentMap = new ArrayMap<>();
        initIndexFragment();
    }

    private void initIndexFragment() {
        FragmentIndex fragmentIndex = new FragmentIndex();
        fragmentMap.put("fragmentIndex", fragmentIndex);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentIndex).commit();
    }

    private void initBar() {
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationIcon(R.mipmap.img_menu);
        mToolbar.setTitleTextColor(Color.WHITE);
    }


    private void initMenu() {
        navigationMenu.addHeaderView(headerView);
        ViewGroup.LayoutParams layoutParams = navigationMenu.getLayoutParams();
        layoutParams.width = (getResources().getDisplayMetrics().widthPixels / 4) * 3;
        navigationMenu.setLayoutParams(layoutParams);


        navigationMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                mToolbar.setTitle(item.getTitle().toString());
                switch (item.getItemId()) {
                    case R.id.page_index:
                        doReplaceFragment("fragmentIndex");
                        break;
                    case R.id.page_psychology://好奇心日报
                        doReplaceFragment(ThemType.PAGE_PSYCHOLOGY);
                        break;
                    case R.id.page_movie://电影日报
                        doReplaceFragment(ThemType.PAGE_MOVIE);
                        break;
                    case R.id.page_bored://不许无聊日报
                        doReplaceFragment(ThemType.PAGE_BORED);
                        break;
                    case R.id.page_design:  //设计日报
                        doReplaceFragment(ThemType.PAGE_DESIGN);
                        break;
                    case R.id.page_company://大公司日报
                        doReplaceFragment(ThemType.PAGE_COMPANY);
                        break;
                    case R.id.page_finance://财经日报
                        doReplaceFragment(ThemType.PAGE_FINANCE);
                        break;
                }

                //如果抽屉打开则关闭
                if (mDrawerLayout.isDrawerOpen(navigationMenu)) {
                    mDrawerLayout.closeDrawer(navigationMenu);
                }
                return true;
            }

        });

    }

    private void initHeaderView() {

        headerView = LayoutInflater.from(this).inflate(R.layout.activity_main_menu_header, navigationMenu, false);
        TextView tvCollect = (TextView) headerView.findViewById(R.id.tvFavorite);
        TextView tvDown = (TextView) headerView.findViewById(R.id.tvDown);
        TextView tvName = (TextView) headerView.findViewById(R.id.tvName);
        tvName.setTextColor(Color.WHITE);
        tvCollect.setTextColor(Color.WHITE);
        tvDown.setTextColor(Color.WHITE);
        tvDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("离线下载");
            }
        });
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("收藏");
            }
        });

    }


    private void doReplaceFragment(String tag) {
        if (!tag.equals(nowTag)) {
            if (fragmentMap.get(tag) == null) {
                ThemFragment fragment = new ThemFragment(tag);
                fragmentMap.put(tag, fragment);
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(fragmentMap.get(nowTag))
                        .add(R.id.frameLayout, fragment, tag)
                        .commit();

            } else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .hide(fragmentMap.get(nowTag))
                        .show(fragmentMap.get(tag))
                        .commit();
            }
            nowTag = tag;
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (!mDrawerLayout.isDrawerOpen(navigationMenu)) {
                    mDrawerLayout.openDrawer(navigationMenu);
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (mDrawerLayout.isDrawerOpen(navigationMenu)) {
            mDrawerLayout.closeDrawer(navigationMenu);
        } else {
            if (isBack) {
                super.onBackPressed();
                ActivityController.finishAllActivity();
                return;
            }
            isBack = true;
            showToast("再按一次退出程序");
        }

    }
}
