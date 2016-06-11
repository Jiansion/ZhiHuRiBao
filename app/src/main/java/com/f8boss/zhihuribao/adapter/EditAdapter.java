package com.f8boss.zhihuribao.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.f8boss.zhihuribao.MyApplication;
import com.f8boss.zhihuribao.activity.EditWebActivity;
import com.f8boss.zhihuribao.bean.ThemBean;
import com.f8boss.zhihuribao.util.ImageLoaderUtil;
import com.f8boss.zhihuribao.widget.CircleImageView;

import java.util.List;

/**
 * Created by jiansion on 2016/6/1.
 */
public class EditAdapter extends RecyclerView.Adapter<EditAdapter.ViewHolder> {


    private List<ThemBean.EditorsBean> list;
    private Context context;

    public EditAdapter(Context context, List<ThemBean.EditorsBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CircleImageView circleImageView = new CircleImageView(MyApplication.getInstance());

        return new ViewHolder(circleImageView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        ((CircleImageView) holder)

        ImageLoaderUtil.displayImage(list.get(position).getAvatar(), ((CircleImageView) holder.itemView));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, EditWebActivity.class);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
