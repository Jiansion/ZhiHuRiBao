package com.qianjia.zhihuribao.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.util.progress.ProgressLoadListener;
import com.qianjia.zhihuribao.util.progress.ProgressModelLoader;
import com.qianjia.zhihuribao.util.progress.ProgressUIListener;
import com.qianjia.zhihuribao.util.transfrom.BlurTransformation;
import com.qianjia.zhihuribao.util.transfrom.CircleTransform;
import com.qianjia.zhihuribao.util.transfrom.RoundedTransform;


/**
 * Created by jiansion on 2016/4/10.
 * Glide的包装工具类
 */
public class ImageLoaderUtil {

    /**
     * 加载普通图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)//下载是占位符
                .error(R.mipmap.ic_launcher)    //下载失败
                .into(imageView);
    }

    /**
     * 加载圆形图片
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadCirleImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .transform(new CircleTransform(context))
                .placeholder(R.mipmap.ic_launcher)//下载占位符
                .error(R.mipmap.ic_launcher)    //下载失败
                .into(imageView);
    }

    /**
     * 加载圆角图片
     *
     * @param context
     * @param url
     * @param dp
     * @param imageView
     */
    public static void loadRoundImage(Context context, String url, @Nullable int dp, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .transform(new RoundedTransform(context, dp))
                .placeholder(R.mipmap.ic_launcher)//下载占位符
                .error(R.mipmap.ic_launcher)    //下载失败
                .into(imageView);
    }


    /**
     * 加载图片,缩略图为原图的 0.1倍
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadThumbnail(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_launcher)//下载占位符
                .error(R.mipmap.ic_launcher)    //下载失败
                .thumbnail(0.1f)
                .into(imageView);
    }

    /**
     * 加载图片,并附有缩略图
     *
     * @param context
     * @param thumbnailUrl 缩略图 URL
     * @param url          图片 URL
     * @param imageView
     */
    public static void loadThumbnail(Context context, String thumbnailUrl, String url, ImageView imageView) {
        DrawableTypeRequest<String> load = Glide.with(context).load(thumbnailUrl);
        Glide.with(context)
                .load(url)
                .thumbnail(load)
                .into(imageView);
    }

    /**
     * 模糊加载
     *
     * @param context
     * @param url
     * @param imageView
     */
    public static void loadBlur(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transform(new BlurTransformation(context))
//                .thumbnail(0.1f)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher)    //下载失败
                .into(imageView);
    }

    /**
     * 加载图片有进度回调
     *
     * @param context
     * @param url
     * @param imageView
     * @param listener
     */
    public static void loadWitheProgress(Context context, String url, ImageView imageView, ProgressLoadListener listener) {
        Glide.with(imageView.getContext())
                .using(new ProgressModelLoader((bytesRead, contentLength) -> imageView.post(() -> listener.update(bytesRead, contentLength))))
                .load(url)
                .asBitmap()
                .skipMemoryCache(true)//跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.NONE)//磁盘缓存策略,不缓存任何数据
                .dontAnimate()
                .listener(new RequestListener<Object, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        listener.onException();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        listener.onResourceReady();
                        return false;
                    }
                }).into(imageView);

    }


}
