package com.qianjia.zhihuribao.ui.activity;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spanned;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianjia.basemodel.view.BaseView;
import com.qianjia.basemodel.view.ProgressView;
import com.qianjia.statuslayout.StatusLayout;
import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.base.BaseActivity;
import com.qianjia.zhihuribao.bean.Detail;
import com.qianjia.zhihuribao.presenter.DetailPresenter;
import com.qianjia.zhihuribao.util.ImageLoaderUtil;
import com.qianjia.zhihuribao.util.LogUtil;

import butterknife.BindView;

/**
 * Created by Jiansion on 2017/3/15.
 * 知乎详情页
 */

public class DetailActivity extends BaseActivity implements BaseView<Detail> {
    private static final String DETAIL_ID = "detail_id";
    private static final String TAG = DetailActivity.class.getSimpleName();
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

    @BindView(R.id.mStatusLayout)
    StatusLayout mStatusLayout;


    @BindView(R.id.tvTitle)
    TextView tvTitle;

    private DetailPresenter presenter;

    private int id;

    public static void onToDatailPage(Context context, int id) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(DETAIL_ID, id);
        context.startActivity(intent);
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_content;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {

//        mStatusLayout.showLoading();

        mWebView.setScrollbarFadingEnabled(true);
        //能够和js交互
        mWebView.getSettings().setJavaScriptEnabled(true);
        //缩放,设置为不能缩放可以防止页面上出现放大和缩小的图标
        mWebView.getSettings().setBuiltInZoomControls(false);
        //缓存
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        //开启DOM storage API功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启application Cache功能
        mWebView.getSettings().setAppCacheEnabled(false);

        mToolbar.setNavigationIcon(R.mipmap.ic_action_navigation);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);


//        mWebView.setWebChromeClient(new WebChromeClient() {
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//                super.onProgressChanged(view, newProgress);
//                if (newProgress == 100) {
//                    LogUtil.e(TAG, "加载完成");
//                    mStatusLayout.showContent();
//                }
//            }
//        });

    }

    @Override
    protected void initData() {
        id = getIntent().getIntExtra(DETAIL_ID, 0);
        presenter = new DetailPresenter(this);
        presenter.onGetDatailData(id);
    }


    @Override
    public void onSuccess(Detail detail) {
        String title = detail.getTitle();
        tvTitle.setText(title);
        ImageLoaderUtil.loadImage(mActivity, detail.getImage(), imPoster);

        String body = detail.getBody();
        mWebView.loadDataWithBaseURL("x-data://base", convertZhihuContent(body), "text/html", "utf-8", null);


    }

    @Override
    public void onError(ErrorType type) {
        mStatusLayout.showError("发生异常,请重试", v -> {
            mStatusLayout.showLoading();
            presenter.onGetDatailData(id);
        });
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


    private String convertZhihuContent(String preResult) {
        preResult = preResult.replace("<div class=\"img-place-holder\">", "");
        preResult = preResult.replace("<div class=\"headline\">", "");

        // 在api中，css的地址是以一个数组的形式给出，这里需要设置
        // in fact,in api,css addresses are given as an array
        // api中还有js的部分，这里不再解析js
        // javascript is included,but here I don't use it
        // 不再选择加载网络css，而是加载本地assets文件夹中的css
        // use the css file from local assets folder,not from network
        String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/zhihu_daily.css\" type=\"text/css\">";


        // 根据主题的不同确定不同的加载内容
        // load content judging by different theme
        String theme = "<body className=\"\" onload=\"onLoaded()\">";
        if ((getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK)
                == Configuration.UI_MODE_NIGHT_YES) {
            theme = "<body className=\"\" onload=\"onLoaded()\" class=\"night\">";
        }

        return new StringBuilder()
                .append("<!DOCTYPE html>\n")
                .append("<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\">\n")
                .append("<head>\n")
                .append("\t<meta charset=\"utf-8\" />")
                .append(css)
                .append("\n</head>\n")
                .append(theme)
                .append(preResult)
                .append("</body></html>").toString();
    }

}
