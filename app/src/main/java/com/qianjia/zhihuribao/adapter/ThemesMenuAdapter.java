package com.qianjia.zhihuribao.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.bean.Theme;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Jiansion on 2017/3/20.
 */

public class ThemesMenuAdapter extends BaseAdapter {


    private List<Theme.OthersBean> mList;

    public ThemesMenuAdapter(List<Theme.OthersBean> mList) {
        this.mList = mList;
    }

    @Override
    public int getCount() {
        return mList.size();
    }


    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    /**
     * 获取栏目ID
     *
     * @param position
     * @return 栏目ID
     */
    @Override
    public long getItemId(int position) {
        return mList.get(position).getId();
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
            vHolder = new ViewHolder(convertView);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) convertView.getTag();
        }
        vHolder.title.setText(mList.get(position).getName());
        return convertView;
    }


    static class ViewHolder {

        @BindView(R.id.tv_menu)
        TextView title;


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
