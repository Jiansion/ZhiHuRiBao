package com.qianjia.zhihuribao.ui.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.bean.Detail;
import com.qianjia.zhihuribao.presenter.DetailPresenter;
import com.qianjia.zhihuribao.ui.view.BaseView;
import com.qianjia.zhihuribao.util.ConvertHtml;

import butterknife.BindView;

/**
 * Created by Jiansion on 2017/3/22.
 * 详情页 type : 1
 */

public class DetailOtherActivity extends BaseActivity implements BaseView<Detail> {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mToolbarLayout)
    CollapsingToolbarLayout mToolbarLayout;
    @BindView(R.id.mWebView)
    WebView mWebView;
    @BindView(R.id.mProgressBar)
    ProgressBar mProgressBar;

    private final static String DETAIL_ID = "detail_id";
    private final static String DETAIL_TYPE = "detail_type";
    private int type;

    private DetailPresenter presenter;
    private boolean firstRead = true;

    public static void onToDetailPage(Context context, int id, int type) {
        Intent intent = new Intent(context, DetailOtherActivity.class);
        intent.putExtra(DETAIL_ID, id);
        intent.putExtra(DETAIL_TYPE, type);
        context.startActivity(intent);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_detail_other;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (firstRead) {
                    view.loadUrl(url);
                }
                firstRead = false;
                return super.shouldOverrideUrlLoading(view, url);
            }
        });

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
        type = getIntent().getIntExtra(DETAIL_TYPE, 1);
        presenter = new DetailPresenter(this);
        presenter.onGetDetailData(id);

    }

    @Override
    public void onSuccess(Detail detail) {
        if (type == 1) {
            mWebView.loadUrl(detail.getShare_url());
        } else {
            String body = detail.getBody();
            mWebView.loadDataWithBaseURL("x-data://base", ConvertHtml.convertZhihuContent(mActivity, body), "text/html", "utf-8", null);
        }

    }

    @Override
    public void onFail(String string) {

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
