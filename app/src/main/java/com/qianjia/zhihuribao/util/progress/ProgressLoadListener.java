package com.qianjia.zhihuribao.util.progress;

/**
 * Created by Jiansion on 2017/4/14.
 * 通知图片加载进度
 */

public interface ProgressLoadListener {
    /**
     * @param bytesRead     图片下载进度的字节长度
     * @param contentLength 图片大小的字节总长度
     */
    void update(long bytesRead, long contentLength);

    void onException();

    void onResourceReady();
}
