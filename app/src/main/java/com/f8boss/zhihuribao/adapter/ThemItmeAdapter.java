package com.f8boss.zhihuribao.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.f8boss.zhihuribao.MyApplication;
import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.activity.WebContentActivity;
import com.f8boss.zhihuribao.bean.ThemBean;
import com.f8boss.zhihuribao.util.LoaderImageUtil;

import java.util.List;

/**
 * Created by jiansion on 2016/5/31.
 * 各项栏目的Item的Adapter
 */
public class ThemItmeAdapter extends RecyclerView.Adapter<ThemItmeAdapter.ViewHolder> {

    private Context context;
    private List<ThemBean.StoriesBean> mList;

    public ThemItmeAdapter(Context context, List<ThemBean.StoriesBean> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_them, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        ThemBean.StoriesBean storiesBean = mList.get(position);
        holder.tvTitle.setText(storiesBean.getTitle());
        List<String> images = storiesBean.getImages();
        //先判断是否存在有Itme的图片，如果没有隐藏该图标
        if (images != null) {
            String imageUrl = images.get(0);
            LoaderImageUtil.downLoadImage(MyApplication.getInstance(), imageUrl, holder.imageContentIcon);

        } else {
            holder.imageContentIcon.setVisibility(View.GONE);
        }

        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                WebContentActivity.startAction(context, mList.get(position).getId() + "");
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private ImageView imageContentIcon;
        private CardView mCardView;


        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvTitle.setTextColor(Color.BLACK);
            imageContentIcon = (ImageView) itemView.findViewById(R.id.imageContentIcon);
            mCardView = (CardView) itemView.findViewById(R.id.mCardView);

        }
    }
}
