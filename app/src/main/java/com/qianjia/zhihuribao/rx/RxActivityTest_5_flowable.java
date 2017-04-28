package com.qianjia.zhihuribao.rx;

import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.util.LogUtil;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

/**
 * Created by Jiansion on 2017/4/19.
 * RxJava 的 flowable 的使用
 * Flowable 和 Subsciber 与 Observable 和 Subcriber 对应
 */

public class RxActivityTest_5_flowable extends BaseActivity {
    private static final String TAG = RxActivityTest_5_flowable.class.getSimpleName();

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

    private void flowableMethob() {
        Flowable<Integer> flowable = Flowable.create(e -> {
            e.onNext(1);
            e.onNext(2);
            e.onNext(3);
            e.onComplete();

        }, BackpressureStrategy.ERROR);
        //增加按一个参数,这个参数在上下游流速不均衡时会使用到


        Subscriber<Integer> subscriber = new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                LogUtil.e(TAG, "onSubscribe");

                //注意
                s.request(Long.MAX_VALUE);
            }

            @Override
            public void onNext(Integer integer) {
                LogUtil.e(TAG, "onNext:" + integer);
            }

            @Override
            public void onError(Throwable t) {
                LogUtil.e(TAG, "onError");
            }

            @Override
            public void onComplete() {
                LogUtil.e(TAG, "onComplete");
            }
        };

        // 绑定上下游
        flowable.subscribe(subscriber);

    }
}
