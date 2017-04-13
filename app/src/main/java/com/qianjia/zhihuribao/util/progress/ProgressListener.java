package com.qianjia.zhihuribao.util.progress;

/**
 * Created by Jiansion on 2017/4/13.
 */

public interface ProgressListener {
    void progress(long bytesRead, long contentLength, boolean done);
}
