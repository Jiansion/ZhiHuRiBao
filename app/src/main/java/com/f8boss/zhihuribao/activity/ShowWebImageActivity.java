package com.f8boss.zhihuribao.activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.util.PicassoUtil;

import butterknife.BindView;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by jiansion on 2016/10/26.
 */

public class ShowWebImageActivity extends BaseActivity {

    private static final String TAG = ShowWebImageActivity.class.getSimpleName();
    @BindView(R.id.mImageView)
    PhotoView imageView;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_showimage;
    }

    @Override
    protected void initView() {
        transparentBar();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("imageUrl");
        Log.e(TAG, imageUrl);
//        Picasso.with(this).load(imageUrl).into(imageView);
        PicassoUtil.downLoadImage(mActivity, imageUrl, "tag", imageView);
        PhotoViewAttacher attacher = new PhotoViewAttacher(imageView);
        attacher.update();
    }

    public static void startShowImage(Context context, String imageUrl) {
        Intent intent = new Intent(context, ShowWebImageActivity.class);
        intent.putExtra("imageUrl", imageUrl);
        context.startActivity(intent);

    }
}
