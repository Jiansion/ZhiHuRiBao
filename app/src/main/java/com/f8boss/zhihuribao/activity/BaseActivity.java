package com.f8boss.zhihuribao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

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
        mActivity = this;
        ActivityController.addActivity(this);
        bind = ButterKnife.bind(this);

        initData();
        initView();
    }

    public void showToast(String msg) {
        Toast.makeText(mActivity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityController.removeActivity(this);
        bind.unbind();
        OkHttpUtils.getInstance().cancelTag(this);

    }
}
