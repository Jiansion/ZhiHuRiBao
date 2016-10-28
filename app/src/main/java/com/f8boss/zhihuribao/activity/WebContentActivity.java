package com.f8boss.zhihuribao.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.bean.StoryExtra;
import com.f8boss.zhihuribao.util.JSInterface;
import com.f8boss.zhihuribao.util.LogUtil;
import com.f8boss.zhihuribao.util.PicassoUtil;
import com.f8boss.zhihuribao.util.ToastUtil;
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
import butterknife.OnClick;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by jiansion on 2016/5/28.
 * 详情页，内容展示
 */
public class WebContentActivity extends BaseActivity {
    @BindView(R.id.mToolbar)
    Toolbar mToolbar;

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

    @BindView(R.id.imageCollect)
    ImageView imageCollect;

    @BindView(R.id.tvComment)
    TextView tvComment;

    @BindView(R.id.tvPraise)
    TextView tvPraise;


    private String TAG = "WebContentActivity";

    private String body;
    private String imageUrl;
    private String share_url;
    private String image_source;
    private String title = "null";

    @Override
    protected int initLayoutId() {
        transparentBar();
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
                        tvComment.setText(String.valueOf(storyExtra.getComments()));
                        tvPraise.setText(String.valueOf(storyExtra.getPopularity()));
                    }
                });

    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
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

    @SuppressLint({"SetJavaScriptEnabled", "AddJavascriptInterface"})
    private void initWebView() {
        // 允许 WebView 进行缩放
        WebSettings settings = mWebView.getSettings();

        //设置可以执行Js代码
        settings.setJavaScriptEnabled(true);
        settings.setSupportZoom(true);
        // 添加js交互接口类，并起别名 imagelistner
        mWebView.addJavascriptInterface(new JSInterface(mActivity), "imagelistner");


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


        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                addImageClickListner();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Uri uri = Uri.parse(url); //url为你要链接的地址
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            }

        });

    }


    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        mWebView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }


    //设置网页样式
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


    //用于提供与其他Activity调用跳转到该Activity
    public static void startAction(Context context, String Id) {
        Intent intent = new Intent(context, WebContentActivity.class);
        intent.putExtra("Id", Id);
        context.startActivity(intent);
    }

    @OnClick({R.id.imageBack, R.id.imageShare, R.id.imageCollect, R.id.tvComment, R.id.tvPraise})
    public void viewOnClick(View view) {
        switch (view.getId()) {
            case R.id.imageBack:
                finish();
                break;
            case R.id.imageShare:
                ToastUtil.showToast(mActivity, "分享");
                break;
            case R.id.imageCollect:
                ToastUtil.showToast(mActivity, "收藏");
                break;
            case R.id.tvComment:
                ToastUtil.showToast(mActivity, "评论");
                break;
            case R.id.tvPraise:
                ToastUtil.showToast(mActivity, "点赞");
                break;

        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        OkHttpUtils.getInstance().cancelTag(this);
    }

}
