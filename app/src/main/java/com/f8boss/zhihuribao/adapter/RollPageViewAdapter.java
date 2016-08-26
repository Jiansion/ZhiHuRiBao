package com.f8boss.zhihuribao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f8boss.zhihuribao.activity.WebContentActivity;
import com.f8boss.zhihuribao.bean.IndextItemBean;
import com.f8boss.zhihuribao.util.PicassoUtil;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;

import java.util.List;

/**
 * Created by jiansion on 2016/8/2.
 * 首页轮播图适配器
 */
public class RollPageViewAdapter extends StaticPagerAdapter {

    private List<IndextItemBean.TopStoriesBean> list;
    private Context mContext;

    public RollPageViewAdapter(Context mContext, List<IndextItemBean.TopStoriesBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @Override
    public View getView(ViewGroup container, final int position) {

        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        ImageView imageView = new ImageView(mContext);
        TextView tvTitle = new TextView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        tvTitle.setTextSize(23);
        tvTitle.setTextColor(Color.WHITE);
        tvTitle.setPadding(10, 10, 10, 10);
        RelativeLayout.LayoutParams lP1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lP1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        lP1.setMargins(15, 10, 15, 30);
        relativeLayout.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        relativeLayout.addView(tvTitle, lP1);

        tvTitle.setText(list.get(position).getTitle());
        PicassoUtil.downLoadImage(mContext, list.get(position).getImage(), imageView);


        //事件点击处理
        relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebContentActivity.startAction(mContext, list.get(position).getId() + "");
            }
        });


        return relativeLayout;
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
