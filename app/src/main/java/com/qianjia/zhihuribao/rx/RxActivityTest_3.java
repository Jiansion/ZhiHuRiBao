package com.qianjia.zhihuribao.rx;

import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.util.LogUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;

/**
 * Created by Jiansion on 2017/4/18.
 * Description:RxJava 的操作符
 * 1. map ,最简单的变换操作符,
 * 它的作用就是对上游发送的每一个事件应用到一个函数中,
 * 使得每一个事件都按照指定的函数去变化
 * <br/>
 * 2. FlatMap ,RxJava 强大的操作符
 * FlatMap 就是将发送事件的 Observable 变换为多个发送事件的 Observables,
 * 然后将发射的事件合并放入到一个单独的 Observable 中
 * 例如 flatMap 需要处理的事件是将上游的发送的 圆 转为 矩形和三角形发送
 * 如果需要顺序 则使用 concatMap()
 * 需要注意 flatMap(), 不保证事件的顺序
 */

public class RxActivityTest_3 extends BaseActivity {
    private static final String TAG = RxActivityTest_3.class.getSimpleName();

    @Override
    protected int initLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    /**
     * RxJava 基本操作符 map
     * 简单来说 map 相当于一个拦截器
     * 先将上游发送的事件拦截
     * 然后进行加工后再发送到 下游
     */
    private void mapMobth() {
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
        }).map(integer -> "将数字转为字符串:" + integer)//使用 map 操作符进行转换
                .subscribe(s -> LogUtil.e(TAG, s));//最后得到的是字符串

    }

    /**
     * 上游发送 3 个事件
     * 先用 FlatMap() 将上游的发来的事件转换为发送三个事件的水管,
     * 并且为了看出效果加上 1s 的延迟
     */
    private void faltMapMobth() {
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
        }).flatMap(integer -> {
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                list.add("上游发送的事件" + integer);
            }
            return Observable.fromIterable(list).delay(1, TimeUnit.SECONDS);
        }).subscribe(s -> LogUtil.e(TAG, s));
    }

}
