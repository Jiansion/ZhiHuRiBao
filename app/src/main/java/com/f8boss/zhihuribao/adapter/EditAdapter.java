package com.f8boss.zhihuribao.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.f8boss.zhihuribao.MyApplication;
import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.activity.EditWebActivity;
import com.f8boss.zhihuribao.bean.ThemBean;
import com.f8boss.zhihuribao.util.PicassoUtil;

import java.util.List;

/**
 * Created by jiansion on 2016/6/1.
 * 主编头像列表适配器
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
//        ImageView circleImageView = new ImageView(MyApplication.getInstance());
        View view = LayoutInflater.from(context).inflate(R.layout.item_edit, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PicassoUtil.downCircleImage(MyApplication.getInstance(), list.get(position).getAvatar(), holder.editImageView);
        final int id = list.get(position).getId();
        holder.editImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditWebActivity.startEditActivity(context, id + "");
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView editImageView;

        public ViewHolder(View itemView) {
            super(itemView);
            editImageView = (ImageView) itemView.findViewById(R.id.editImageView);
        }
    }
}
