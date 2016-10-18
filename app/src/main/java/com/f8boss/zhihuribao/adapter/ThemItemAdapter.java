package com.f8boss.zhihuribao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.f8boss.zhihuribao.MyApplication;
import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.activity.WebContentActivity;
import com.f8boss.zhihuribao.bean.ThemBean;
import com.f8boss.zhihuribao.util.PicassoUtil;

import java.util.List;

/**
 * Created by jiansion on 2016/5/31.
 * 各项栏目的Item的Adapter
 */
public class ThemItemAdapter extends RecyclerView.Adapter<ThemItemAdapter.ViewHolder> {


    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;
    private View mHeaderView;


    private Context context;
    private List<ThemBean.StoriesBean> mList;

    public ThemItemAdapter(Context context, List<ThemBean.StoriesBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    public void setHeaderView(View headerView) {
        mHeaderView = headerView;
        notifyItemInserted(0);
    }

    public View getHeaderView() {
        return mHeaderView;
    }

    @Override
    public int getItemViewType(int position) {
        if (mHeaderView == null) {
            return TYPE_NORMAL;
        }
        if (position == 0) {
            return TYPE_HEADER;
        }
        return TYPE_NORMAL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mHeaderView != null && viewType == TYPE_HEADER) {
            return new ViewHolder(mHeaderView);
        }
        View view = LayoutInflater.from(context).inflate(R.layout.item_them, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        final int pos = getRealPosition(holder);
        final ThemBean.StoriesBean storiesBean = mList.get(pos);
        if (holder instanceof ViewHolder) {
            holder.tvTitle.setText(storiesBean.getTitle());
            List<String> images = storiesBean.getImages();
            //先判断是否存在有Itme的图片，如果没有隐藏该图标
            if (images != null) {
                String imageUrl = images.get(0);
                holder.imageContentIcon.setVisibility(View.VISIBLE);
                PicassoUtil.downLoadImage(MyApplication.getInstance(), imageUrl, "ThemImage", holder.imageContentIcon);
            } else {
                holder.imageContentIcon.setVisibility(View.GONE);
            }

            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebContentActivity.startAction(context, storiesBean.getId() + "");
                }
            });
        }
    }

    public int getRealPosition(RecyclerView.ViewHolder holder) {
        int position = holder.getLayoutPosition();
        return mHeaderView == null ? position : position - 1;
    }

    @Override
    public int getItemCount() {
        return mHeaderView == null ? mList.size() : mList.size() + 1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView imageContentIcon;
        private CardView mCardView;


        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTitle.setTextColor(Color.BLACK);
            imageContentIcon = (ImageView) itemView.findViewById(R.id.imageContentIcon);
            mCardView = (CardView) itemView.findViewById(R.id.mCardView);

        }
    }
}
