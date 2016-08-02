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

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.activity.WebContentActivity;
import com.f8boss.zhihuribao.bean.IndextItemBean;
import com.f8boss.zhihuribao.util.LoaderImageUtil;

import java.util.List;

/**
 * Created by jiansion on 2016/8/2.
 * 首页的列表数据的Adapter
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_NORMAL = 1;

    private View mHeaderView;


    private List<IndextItemBean.StoriesBean> mList;

    private Context mContext;

    public RecyclerAdapter(Context mContext, List<IndextItemBean.StoriesBean> mList) {
        this.mContext = mContext;
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
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_indext_recycler, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (getItemViewType(position) == TYPE_HEADER) {
            return;
        }
        final int pos = getRealPosition(holder);
        final IndextItemBean.StoriesBean storiesBean = mList.get(pos);
        if (holder instanceof ViewHolder) {
            holder.tvTitle.setText(storiesBean.getTitle());
            holder.tvTitle.setTextColor(Color.BLACK);
            //带标记滑动图片
            LoaderImageUtil.downLoadImage(mContext, storiesBean.getImages().get(0), "indexImage", holder.imageContentIcon);
            holder.mCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WebContentActivity.startAction(mContext, storiesBean.getId() + "");

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
        private ImageView imageContentIcon;
        private TextView tvTitle;
        private CardView mCardView;

        public ViewHolder(View itemView) {
            super(itemView);
            if (itemView == mHeaderView) {
                return;
            }
            imageContentIcon = ((ImageView) itemView.findViewById(R.id.imageContentIcon));
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            mCardView = (CardView) itemView.findViewById(R.id.mCardView);

        }
    }
}