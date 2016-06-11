package com.f8boss.zhihuribao.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.f8boss.zhihuribao.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiansion on 2016/6/1.
 */
public class EditWebActivity extends BaseActivity {


    @Bind(R.id.mToolbar)
    Toolbar mToolbar;
    @Bind(R.id.webViewEdit)
    WebView webViewEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_editweb);
        ButterKnife.bind(this);

        initToolbar();

//        WebView view = new WebView(this);
//        setContentView(view);
        webViewEdit.loadUrl("http://news-at.zhihu.com/api/4/editor/79/profile-page/android");

    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.mipmap.back);
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
