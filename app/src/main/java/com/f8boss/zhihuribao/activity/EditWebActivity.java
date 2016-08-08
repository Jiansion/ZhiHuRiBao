package com.f8boss.zhihuribao.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.webkit.WebView;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.util.Urls;
import com.f8boss.zhihuribao.util.Utils;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiansion on 2016/6/1.
 * 主编主页
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
        String id = getIntent().getStringExtra("id");
        webViewEdit.loadUrl(Utils.getReplaceFormat(Urls.EDIT_PAGE, "$", id));
    }

    private void initToolbar() {
        setSupportActionBar(mToolbar);
        mToolbar.setTitleTextColor(Color.WHITE);
        mToolbar.setNavigationIcon(R.mipmap.back);
    }

    public static void startEditActivity(Context mContext, String id) {
        Intent intent = new Intent(mContext, EditWebActivity.class);
        intent.putExtra("id", id);
        mContext.startActivity(intent);
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
