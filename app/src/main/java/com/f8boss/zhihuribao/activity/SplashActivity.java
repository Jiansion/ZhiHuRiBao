package com.f8boss.zhihuribao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.util.BitmapUtil;
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
import okhttp3.Call;
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
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ButterKnife.bind(this);
        timer = new Timer();
        downLoadMessage();
    }

    private void downLoadMessage() {
        OkHttpUtils.get(Urls.SPLASH_IMAGEURL)
                .cacheMode(CacheMode.NO_CACHE)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, Response response) {
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String imageUrl = jsonObject.getString("img");
                            final String author = jsonObject.getString("text");
                            Picasso.with(mActivity)
                                    .load(imageUrl)
                                    .into(splashImageView, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            tvAuthor.setText(author);
                                            jumpActivity();
                                        }

                                        @Override
                                        public void onError() {
                                            jumpActivity();
                                        }
                                    });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(boolean isFromCache, Call call, Response response, Exception e) {
                        super.onError(isFromCache, call, response, e);
                        jumpActivity();
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
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
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
        BitmapUtil.recycleImageView(splashImageView);
    }
}
