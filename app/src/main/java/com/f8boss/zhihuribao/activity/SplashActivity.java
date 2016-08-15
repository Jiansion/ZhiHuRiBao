package com.f8boss.zhihuribao.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.f8boss.zhihuribao.MyApplication;
import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.util.Urls;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lucy on 2016/8/9.
 * 闪屏广告页
 */
public class SplashActivity extends BaseActivity {

    @Bind(R.id.splashImageView)
    ImageView splashImageView;
    @Bind(R.id.tvAuthor)
    TextView tvAuthor;
    private Timer timer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ButterKnife.bind(this);
        timer = new Timer();
        downLoadMessage();
    }

    private void downLoadMessage() {
        OkHttpUtils.get(Urls.SPLASH_IMAGEURL)
                .cacheKey("SPLASH_IMAGEURL")
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String imageUrl = jsonObject.getString("img");
                            final String author = jsonObject.getString("text");

                            Picasso.with(MyApplication.getInstance())
                                    .load(imageUrl)
                                    .into(splashImageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            splashImageView.setVisibility(View.VISIBLE);
                                            tvAuthor.setText(author);
                                            jumpActivity();
                                        }

                                        @Override
                                        public void onError() {
                                            splashImageView.setVisibility(View.VISIBLE);
                                            splashImageView.setImageResource(R.mipmap.ic_launcher);
                                            jumpActivity();
                                        }
                                    });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    private void jumpActivity() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }
}
