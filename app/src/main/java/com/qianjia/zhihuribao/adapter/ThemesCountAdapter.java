package com.qianjia.zhihuribao.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianjia.basemodel.widget.BaseRecyclerAdapter;
import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.bean.ThemesCount;
import com.qianjia.zhihuribao.ui.activity.DetailActivity;
import com.qianjia.zhihuribao.util.ImageLoaderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jiansion on 2017/3/20.
 */

public class ThemesCountAdapter extends BaseRecyclerAdapter<ThemesCount.StoriesBean, ThemesCountAdapter.ViewHolder> {


    private LayoutInflater inflater;

    public ThemesCountAdapter(Context context, List<ThemesCount.StoriesBean> datas) {
        super(context, datas);
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected ViewHolder setDefaultView(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    protected void onViewSetting(ViewHolder holder, int pos) {
        ThemesCount.StoriesBean storiesBean = mList.get(pos);
        String title = storiesBean.getTitle();
        int id = storiesBean.getId();
        List<String> images = storiesBean.getImages();
        if (images == null) {
            holder.imagePosterLayout.setVisibility(View.GONE);
        } else {
            holder.imagePosterLayout.setVisibility(View.VISIBLE);
            ImageLoaderUtil.loadImage(mContext, images.get(0), holder.imPoster);
        }

        holder.tvTitle.setText(title);

        holder.mCardView.setOnClickListener(v -> DetailActivity.onToDatailPage(mContext, id));


    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvTitle)
        TextView tvTitle;
        @BindView(R.id.imPoster)
        ImageView imPoster;
        @BindView(R.id.tvMoreImage)
        TextView tvMoreImage;
        @BindView(R.id.imagePosterLayout)
        FrameLayout imagePosterLayout;
        @BindView(R.id.mCardView)
        CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            tvMoreImage.setVisibility(View.GONE);
        }
    }
}
