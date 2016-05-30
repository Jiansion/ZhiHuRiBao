package com.f8boss.zhihuribao;

import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

/**
 * Created by jiansion on 2016/5/25.
 */
public class MyApplication extends Application {

    //全局上下文
    private static MyApplication applicationContext;

    //通过 getInstance()得到全局上下文
    public static MyApplication getInstance() {
        return applicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = this;
        initImageLoader(getInstance());
    }

    //初始化ImageLoader
    public static void initImageLoader(Context context) {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                .denyCacheImageMultipleSizesInMemory()//禁用多尺寸缓存
                .threadPoolSize(5) // 线程池内加载的数量
                .threadPriority(Thread.NORM_PRIORITY - 2)//线程优先级，1~10优先级逐渐升高，10代表图片立即显示
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())// 保存的时候的URI名称用MD5加密
                .tasksProcessingOrder(QueueProcessingType.LIFO)//任务处理顺序，不常用，可以不配
                .memoryCacheSize(2 * 1024 * 1024)//开辟2M内存缓存空间
                .diskCacheSize(50 * 1024 * 1024)//开辟50M磁盘缓存空间
                .writeDebugLogs()//打印日志
                .build();
        //初始化主键
        ImageLoader.getInstance().init(config);
    }
}
