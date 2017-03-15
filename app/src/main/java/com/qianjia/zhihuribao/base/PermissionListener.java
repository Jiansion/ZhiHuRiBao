package com.qianjia.zhihuribao.base;

/**
 * Created by Jiansion on 2017/2/17.
 * 请求用户权限毁掉接口
 */
public interface PermissionListener {

    /**
     * 动态请求权限回调方法
     * <p>
     * return by{@link BaseActivity#PERMISSION_REQUEST_SUCCESS}表示授权请求成功
     * or return {@link BaseActivity#PERMISSION_REQUEST_ERROR}表示授权失败,没有获得相应的权限
     *
     * @param resultCode
     */
    void permissionResult(int resultCode);
}
