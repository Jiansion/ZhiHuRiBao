package com.f8boss.zhihuribao.util;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.f8boss.zhihuribao.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

/**
 * Created by jiansion on 2016/5/25.
 */
public class ImageLoaderUtil {

    private static ImageLoader imagetLoader = null;
    private static DisplayImageOptions options = null;

    static {
        imagetLoader = ImageLoader.getInstance();
        options = new DisplayImageOptions.Builder()//创建构造器
                .showImageOnLoading(R.mipmap.default_pic_content_image_loading_light)//加载过程中的图片资源
                .showImageForEmptyUri(R.mipmap.account_avatar)//加载为空时的图片资源
                .imageScaleType(ImageScaleType.NONE)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .showImageOnFail(R.mipmap.weibosdk_empty_failed)//加载失败时的图片资源
                .cacheInMemory(true)//是否进行内存缓存
                .cacheOnDisk(true)//是否进行磁盘缓存
                .build();//构造生成DisplayImageOptions
    }


    /***
     * 使用displayImage方法加载图片
     *
     * @param url
     * @param imageView
     */
    public static void displayImage(String url, ImageView imageView) {
        imagetLoader.displayImage(url, imageView, options);
    }


    /***
     * 使用loadImage加载图片
     *
     * @param url
     * @param imageView
     */
    public static void loadImage(String url, final ImageView imageView) {
        imagetLoader.loadImage(url, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {
            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });
    }


}
