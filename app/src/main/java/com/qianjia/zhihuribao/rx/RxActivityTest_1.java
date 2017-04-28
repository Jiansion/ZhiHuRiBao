package com.qianjia.zhihuribao.rx;

import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.util.LogUtil;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * Created by Jiansion on 2017/4/18.
 * <p>
 * #Description:
 * 简单的来说可以把 RxJava 看作为一条水管,
 * Observable 和 Observer 分别作为水管的 上游 和 下游
 * 将他们之间的绑定使用的方法为 subscribe();
 */

public class RxActivityTest_1 extends BaseActivity {
    private static final String TAG = RxActivityTest_1.class.getSimpleName();

    @Override
    protected int initLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        //创建建一个上游 Observable

        /*****
         * ObservableEmitter 发射器的意思,可以用于发送事件到下游
         * ObservableEmitter 可以发送 onNext(),onComplete(),onError()
         * 发送规则:
         * 但发送 onComplete()/onError 后上游可以继续发送事件,而下游接收到 onComplete()/onError()
         * 后不再继续接收是上游发送的事件
         * </p>
         * 上游可以不发送 onComplete()/onError()
         * </p>
         * onError() 与 onComplete() 相互互斥,当调用其中以后再调用另外一个方法
         * 程序可能会崩溃
         *
         ******/
        Observable<String> observable = Observable.create((ObservableEmitter<String> e) -> {
            //事件的监听,上游在此发送事件
            e.onNext("发送第一条");
            e.onNext("发送第二条");
            e.onNext("发送第三条");

            //时间结束
            e.onComplete();
        });


        //创建下游 Observer
        Observer<String> observer = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                LogUtil.e(TAG, "onSubscribe");
                //上下游断开来连接回调
            }

            @Override
            public void onNext(String value) {
                LogUtil.e(TAG, value);
                //处理上游发送的事件
            }

            @Override
            public void onError(Throwable e) {
                LogUtil.e(TAG, "onError");
                //处理上游发送的错误事件

            }

            @Override
            public void onComplete() {
                LogUtil.e(TAG, "onComplete");
                //上游通知发送结束

            }
        };

        //建立连接
        observable.subscribe(observer);

    }
}
