package com.qianjia.zhihuribao.ui.activity;

import android.view.View;

import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.util.ImageLoaderUtil;
import com.qianjia.zhihuribao.util.LogUtil;
import com.qianjia.zhihuribao.util.progress.ProgressLoadListener;
import com.qianjia.zhihuribao.widget.ProgressImageView;

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
        String url = "https://farm4.staticflickr.com/3854/32764887833_0a6192b336_z.jpg";
        ImageLoaderUtil.loadWitheProgress(mActivity, url, proImage.getImageView(), new ProgressLoadListener() {
            @Override
            public void update(long bytesRead, long contentLength) {
                LogUtil.e("ImageActvity", "bytesRead:" + bytesRead);
                LogUtil.e("ImageActvity", "contentLength:" + contentLength);
//                proImage.setProgress(contentLength);
                int progress = (int) ((bytesRead * 100) / contentLength);
                LogUtil.e("ImageActvity", "progress:" + progress);
                proImage.setProgress(progress);

            }

            @Override
            public void onException() {

            }

            @Override
            public void onResourceReady() {
                LogUtil.e("ImageActvity", "onResourceReady");
            }
        });

    }

    @Override
    protected void initData() {
    }


}
