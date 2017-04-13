package com.qianjia.zhihuribao.util.progress;

import android.os.Handler;
import android.os.Message;

import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Jiansion on 2017/4/13.
 */

public class ProgressDataFetcher implements DataFetcher<InputStream> {

    private String url;
    private Handler handler;
    private Call progressCall;
    private InputStream stream;
    private boolean isCancelled;

    public ProgressDataFetcher(String url, Handler handler) {
        this.url = url;
        this.handler = handler;

    }

    @Override
    public InputStream loadData(Priority priority) throws Exception {
        Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();
        client.interceptors().add(new ProgressInterceptor(getProgressListener()));

        progressCall = client.newCall(request);
        Response response = progressCall.execute();
        if (isCancelled) {
            return null;
        }
        if (!response.isSuccessful()) throw new IOException("Unexpected code" + response);
        stream = response.body().byteStream();
        return stream;
    }

    private ProgressListener getProgressListener() {
        ProgressListener progressListener = (bytesRead, contentLength, done) -> {
            if (handler != null && !done) {
                Message message = handler.obtainMessage();
                message.what = 1;
                message.arg1 = (int) bytesRead;
                message.arg2 = (int) contentLength;
            }
        };
        return progressListener;
    }


    @Override
    public void cleanup() {
        if (stream != null) {
            try {
                stream.close();
                stream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (progressCall != null) {
            progressCall.cancel();
        }
    }

    @Override
    public String getId() {
        return url;
    }

    @Override
    public void cancel() {
        isCancelled = true;
    }
}
