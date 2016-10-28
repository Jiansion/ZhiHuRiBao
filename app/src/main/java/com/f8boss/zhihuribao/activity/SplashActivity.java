package com.f8boss.zhihuribao.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.util.BitmapUtil;
import com.f8boss.zhihuribao.util.Urls;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lucy on 2016/8/9.
 * 闪屏广告页
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.splashImageView)
    ImageView splashImageView;
    @BindView(R.id.tvAuthor)
    TextView tvAuthor;
    @BindView(R.id.linearBottom)
    LinearLayout linearBottom;
    private Timer timer;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
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
    }

    @Override
    protected void initData() {
        timer = new Timer();
        downLoadMessage();
    }

    private void downLoadMessage() {
        OkHttpUtils.get(Urls.SPLASH_IMAGEURL)
                .tag(this)
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, Response response) {
                        linearBottom.setVisibility(View.VISIBLE);
                        try {
                            JSONObject jsonObject = new JSONObject(s);
                            String imageUrl = jsonObject.getString("img");
                            final String author = jsonObject.getString("text");
                            Picasso.with(mActivity).load(imageUrl).error(R.mipmap.splash_image).into(splashImageView);
                            tvAuthor.setText(author);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onAfter(boolean isFromCache, @Nullable String s, Call call, @Nullable Response response, @Nullable Exception e) {
                        super.onAfter(isFromCache, s, call, response, e);
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
        if (timer != null) {
            timer.cancel();
        }
        BitmapUtil.recycleImageView(splashImageView);
    }
}
