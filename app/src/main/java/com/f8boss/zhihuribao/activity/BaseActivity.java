package com.f8boss.zhihuribao.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.lzy.okhttputils.OkHttpUtils;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jiansion on 2016/5/6.
 * 基类Activity
 */
public abstract class BaseActivity extends AppCompatActivity {

    public BaseActivity mActivity;

    protected Unbinder bind;


    protected abstract int initLayoutId();

    protected abstract void initView();

    protected abstract void initData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());
        bind = ButterKnife.bind(this);
        mActivity = this;
        ActivityController.addActivity(this);

        initData();
        initView();
    }

    //设置透明的状态栏
    protected void transparentBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
        bind.unbind();
        OkHttpUtils.getInstance().cancelTag(this);

    }
}
