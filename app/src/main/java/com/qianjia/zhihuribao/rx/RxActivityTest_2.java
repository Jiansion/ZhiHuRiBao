package com.qianjia.zhihuribao.rx;


import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.util.LogUtil;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;


/**
 * Created by Jiansion on 2017/4/18.
 * Description:RxJava 线程的简单切换
 * 在 RxJava 中,但我们在主线程中创建一个 Observable 来发送事,
 * 则这个上游默认就在主线程中工作
 * <br/>
 * RxJava 内置了多种线程选项供我们选择
 * {@link Schedulers#io()} 代表 io 操作,通常用于 网络,读写文件等 io 密集型的操作
 * {@link Schedulers#computation()} 代表 CPU 计算密集型的操作,例如需要大量计算的操作
 * {@link Schedulers#newThread()} 代表一个常规的新线程
 * {@link AndroidSchedulers#mainThread()} 代表 Android 的主线程
 */

public class RxActivityTest_2 extends BaseActivity {
    private static final String TAG = RxActivityTest_2.class.getSimpleName();

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
     * Observable 与 Observer
     * 两者默认工作在一个线程中
     */
    private void showThread() {
        Observable<Integer> observable = Observable.create(e -> {
            LogUtil.e(TAG, "Observable thread:" + Thread.currentThread().getName());
            e.onNext(1);
        });

        Consumer<Integer> consumer = integer -> {
            LogUtil.e(TAG, "Observer thread:" + Thread.currentThread().getName());
            LogUtil.e(TAG, "onNext:" + integer);
        };

        observable.subscribe(consumer);
    }

    /**
     * 可以设置更改 Observable 与 Observer 的工作线程
     * 例如需要在子线程中处理耗时的操作
     * 而且需要在 UI 线程处理 UI
     * <br/>
     * 所以需要在 上游发送事件是在子线程中执行
     * 下游处理事件时 在 UI 线程中处理
     * <br/>
     * 多次使用 subscribeOn() 指定上游线程时,只有第一次声明有效,其他的会被忽略
     * 多次使用 observeOn() 指定下游线程是可以的,当每调用一次 observeOn() 时,下游的线程就会切换一次
     * <p>
     * RxJava 切换线程就是那么简单
     */
    private void swichThread() {

        Observable<String> observable = Observable.create(e -> {
            LogUtil.e(TAG, "Observable thread:" + Thread.currentThread().getName());
            e.onNext("发送数据__1");
        });

        Consumer<String> consumer = s -> {
            LogUtil.e(TAG, "Observer thread:" + Thread.currentThread().getName());
            LogUtil.e(TAG, "onNext:" + s);
        };

        observable
                .subscribeOn(Schedulers.newThread())//在新线程中处理上游事件
                .observeOn(AndroidSchedulers.mainThread())// 回调到 UI 线程处理下游业务
                .subscribe(consumer);

    }


}
