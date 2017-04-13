package com.qianjia.zhihuribao.util.progress;

import android.os.Handler;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

/**
 * Created by Jiansion on 2017/4/13.
 */

public class ProgressModelLoder implements StreamModelLoader<String> {

    private Handler handler;

    public ProgressModelLoder(Handler handler) {
        this.handler = handler;
    }

    @Override
    public DataFetcher<InputStream> getResourceFetcher(String model, int width, int height) {
        return new ProgressDataFetcher(model, handler);
    }
}
