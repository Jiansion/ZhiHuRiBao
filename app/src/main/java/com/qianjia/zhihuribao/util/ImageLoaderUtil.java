package com.qianjia.zhihuribao.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.animation.ViewPropertyAnimation;
import com.bumptech.glide.request.target.Target;
import com.qianjia.zhihuribao.R;
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
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)//下载是占位符
                .error(R.mipmap.ic_launcher)    //下载失败
                .into(imageView);
    }

    /**
     * 指定加载图片的动画，动画为资源文件
     *
     * @param context
     * @param url
     * @param animate     动画资源
     * @param imageView
     * @param placeholder 图片占位符,不需要时使用默认填入0
     */
    public static void loadImage(Context context, String url, int animate, ImageView imageView, @Nullable int placeholder) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .animate(animate)//加载图片动画
                .placeholder(placeholder == 0 ? R.mipmap.ic_launcher : placeholder)//下载是占位符
                .error(R.mipmap.ic_launcher)    //下载失败
                .into(imageView);
    }


    /**
     * 如下，可以自定义动画,加载图片
     * //    ViewPropertyAnimation.Animator animationObject = new ViewPropertyAnimation.Animator() {
     * //        @Override
     * //        public void animate(View view) {
     * //            // if it's a custom view class, cast it here
     * //            // then find subviews and do the animations
     * //            // here, we just use the entire view for the fade animation
     * //            view.setAlpha( 0f );
     * //
     * //            ObjectAnimator fadeAnim = ObjectAnimator.ofFloat( view, "alpha", 0f, 1f );
     * //            fadeAnim.setDuration( 2500 );
     * //            fadeAnim.start();
     * //        }
     * //    };
     *
     * @param context
     * @param url
     * @param animator
     * @param imageView
     * @param placeholder 图片占位符,不需要时使用默认填入0
     */
    public static void loadImage(Context context, String url, ViewPropertyAnimation.Animator animator, ImageView imageView, @Nullable int placeholder) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .animate(animator)//加载图片动画
                .placeholder(placeholder == 0 ? R.mipmap.ic_launcher : placeholder)//下载是占位符
                .error(R.mipmap.ic_launcher)    //下载失败
                .into(imageView);
    }


    /**
     * 设置图片下载优先级
     * 使用 Priority 的枚举类型实现
     *
     * @param context
     * @param url
     * @param priority    用于指定优先级
     * @param imageView
     * @param placeholder 图片占位符,不需要时使用默认填入0
     */
    public static void loadImage(Context context, String url, Priority priority, ImageView imageView, @Nullable int placeholder) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .priority(priority)
                .placeholder(placeholder == 0 ? R.mipmap.ic_launcher : placeholder)//下载是占位符
                .error(R.mipmap.ic_launcher)    //下载失败
                .into(imageView);
    }

    /**
     * 设置磁盘缓存，与内存缓存策略
     * 对于同一URL请求时，内存策略要相一致
     *
     * @param context
     * @param url
     * @param b           True 为跳过内存缓存
     * @param disk        DiskCacheStrategy的枚举类型,设置磁盘缓存策略
     * @param imageView
     * @param placeholder 图片占位符,不需要时使用默认填入0
     */
    public static void loadImageNoCache(Context context, String url, boolean b, DiskCacheStrategy disk, ImageView imageView, @Nullable int placeholder) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .diskCacheStrategy(disk)    //磁盘缓存
                .skipMemoryCache(b)//内存缓存
                .placeholder(placeholder == 0 ? R.mipmap.ic_launcher : placeholder)//下载是占位符
                .error(R.mipmap.ic_launcher)    //下载失败
                .into(imageView);
    }


    /**
     * 加载圆形头像
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
                .placeholder(R.mipmap.ic_launcher)//下载是占位符
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
     * @param placeholder 图片占位符,不需要时使用默认填入0
     */
    public static void loadRoundImage(Context context, String url, @Nullable int dp, ImageView imageView, @Nullable int placeholder) {
        Glide.with(context)
                .load(url)
                .asBitmap()
                .transform(new RoundedTransform(context, dp <= 0 ? 4 : dp))
                .placeholder(placeholder == 0 ? R.mipmap.ic_launcher : placeholder)//下载是占位符
                .error(R.mipmap.ic_launcher)    //下载失败
                .into(imageView);
    }


    /**
     * 需要在子线程执行
     *
     * @param context
     * @param url
     * @return Bitmap
     */
    public static Bitmap loadBitmap(Context context, String url) {
        try {
            return Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL)
                    .get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
