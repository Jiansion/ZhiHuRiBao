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
import com.qianjia.zhihuribao.ui.activity.DetailDefaultActivity;
import com.qianjia.zhihuribao.ui.activity.DetailOtherActivity;
import com.qianjia.zhihuribao.util.ImageLoaderUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jiansion on 2017/3/20.
 * 其他主题日报列表
 */

public class ThemesCountAdapter extends BaseRecyclerAdapter<ThemesCount.StoriesBean, RecyclerView.ViewHolder> {


    private LayoutInflater inflater;

    public ThemesCountAdapter(Context context, List<ThemesCount.StoriesBean> datas) {
        super(context, datas);
        inflater = LayoutInflater.from(context);
    }

    @Override
    protected RecyclerView.ViewHolder setHeadFooterView(ViewGroup parent, int viewType) {
        return new HeadFooterHolder(getHeadView());
    }

    @Override
    protected ViewHolder setDefaultView(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }


    @Override
    protected void onViewSetting(RecyclerView.ViewHolder vh, int pos) {
        ThemesCountAdapter.ViewHolder holder = (ThemesCountAdapter.ViewHolder) vh;
        ThemesCount.StoriesBean storiesBean = mList.get(pos);
        String title = storiesBean.getTitle();
        int id = storiesBean.getId();
        List<String> images = storiesBean.getImages();
        if (images == null) {
            holder.imagePosterLayout.setVisibility(View.GONE);
        } else {
            holder.imagePosterLayout.setVisibility(View.VISIBLE);
            ImageLoaderUtil.loadThumbnail(mContext, images.get(0), holder.imPoster);
        }

        holder.tvTitle.setText(title);

        int type = storiesBean.getType();

        holder.mCardView.setOnClickListener(v -> {
            if (type == 0) {
                DetailDefaultActivity.onToDetailPage(mContext, id, holder.mCardView);
            } else {
                DetailOtherActivity.onToDetailPage(mContext, id, type);
            }
        });
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
