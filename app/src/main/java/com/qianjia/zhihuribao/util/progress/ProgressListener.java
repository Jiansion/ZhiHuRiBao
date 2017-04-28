package com.qianjia.zhihuribao.util.progress;

/**
 * Created by Jiansion on 2017/4/14.
 * 通知下载进度
 */

public interface ProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}
