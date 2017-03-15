package com.qianjia.zhihuribao.base;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Jiansion on 2017/2/17.
 * 基类Activity
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected BaseActivity mActivity;

    private Unbinder bind;

    private PermissionListener listener;

    //权限请求码
    private static final int PERMISSION_REQUEST_CODE = 123;

    //请求成功结果码
    public static final int PERMISSION_REQUEST_SUCCESS = 101;

    //请求失败结果码
    public static final int PERMISSION_REQUEST_ERROR = 103;

    /**
     * 初始化ID
     *
     * @return 布局ID
     */
    protected abstract int initLayoutId();

    /**
     * 初始化布局
     */
    protected abstract void initView();

    /**
     * 初始化数据
     */
    protected abstract void initData();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLayoutId());
        mActivity = this;
        bind = ButterKnife.bind(this);
        initView();
        initData();

    }


    /**
     * 动态请求权限
     *
     * @param permissions 权限数组
     * @param listener    事件回调
     */
    public final void requestPermission(String[] permissions, PermissionListener listener) {
        this.listener = listener;
        if (Build.VERSION.SDK_INT >= 23) {//大于6.0版本动态请求权限
            List<String> list = new ArrayList<>();
            for (String permission : permissions) {
                //检查授权
                if (PermissionChecker.checkSelfPermission(mActivity, permission) != PackageManager.PERMISSION_GRANTED) {
                    list.add(permission);
                }
            }
            //所有权限都已获取
            if (list.isEmpty()) listener.permissionResult(PERMISSION_REQUEST_SUCCESS);
            else    //请求权限
                ActivityCompat.requestPermissions(this, list.toArray(new String[list.size()]), PERMISSION_REQUEST_CODE);

        } else {//小于6.0直接授权
            listener.permissionResult(PERMISSION_REQUEST_SUCCESS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                List<String> list = new ArrayList<>();
                for (int i = 0; i < grantResults.length; i++) {
                    int grantResult = grantResults[i];
                    String permission = permissions[i];
                    if (grantResult == PackageManager.PERMISSION_DENIED) {
                        list.add(permission);
                    }
                }
                if (list.isEmpty()) {
                    listener.permissionResult(PERMISSION_REQUEST_SUCCESS);
                } else {
                    listener.permissionResult(PERMISSION_REQUEST_ERROR);
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bind.unbind();
    }
}
