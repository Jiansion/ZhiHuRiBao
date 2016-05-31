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
import android.widget.Toast;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.fragment.FragmentIndext;
import com.f8boss.zhihuribao.fragment.FragmentPsychology;
import com.f8boss.zhihuribao.util.LogUtil;

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

    private FragmentPsychology fragementPsycholgy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        LogUtil.e("MainActivity", "主Activity");
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
                        if (fragementPsycholgy == null) {
                            fragementPsycholgy = new FragmentPsychology();
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragementPsycholgy).commit();
//                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, fragementPsycholgy);

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
                Toast.makeText(MainActivity.this, "离线下载", Toast.LENGTH_SHORT).show();
            }
        });
        tvCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "收藏的", Toast.LENGTH_SHORT).show();
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
