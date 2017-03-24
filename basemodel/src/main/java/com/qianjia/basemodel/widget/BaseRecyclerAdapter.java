package com.qianjia.basemodel.widget;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Jiansion on 2017/2/20.
 * RecyclerView通用的Adapter
 */

public abstract class BaseRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected List<T> mList;
    protected LayoutInflater inflater;

    private View headView;
    private View footerView;

    protected final static int DEFAULT_TYPE = 1;//正常布局

    protected final static int HEAD_TYPE = 2;//头布局

    protected final static int FOOTER_TYPE = 3;//底部布局

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
        this.mList = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseRecyclerAdapter(Context context, List<T> datas) {
        if (datas == null) datas = new ArrayList<>();
        this.mContext = context;
        this.mList = datas;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public BaseRecyclerAdapter(Context context, T[] datas) {
        this.mContext = context;
        this.mList = new ArrayList<>();
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Collections.addAll(mList, datas);
    }

    public void setHeadView(View headView) {
        if (this.headView == null) {
            this.headView = headView;
            notifyItemInserted(0);
        }
    }

    public View getHeadView() {
        return headView == null ? null : headView;
    }

    public void setFooterView(View footerView) {
        if (this.footerView != null && getItemCount() > 0) {
            this.footerView = footerView;
            notifyItemInserted(getItemCount() - 1);
        }
    }

    public View getFooterView() {
        return footerView == null ? null : footerView;
    }

    @Override
    public int getItemViewType(int position) {
        if (headView != null && position == 0) {
            return HEAD_TYPE;
        }
        if (footerView != null && position == getItemCount() - 1) {
            return FOOTER_TYPE;
        }

        return DEFAULT_TYPE;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        if (getItemViewType(position) == DEFAULT_TYPE) {
            final int pos = headView == null ? position : position - 1;
            onViewSetting(holder, pos);
        }
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == HEAD_TYPE && headView != null) {
            return setHeadFooterView(parent, viewType);
        }
        if (viewType == FOOTER_TYPE && footerView != null) {
            return setHeadFooterView(parent, viewType);
        }
        return setDefaultView(parent, viewType);
    }

    protected VH setHeadFooterView(ViewGroup parent, int viewType) {
        return null;
    }

    //获取布局
    protected abstract VH setDefaultView(ViewGroup parent, int viewType);

    //绑定数据
    protected abstract void onViewSetting(VH holder, int pos);


    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    /**
     * 更新数据，替换原有数据
     */
    public void updateItems(List<T> items) {
        mList = items;
        notifyDataSetChanged();
    }

    /**
     * 插入一条数据
     * 往头部插入一条数据
     */
    public void addItem(T item) {
        mList.add(0, item);
        if (headView != null) {
            notifyItemInserted(1);
        } else {
            notifyItemInserted(0);
        }
    }

    /**
     * 插入一条数据
     */
    public void addItem(T item, int position) {
        position = Math.min(position, mList.size());
        mList.add(position, item);
        notifyItemInserted(position);
    }

    /**
     * 在列表尾添加一串数据
     */
    public void addItems(List<T> items) {
        int start = getHeadView() == null ? mList.size() : mList.size() + 1;
        mList.addAll(items);
        notifyItemRangeChanged(start, items.size());

    }

    /**
     * 移除一条数据
     */
    public void removeItem(int position) {
        if (headView == null && position > mList.size() - 1) return;

        if (headView != null && position > mList.size()) return;

        if (headView == null) {
            mList.remove(position);
            notifyItemRemoved(position);
        } else {
            mList.remove(position + 1);
            notifyItemRemoved(position + 1);
        }

    }

    /**
     * 清除所有数据
     */
    public void removeAllItems() {
        mList.clear();
        notifyDataSetChanged();
    }


}
