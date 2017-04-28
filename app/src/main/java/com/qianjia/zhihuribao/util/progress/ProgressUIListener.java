package com.qianjia.zhihuribao.util.progress;

/**
 * Created by Jiansion on 2017/4/13.
 * 通知 UI 进度
 */

public interface ProgressUIListener {
    void update(long bytesRead, long contentLength);
}
