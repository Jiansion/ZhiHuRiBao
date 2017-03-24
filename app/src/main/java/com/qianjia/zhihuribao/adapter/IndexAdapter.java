package com.qianjia.zhihuribao.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.bean.IndexList;
import com.qianjia.zhihuribao.ui.activity.DetailDefaultActivity;
import com.qianjia.zhihuribao.ui.activity.DetailOtherActivity;
import com.qianjia.zhihuribao.util.ImageLoaderUtil;

import java.util.List;

/**
 * Created by Jiansion on 2017/3/14.
 * 首页
 */

public class IndexAdapter extends RecyclerView.Adapter {

    private final static int DEFAULT_TYPE = 1;//正常布局

    private final static int HEAD_TYPE = 2;//头布局

    private List<IndexList.StoriesBean> mList;

    private View headView;

    private LayoutInflater inflater;

    private Context context;

    public IndexAdapter(Context context, List<IndexList.StoriesBean> mList) {
        inflater = LayoutInflater.from(context);
        this.mList = mList;
        this.context = context;
    }

    public void addItems(List<IndexList.StoriesBean> mList) {
        //当有头布局时刷新位置理应在要往后移一位
        int start = getHeadView() == null ? this.mList.size() : this.mList.size() + 1;
        this.mList.addAll(mList);
        notifyItemRangeChanged(start, mList.size());
    }

    public void updateData(List<IndexList.StoriesBean> mList) {
        this.mList.clear();
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }


    public void setHeadView(View headView) {
        if (this.headView == null) {
            this.headView = headView;
            notifyItemInserted(0);
        }
    }

    private View getHeadView() {
        return headView == null ? null : headView;
    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD_TYPE;
        } else {
            return DEFAULT_TYPE;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case DEFAULT_TYPE:
                View defaultView = inflater.inflate(R.layout.item_list, parent, false);
                return new ContentHolder(defaultView);
            case HEAD_TYPE:
                return new HeadHolder(getHeadView());
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ContentHolder) {
            ContentHolder contentHolder = (ContentHolder) holder;
            int pos = position > 0 ? position - 1 : 0;
            IndexList.StoriesBean storiesBean = mList.get(pos);
            if (storiesBean.isMultipic()) {
                contentHolder.tvMoreImage.setVisibility(View.VISIBLE);
            } else {
                contentHolder.tvMoreImage.setVisibility(View.GONE);
            }
            contentHolder.tvTitle.setText(storiesBean.getTitle());
            ImageLoaderUtil.loadImage(context, storiesBean.getImages().get(0), contentHolder.imPoster);
            contentHolder.mCardView.setOnClickListener(v -> {
                int type = storiesBean.getType();
                int id = storiesBean.getId();
                if (type == 0) {
                    DetailDefaultActivity.onToDetailPage(context, id);
                } else {
                    DetailOtherActivity.onToDetailPage(context, id, type);
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    private static class ContentHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        ImageView imPoster;
        TextView tvMoreImage;
        CardView mCardView;

        ContentHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            imPoster = (ImageView) itemView.findViewById(R.id.imPoster);
            tvMoreImage = (TextView) itemView.findViewById(R.id.tvMoreImage);
            mCardView = (CardView) itemView.findViewById(R.id.mCardView);
        }
    }

    private static class HeadHolder extends RecyclerView.ViewHolder {
        HeadHolder(View itemView) {
            super(itemView);
        }
    }

}
