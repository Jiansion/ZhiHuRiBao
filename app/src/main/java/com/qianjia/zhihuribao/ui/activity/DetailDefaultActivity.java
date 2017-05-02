package com.qianjia.zhihuribao.ui.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.bean.Detail;
import com.qianjia.zhihuribao.presenter.DetailPresenter;
import com.qianjia.zhihuribao.ui.view.BaseView;
import com.qianjia.zhihuribao.util.ConvertHtml;
import com.qianjia.zhihuribao.util.ImageLoaderUtil;
import com.qianjia.zhihuribao.util.ToastUtil;

import butterknife.BindView;

/**
 * Created by Jiansion on 2017/3/15.
 * 知乎详情页
 */

public class DetailDefaultActivity extends BaseActivity implements BaseView<Detail> {
    private static final String DETAIL_ID = "detail_id";
    @BindView(R.id.imPoster)
    ImageView imPoster;

    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

    @BindView(R.id.mToolbarLayout)
    CollapsingToolbarLayout mToolbarLayout;

    @BindView(R.id.mAppBar)
    AppBarLayout mAppBar;

    @BindView(R.id.mWebView)
    WebView mWebView;

    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;


    public static void onToDetailPage(Context context, int id, View view) {
        Intent intent = new Intent(context, DetailDefaultActivity.class);
        intent.putExtra(DETAIL_ID, id);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation((Activity) context, view, "poster").toBundle());
        } else {
            context.startActivity(intent);
        }
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_detail_default;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {


        mWebView.setScrollbarFadingEnabled(true);
        //能够和js交互
        mWebView.getSettings().setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        mWebView.getSettings().setBuiltInZoomControls(false);
        //缓存
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //开启DOM storage API功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        mWebView.getSettings().setAppCacheEnabled(false);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (mProgressBar != null) {
                    mProgressBar.setProgress(newProgress);
                    if (newProgress == 100) {
                        mProgressBar.setVisibility(View.GONE);
                    }
                }

            }
        });

    }

    @Override
    protected void initData() {
        int id = getIntent().getIntExtra(DETAIL_ID, 0);
        DetailPresenter presenter = new DetailPresenter(this);
        presenter.onGetDetailData(id);
    }


    @Override
    public void onSuccess(Detail detail) {
        String image = detail.getImage();
        String title = detail.getTitle();
        tvTitle.setText(title);

        if (!TextUtils.isEmpty(image)) {
            ImageLoaderUtil.loadImage(mActivity, image, imPoster);
        }

        String body = detail.getBody();
        mWebView.loadDataWithBaseURL("x-data://base", ConvertHtml.convertZhihuContent(mActivity, body), "text/html", "utf-8", null);


    }

    @Override
    public void onFail(String string) {
        ToastUtil.showToast(string);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
