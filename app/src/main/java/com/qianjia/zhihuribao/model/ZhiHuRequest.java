package com.qianjia.zhihuribao.model;

import com.qianjia.zhihuribao.api.ZhiHuApi;
import com.qianjia.zhihuribao.bean.Detail;
import com.qianjia.zhihuribao.bean.IndexList;
import com.qianjia.zhihuribao.bean.Theme;
import com.qianjia.zhihuribao.bean.ThemesCount;
import com.qianjia.zhihuribao.http.RetrofitHelper;
import com.qianjia.zhihuribao.util.LogUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jiansion on 2017/3/14.
 * 网络请求操作类
 */

public class ZhiHuRequest {


    private static final String TAG = ZhiHuRequest.class.getSimpleName();

    /**
     * 请求网络获取第一屏数据
     *
     * @param listener
     */
    public static void getZhiHuData(RequestListener<IndexList> listener) {
        ZhiHuApi api = RetrofitHelper.createZhiHuApi(ZhiHuApi.class);
        Observable<IndexList> observable = api.getIndexData();
        observable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(indexList -> {
                            LogUtil.e(TAG, indexList.getDate());
                            listener.onRequestSuccess(indexList);
                        },
                        throwable -> listener.onRequestFail("网络异常~~~"));

    }


    /**
     * 获取首页过往内容
     *
     * @param date
     * @param listener
     */
    public static void getZhiHuData(String date, RequestListener<IndexList> listener) {
        ZhiHuApi zhiHuApi = RetrofitHelper.createZhiHuApi(ZhiHuApi.class);
        Observable<IndexList> indexMoreData = zhiHuApi.getIndexMoreData(date);
        indexMoreData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onRequestSuccess, throwable -> listener.onRequestFail("请求失败"));

    }

    /**
     * 获取详情页的数据
     * type:0 or type:1
     *
     * @param id
     * @param listener
     */
    public static void getZhiHuDetail(int id, RequestListener<Detail> listener) {
        ZhiHuApi api = RetrofitHelper.createZhiHuApi(ZhiHuApi.class);
        Observable<Detail> detailData = api.getDetailData(id);
        detailData.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onRequestSuccess, throwable -> listener.onRequestFail("请求失败"));
    }


    /**
     * 获取全部栏目主题
     *
     * @param listener
     */
    public static void getThemesList(RequestListener<Theme> listener) {
        ZhiHuApi api = RetrofitHelper.createZhiHuApi(ZhiHuApi.class);
        Observable<Theme> themes = api.getThemes();
        themes.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onRequestSuccess, throwable -> listener.onRequestFail("请求失败"));
    }


    /**
     * 获取栏目的内容
     *
     * @param id       栏目id
     * @param listener
     */
    public static void getThemesContent(int id, RequestListener<ThemesCount> listener) {
        ZhiHuApi api = RetrofitHelper.createZhiHuApi(ZhiHuApi.class);
        Observable<ThemesCount> themesCount = api.getThemesCount(id);
        themesCount.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onRequestSuccess, throwable -> listener.onRequestFail("请求失败"));
    }

    /**
     * 加载其他栏目的过往内容
     *
     * @param id         栏目ID
     * @param lastItemId 显示的最后一项内容的 ID
     * @param listener
     */
    public static void getThemesCountent(int id, int lastItemId, RequestListener<ThemesCount> listener) {
        ZhiHuApi api = RetrofitHelper.createZhiHuApi(ZhiHuApi.class);
        Observable<ThemesCount> themesCount = api.getThemesCount(id, lastItemId);
        themesCount.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(listener::onRequestSuccess, throwable -> listener.onRequestFail("请求失败"));

    }

}
