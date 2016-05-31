package com.f8boss.zhihuribao.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jiansion on 2016/5/6.
 *
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            ActivityController.addActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            ActivityController.removeActivity(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
