package com.f8boss.zhihuribao.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.widget.Toast;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Jiansion on 2016/3/21.
 * 联网操作工具类
 */
public class OkHttpUtils {

    static ExecutorService executorService;//线程池
    static OkHttpClient okHttpClient;
    static Handler handler;


    public static final int TYPE_TEXT = 100;//下载类型为 文本类型
    public static final int TYPE_BYTE = 102;//下载类型为 数组类型

    //静态代码块
    //初始化
    static {
        executorService = Executors.newFixedThreadPool(5);//开启线程池
        okHttpClient = new OkHttpClient();//okHttpClient 不仅可以返回String类型， 还可以返回byte类型 以及以流的形式返回
        handler = new Handler();
    }

    //回调接口
    //谁调用数据就返回给谁
    public interface OnCallBack {
        void callBackUIString(String data);//返回字符

        void callBackUIByte(byte[] datas);//返回数组(一般用来转换成bitmap)


    }

    /**
     * GET请求联网下载数据 ,根据 Type 选择是否下载 String类型数据，还是Byte[]数据
     *
     * @param context
     * @param url        请求地址
     * @param type_id    请求的数据类型 TYPE_TEXT:String  TYPE_BYTE:byte[]
     * @param returndata 返回得到的数据
     */
    public static void onGetToDownLoadData(final Context context, final String url, final int type_id, final OnCallBack
            returndata) {

        //判断网络状态
        ConnectivityManager mConnectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 检查网络连接，如果无网络可用，就不需要进行连网操作等
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info == null ||
                !mConnectivity.getBackgroundDataSetting()) {
            Toast.makeText(context, "当前没有网络", Toast.LENGTH_SHORT).show();
        } else {
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        final String data;
                        final byte[] datas;
                        Request request = new Request.Builder()
                                .url(url).tag(url).build();
                        Response resp = okHttpClient.newCall(request).execute();
                        if (resp.isSuccessful()) {
                            if (type_id == OkHttpUtils.TYPE_TEXT) {
                                data = resp.body().string();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        returndata.callBackUIString(data);
                                    }
                                });
                            } else if (type_id == OkHttpUtils.TYPE_BYTE) {
                                datas = resp.body().bytes();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        returndata.callBackUIByte(datas);
                                    }
                                });
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "发生联网异常", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }


    /**
     * POST请求联网下载数据 ,根据 Type 选择是否下载 String类型数据，还是Byte[]数据
     *
     * @param context
     * @param url
     * @param type_id
     * @param key
     * @param volue
     * @param returndata
     */
    public static void onPostToDownLoadData(Context context, final String url, final int type_id, final String key, final String volue, final OnCallBack
            returndata) {

        //判断网络状态
        ConnectivityManager mConnectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        // 检查网络连接，如果无网络可用，就不需要进行连网操作等
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info == null ||
                !mConnectivity.getBackgroundDataSetting()) {
            Toast.makeText(context, "当前没有网络", Toast.LENGTH_SHORT).show();
        } else {

            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    RequestBody requestBody = new FormEncodingBuilder().add(key, volue).build();
                    Request request = new Request.Builder().url(url).post(requestBody).build();
                    try {
                        final String data;
                        final byte[] datas;
                        Response response = okHttpClient.newCall(request).execute();
                        if (response.isSuccessful()) {
                            if (type_id == OkHttpUtils.TYPE_TEXT) {
                                data = response.body().string();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        returndata.callBackUIString(data);
                                    }
                                });
                            } else if (type_id == OkHttpUtils.TYPE_BYTE) {
                                datas = response.body().bytes();
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        returndata.callBackUIByte(datas);
                                    }
                                });
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }


    /**
     * @param url    拼接地址
     * @param format 表示占位符
     * @param page   表示 替换的内容
     * @return
     */
    public static String getReplaceFormat(String url, String format, String page) {
        String urls = url.replace(format, page);
        return urls;
    }


}
