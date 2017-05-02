package com.qianjia.zhihuribao.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.bean.ThemesCount;
import com.qianjia.zhihuribao.ui.activity.DetailDefaultActivity;
import com.qianjia.zhihuribao.ui.activity.DetailOtherActivity;
import com.qianjia.zhihuribao.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Jiansion on 2017/3/20.
 * 其他主题日报列表
 */

public class ThemesCountAdapter extends BaseQuickAdapter<ThemesCount.StoriesBean, BaseViewHolder> {


    public ThemesCountAdapter(@Nullable List<ThemesCount.StoriesBean> data) {
        super(R.layout.item_list, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ThemesCount.StoriesBean item) {
        helper.setText(R.id.tvTitle, item.getTitle());
        helper.getView(R.id.tvMoreImage).setVisibility(View.GONE);
        if (item.getImages() != null) {
            ImageView imPoster = helper.getView(R.id.imPoster);
            helper.getView(R.id.imagePosterLayout).setVisibility(View.VISIBLE);
            ImageLoaderUtil.loadThumbnail(mContext, item.getImages().get(0), imPoster);
        } else {
            helper.getView(R.id.imagePosterLayout).setVisibility(View.GONE);
        }

        helper.getView(R.id.mCardView).setOnClickListener(v -> {
            int id = item.getId();
            if (item.getType() == 0) {
                DetailDefaultActivity.onToDetailPage(mContext, id, helper.getView(R.id.imPoster));
            } else {
                DetailOtherActivity.onToDetailPage(mContext, id, item.getType());

            }
        });

    }

}
