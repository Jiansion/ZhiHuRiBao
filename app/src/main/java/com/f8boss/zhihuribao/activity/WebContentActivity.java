package com.f8boss.zhihuribao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.util.ImageLoaderUtil;
import com.f8boss.zhihuribao.util.OkHttpUtils;
import com.f8boss.zhihuribao.util.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiansion on 2016/5/28.
 */
public class WebContentActivity extends AppCompatActivity {


    @Bind(R.id.mToolbar)
    Toolbar mToolbar;
    @Bind(R.id.mCollapsingToobarLayout)
    CollapsingToolbarLayout mCollapsingToobarLayout;
    @Bind(R.id.mAppBarLayout)
    AppBarLayout mAppBarLayout;
    @Bind(R.id.mWebView)
    WebView mWebView;
    @Bind(R.id.tvTitle)
    TextView tvTitle;
    @Bind(R.id.tvImageSoure)
    TextView tvImageSoure;
    @Bind(R.id.imageHeader)
    ImageView imageHeader;


    private String TAG = "WebContentActivity";

    private String body;
    private String imageUrl;
    //    public String cssurl;
    private String share_url;
    private String image_source;
    private String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_PROGRESS);
        setContentView(R.layout.activity_webcontent);

        ButterKnife.bind(this);
        initToolBar();
        Intent intent = getIntent();
        String id = intent.getStringExtra("Id");
        Log.e(TAG, "onCreate: " + id);
        initWebView();
        downLoadContent(id);
    }

    private void initToolBar() {
        setSupportActionBar(mToolbar);
        mCollapsingToobarLayout.setTitle(" ");
        mCollapsingToobarLayout.setCollapsedTitleTextColor(Color.TRANSPARENT);
        mToolbar.setNavigationIcon(R.mipmap.back);
//        mToolbar.setTitle(" ");
//        mToolbar.setTitleTextColor(Color.TRANSPARENT);
    }

    private void downLoadContent(String id) {

        OkHttpUtils.onGetToDownLoadData(this, Urls.NEWCONTENT + id, OkHttpUtils.TYPE_TEXT, new OkHttpUtils.OnCallBack() {


            @Override
            public void callBackUIString(String data) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
//                    cssurl = jsonObject.getJSONArray("css").getString(0);
                    body = jsonObject.getString("body");
                    imageUrl = jsonObject.getString("image");
                    title = jsonObject.getString("title");
                    image_source = jsonObject.getString("image_source");
                    share_url = jsonObject.getString("share_url");

//                    Log.e(TAG, "callBackUIString: " + cssurl);
                    setViewData();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void callBackUIByte(byte[] datas) {

            }
        });

    }

    private void setViewData() {
        String html = toGetStyle();
        ImageLoaderUtil.displayImage(imageUrl, imageHeader);
        tvImageSoure.setText(image_source);
        tvImageSoure.setTextColor(Color.WHITE);
        tvTitle.setTextColor(Color.WHITE);
        tvTitle.setText(title);

        mWebView.loadData(html, "text/html; charset=utf-8", "utf-8");
    }

    private void initWebView() {
        // 允许 WebView 进行缩放
        mWebView.getSettings().setSupportZoom(true);

        //设置WebView屏幕自适应
//        ViewGroup.LayoutParams layoutParams = mWebView.getLayoutParams();
//        layoutParams.width = getResources().getDisplayMetrics().widthPixels;
//        mWebView.setLayoutParams(layoutParams);

//        mWebView.getSettings().setUseWideViewPort(true);
        //设置可以执行Js代码
        mWebView.getSettings().setJavaScriptEnabled(true);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            mWebView.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }


        //优化WebView，使WebView页面加载出来后再加载图片
        if (Build.VERSION.SDK_INT >= 19) {
            mWebView.getSettings().setLoadsImagesAutomatically(true);
        } else {
            mWebView.getSettings().setLoadsImagesAutomatically(false);
        }


        //设置WebView 的进度条
        mWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                // Activities and WebViews measure progress with different scales.
                // The progress meter will automatically disappear when we reach 100%
                WebContentActivity.this.setProgress(progress * 1000);
            }
        });


        //防止点中WebView中的链接是使用系统浏览器访问
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mWebView.loadUrl(url);
                return false;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                Toast.makeText(WebContentActivity.this, "页面加载出错", Toast.LENGTH_SHORT).show();
            }


            //            @Override//页面加载完成后回调
//            public void onPageFinished(WebView view, String url) {
////                imageView.setVisibility(View.GONE);
//                if (!mWebView.getSettings().getLoadsImagesAutomatically()) {
//                    mWebView.getSettings().setLoadsImagesAutomatically(true);
//                }
//            }
//
        });


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
        //file:///android_asset/zhihu.css

        sb.append("<html>\n")
                .append("<link rel=\"stylesheet\" type=\"text/css\" href=").append("http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3")
                .append(">\n")
                .append("<body>\n")
                .append(body)
                .append("</body>")
                .append("</html>");
        return String.valueOf(sb);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web, menu);
        return super.onCreateOptionsMenu(menu);
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
                Toast.makeText(WebContentActivity.this, "赞", Toast.LENGTH_SHORT).show();
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


}
