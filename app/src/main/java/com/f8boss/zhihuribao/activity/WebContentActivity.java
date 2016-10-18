package com.f8boss.zhihuribao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.bean.StoryExtra;
import com.f8boss.zhihuribao.util.LogUtil;
import com.f8boss.zhihuribao.util.PicassoUtil;
import com.f8boss.zhihuribao.util.Urls;
import com.f8boss.zhihuribao.util.Utils;
import com.f8boss.zhihuribao.widget.NoScrollWebView;
import com.google.gson.Gson;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.cache.CacheMode;
import com.lzy.okhttputils.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiansion on 2016/5/28.
 * 详情页，内容展示
 */
public class WebContentActivity extends BaseActivity {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;
    @BindView(R.id.mCollapsingToobarLayout)
    CollapsingToolbarLayout mCollapsingToobarLayout;
    @BindView(R.id.mAppBarLayout)
    AppBarLayout mAppBarLayout;
    @BindView(R.id.mWebView)
    NoScrollWebView mWebView;
    @BindView(R.id.tvImageSoure)
    TextView tvImageSoure;
    @BindView(R.id.imageHeader)
    ImageView imageHeader;
    @BindView(R.id.relativeHeader)
    RelativeLayout relativeHeader;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.webProgressBar)
    ProgressBar webProgressBar;


    private String TAG = "WebContentActivity";

    private String body;
    private String imageUrl;
    private String share_url;
    private String image_source;
    private String title = "null";

    @Override
    protected int initLayoutId() {
        return R.layout.activity_webcontent;
    }

    @Override
    protected void initView() {
        initToolBar();
        initWebView();
    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String id = intent.getStringExtra("Id");
        downLoadContent(id);
        downLoadExtra(id);
        LogUtil.e(TAG, Urls.NEW_CONTENT + id);
    }

    //获取对应新闻的额外信息，如评论数量，所获的『赞』的数量
    private void downLoadExtra(String id) {

        String url = Utils.getReplaceFormat(Urls.STORY_EXTRA, "$", id);
        OkHttpUtils.get(url)
                .tag(this)
                .cacheMode(CacheMode.REQUEST_FAILED_READ_CACHE)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        LogUtil.e(TAG, s);
                        Gson gson = new Gson();
                        StoryExtra storyExtra = gson.fromJson(s, StoryExtra.class);
                    }
                });

    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        mAppBarLayout.setExpanded(false);
        mCollapsingToobarLayout.setTitle(" ");
        //折叠的字体颜色颜色
        mCollapsingToobarLayout.setCollapsedTitleTextColor(Color.WHITE);
        //展开的字体颜色
        mCollapsingToobarLayout.setExpandedTitleColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.mipmap.back);
    }

    private void downLoadContent(String id) {
        OkHttpUtils
                .get(Urls.NEW_CONTENT + id)
                .tag(this)
                .cacheKey("news_" + id)
                .cacheMode(CacheMode.IF_NONE_CACHE_REQUEST)
                .execute(new StringCallback() {
                    @Override
                    public void onResponse(boolean isFromCache, String s, Request request, @Nullable Response response) {
                        try {
                            LogUtil.e(TAG, s);
                            JSONObject jsonObject = new JSONObject(s);
                            body = jsonObject.getString("body");
                            share_url = jsonObject.getString("share_url");
                            imageUrl = jsonObject.getString("image");
                            title = jsonObject.getString("title");
                            image_source = jsonObject.getString("image_source");
                            setViewData();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            LogUtil.e(TAG, e.toString());
                            relativeHeader.setVisibility(View.GONE);
                            setOtherViewData();
                        }
                    }
                });
    }

    //没有头部图片的WEB加载
    private void setOtherViewData() {
        LogUtil.e(TAG, "进来到其他的吗?");
        StringBuilder sb = new StringBuilder();
        sb.append("<html>\n")
                .append("<link rel=\"stylesheet\" type=\"text/css\" href=")
                .append("http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3")
//                .append("file:///android_asset/zhihu.html")
                .append(">\n")
                .append("<body>\n")
                .append(body)
                .append("</body>")
                .append("</html>");
        String html = String.valueOf(sb);
        mWebView.loadDataWithBaseURL(null, html, "text/html", "utf-8", null);
    }


    //带有头部图片的WEB加载
    private void setViewData() {
        String html = toGetStyle();
        PicassoUtil.downLoadImage(mActivity, imageUrl, imageHeader);
        tvImageSoure.setText(image_source);
        tvImageSoure.setTextColor(Color.WHITE);
        tvTitle.setTextColor(Color.WHITE);
        tvTitle.setText(title);


        mWebView.loadData(html, "text/html; charset=utf-8", "utf-8");
    }

    private void initWebView() {
        // 允许 WebView 进行缩放
        WebSettings settings = mWebView.getSettings();

        //设置可以执行Js代码
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }


        //优化WebView，使WebView页面加载出来后再加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            settings.setLoadsImagesAutomatically(true);
        } else {
            settings.setLoadsImagesAutomatically(false);
        }

        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                webProgressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    webProgressBar.setVisibility(View.GONE);
                }
            }
        });


        //防止点中WebView中的链接是使用系统浏览器访问
//        mWebView.setWebViewClient(new WebViewClient() {
//            @Override
//            public boolean shouldOverrideUrlLoading(WebView view, String url) {
//                mWebView.loadUrl(url);
//                return false;
//            }
//
//            @Override
//            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
//                showToast("页面加载出错");
//            }
//        });

    }


    private String toGetStyle() {

        StringBuilder sb = new StringBuilder();


        String s1 = "<div class=\"headline\">\n" +
                "\n" +
                "<div class=\"img-place-holder\"></div>\n" +
                "\n" +
                "\n" +
                "\n" +
                "</div>";
        String s2 = "\n";
        body = body.replace(s1, s2);

        // http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3
        //file:///android_asset/zhihu.html
        sb.append("<html>\n")
                .append("<link rel=\"stylesheet\" type=\"text/css\" href=")
                .append("http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3")
//                .append("file:///android_asset/zhihu.html")
                .append(">\n")
                .append("<body>\n")
                .append(body)
                .append("</body>")
                .append("</html>");
        return String.valueOf(sb);

    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                toGoback();
                break;
            //分享
            case R.id.action_share:

                break;
            //收藏
            case R.id.action_collect:
                break;
            //评论
            case R.id.action_comment:
                break;
            //点赞
            case R.id.action_praise:
                showToast("赞");
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {// 按下回退键时,回到初始上一页
            if (toGoback()) return true;

        }
        return false;
    }

    private boolean toGoback() {
        // 判断 WebView 是否有历史记录
        if (mWebView.canGoBack()) {
            // 防止按回退键时直接退出
            mWebView.goBack();
            return true;
        } else {
            finish();
        }
        return false;
    }


    //用于提供与其他Activity调用跳转到该Activity
    public static void startAction(Context context, String Id) {
        Intent intent = new Intent(context, WebContentActivity.class);
        intent.putExtra("Id", Id);
        context.startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }
}
