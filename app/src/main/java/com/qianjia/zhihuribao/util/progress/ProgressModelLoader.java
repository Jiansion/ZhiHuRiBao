package com.qianjia.zhihuribao.util.progress;

import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.stream.StreamModelLoader;

import java.io.InputStream;

/**
 * Created by Jiansion on 2017/4/13.
 */

public class ProgressModelLoader implements StreamModelLoader<String> {

    private final ModelCache<String, String> modelCache;
    private ProgressUIListener proListener;

    public ProgressModelLoader(ProgressUIListener listener) {
        this(null, listener);
    }

    public ProgressModelLoader(ModelCache<String, String> modelCache) {
        this(modelCache, null);
    }


    public ProgressModelLoader(ModelCache<String, String> modelCache, ProgressUIListener listener) {
        this.modelCache = modelCache;
        this.proListener = listener;
    }


    @Override
    public DataFetcher<InputStream> getResourceFetcher(String model, int width, int height) {
        String result = null;
        if (modelCache != null) {
            result = modelCache.get(model, width, height);
        }
        if (result == null) {
            result = model;
            if (modelCache != null) {
                modelCache.put(model, width, height, result);
            }
        }
        return new ProgressDataFetcher(result, proListener);
    }

}
