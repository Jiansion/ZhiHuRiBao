package com.qianjia.zhihuribao.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.qianjia.zhihuribao.R;

/**
 * Created by Jiansion on 2017/4/13.
 */

public class ProgressImageView extends RelativeLayout {

    private ImageView mImageView;

    private CircleProgressBar circleProgressBar;

    public ProgressImageView(Context context) {
        this(context, null);
    }

    public ProgressImageView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ProgressImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View rootView = LayoutInflater.from(context).inflate(R.layout.progress_imageview, this, true);
        mImageView = (ImageView) rootView.findViewById(R.id.imageView);
        circleProgressBar = (CircleProgressBar) rootView.findViewById(R.id.progressBar);
        circleProgressBar.setListener(progress -> {
            if (progress == 100) {
                circleProgressBar.setVisibility(GONE);
            }
        });
    }

    /**
     * 设置进度
     *
     * @param progressBar
     */
    public void setProgress(int progressBar) {
        circleProgressBar.setProgress(progressBar);
    }

    public ImageView getImageView() {
        return mImageView;
    }

    /**
     * 设置字体颜色
     *
     * @param color
     */
    public void setProgressTextColor(@ColorRes int color) {
        circleProgressBar.setNumberColor(color);
    }
}
