package com.qianjia.zhihuribao.ui.activity;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.qianjia.basemodel.util.ToastUtil;
import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.util.progress.ProgressModelLoder;
import com.qianjia.zhihuribao.widget.ProgressImageView;

import java.lang.ref.WeakReference;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by Jiansion on 2017/4/13.
 */

public class ImageActvity extends BaseActivity {
    @BindView(R.id.proImage)
    ProgressImageView proImage;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_image;
    }

    @Override
    protected void initView() {

    }

    @OnClick(R.id.btnStart)
    public void onViewClick(View v) {
        ToastUtil.showToast(mActivity, "下载中..");
        Glide.with(mActivity)
                .using(new ProgressModelLoder(new ProgressHandler(mActivity, proImage)))
                .load("https://farm4.staticflickr.com/3854/32764887833_0a6192b336_z.jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(proImage.getImageView());

//        Glide.with(mActivity)
//                .load("https://farm4.staticflickr.com/3854/32764887833_0a6192b336_z.jpg")
    }

    @Override
    protected void initData() {

    }

    private static class ProgressHandler extends Handler {

        private final WeakReference<Activity> mActivity;
        private final ProgressImageView mProgressImageView;

        ProgressHandler(Activity activity, ProgressImageView progressImageView) {
            super(Looper.getMainLooper());
            mActivity = new WeakReference<>(activity);
            mProgressImageView = progressImageView;
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final Activity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        int percent = msg.arg1 * 100 / msg.arg2;
                        mProgressImageView.setProgressBar(percent);
                        break;
                    default:
                        break;
                }
            }
        }
    }

}
