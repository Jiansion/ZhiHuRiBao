package com.f8boss.zhihuribao.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.fragment.FragmentIndext;
import com.f8boss.zhihuribao.fragment.ThemFragment;
import com.f8boss.zhihuribao.util.LogUtil;
import com.f8boss.zhihuribao.util.ThemType;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";
    @Bind(R.id.mToolbar)
    Toolbar mToolbar;
    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;
    @Bind(R.id.navigation_menu)
    NavigationView navigationMenu;
    @Bind(R.id.mDrawerLayout)
    DrawerLayout mDrawerLayout;

    private View headerView;
    private TextView tvCollect, tvDown, tvName;

    private FragmentIndext fragmentIndext;

    private ThemFragment psycholgyFragement;

    private ThemFragment movieFragment;

    private ThemFragment boredFragment;

    private ThemFragment designFragment;

    private ThemFragment companyFragment;

    private ThemFragment financeFramgent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LogUtil.e(TAG, "主Activity");
        initBar();
        initHeaderView();
        initMenu();
        initIndext();
    }


    private void initIndext() {
        fragmentIndext = new FragmentIndext();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragmentIndext).commit();
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragmentIndext).commit();
                        break;
                    case R.id.page_psychology:
                        if (psycholgyFragement == null) {
                            psycholgyFragement = new ThemFragment(ThemType.PAGE_PSYCHOLOGY);
                        }
                        getSupportFragmentManager().beginTransaction().addToBackStack("PAGE").replace(R.id.frameLayout, psycholgyFragement).commit();
                        break;
                    case R.id.page_movie://电影日报
                        if (movieFragment == null) {
                            movieFragment = new ThemFragment(ThemType.PAGE_MOVIE);
                        }
                        getSupportFragmentManager().beginTransaction().addToBackStack("PAGE").replace(R.id.frameLayout, movieFragment).commit();
                        break;
                    case R.id.page_bored://不许无聊日报
                        if (boredFragment == null) {
                            boredFragment = new ThemFragment(ThemType.PAGE_BORED);
                        }
                        getSupportFragmentManager().beginTransaction().addToBackStack("PAGE").replace(R.id.frameLayout, boredFragment).commit();
                        break;
                    case R.id.page_design:  //设计日报
                        if (designFragment == null) {
                            designFragment = new ThemFragment(ThemType.PAGE_DESIGN);
                        }
                        getSupportFragmentManager().beginTransaction().addToBackStack("PAGE").replace(R.id.frameLayout, designFragment).commit();
                        break;
                    case R.id.page_company://大公司日报
                        if (companyFragment == null) {
                            companyFragment = new ThemFragment(ThemType.PAGE_COMPANY);
                        }
                        getSupportFragmentManager().beginTransaction().addToBackStack("PAGE").replace(R.id.frameLayout, companyFragment).commit();
                        break;
                    case R.id.page_finance://财经日报
                        if (financeFramgent == null) {
                            financeFramgent = new ThemFragment(ThemType.PAGE_FINANCE);
                        }
                        getSupportFragmentManager().beginTransaction().addToBackStack("PAGE").replace(R.id.frameLayout, financeFramgent).commit();
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
        tvCollect = (TextView) headerView.findViewById(R.id.tvFavorite);
        tvDown = (TextView) headerView.findViewById(R.id.tvDown);
        tvName = (TextView) headerView.findViewById(R.id.tvName);
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


}
