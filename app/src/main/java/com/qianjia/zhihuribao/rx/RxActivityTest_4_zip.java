package com.qianjia.zhihuribao.rx;

import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.util.LogUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jiansion on 2017/4/19.
 * RxJava 的 zip() 运算符
 * zip() 通过一个函数将多个 Observable 发送的事件结合在一起
 * 然后发送这些组合的时间到下游,它严格按照顺序组合事件.
 * 并且他只发送最少的那个 Observable 一样多的数据
 * 通俗解释就是有两条支流,通过 zip 合并后留到下游,
 * 但其中一条支流 没有发送数据后 不再合并数据
 */

public class RxActivityTest_4_zip extends BaseActivity {
    private static final String TAG = RxActivityTest_4_zip.class.getSimpleName();

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
     * RxJava zip() 操作符的简单应用
     * 将两个数据发送发送方的上游 进行拼装
     * 然后发送到下游
     * 但只发送上游中最少数据一组那么多的拼装数据,
     * 注意的是,由于没有切换线程,所以两条上游处于同一线程
     * so:但第一条发完后才会发送第二条上游的数据
     */
    private void zipMethob() {
        Observable<Integer> observable_1 = Observable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
            e.onNext(4);
            e.onComplete();
        });
        //将第一条上游切换为 在 io 线程中工作
        observable_1.subscribeOn(Schedulers.io());

        Observable<String> observable_2 = Observable.create(e -> {
            e.onNext("第一");
            e.onNext("第二");
            e.onNext("第三");
            e.onComplete();
        });

        //第二条上游改为在 新建的一条子线程中工作
        observable_2.subscribeOn(Schedulers.newThread());

        Observable.zip(observable_1, observable_2, (integer, s) -> integer + s)
                .observeOn(AndroidSchedulers.mainThread())//把下游切换回 UI 线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        LogUtil.e(TAG, "onSubscribe");
                    }

                    @Override
                    public void onNext(String value) {
                        LogUtil.e(TAG, "onNext:" + value);

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.e(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        LogUtil.e(TAG, "onComplete");
                    }
                });


    }
}
