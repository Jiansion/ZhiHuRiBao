package com.f8boss.zhihuribao.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.f8boss.zhihuribao.R;
import com.f8boss.zhihuribao.activity.WebContentActivity;
import com.f8boss.zhihuribao.bean.IndextItemBean;
import com.f8boss.zhihuribao.util.ImageLoaderUtil;
import com.f8boss.zhihuribao.util.OkHttpUtils;
import com.f8boss.zhihuribao.util.Urls;
import com.f8boss.zhihuribao.widget.BorderScrollView;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jiansion on 2016/5/25.
 * 首页
 */
public class FragmentIndext extends BaseFragment {

    @Bind(R.id.mSwipeRefreshLayout)
    SwipeRefreshLayout mSwipeRefreshLayout;


    @Bind(R.id.mBorderScrollView)
    BorderScrollView mBorderScrollView;

    @Bind(R.id.mRollPageView)
    RollPagerView mRollpageView;

    @Bind(R.id.mRecyclerView)
    RecyclerView mRecyclerView;

    private Gson gson;

    private int beforeData;


    //用于存放首页数据的个Item项
    private List<IndextItemBean.StoriesBean> itemList;
    //轮播的图片数据
    private List<IndextItemBean.TopStoriesBean> headerList;

    private String TAG = "FragmentIndext";
    private RecyclerAdapter recyclerAdapter;
    private RollPageViewAdapter rollPageViewAdapter;


    //初始化视图
    @Override
    public View initViews(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index, container, false);
        ButterKnife.bind(this, view);
        initRecyclerView();
        initObject();
        initBorderScrollView();
        initSwipeRefreshLayout();
        return view;
    }

    //初始化数据
    @Override
    public void initData(Bundle savedInstanceState) {
        downFirstData();
    }

    private void initSwipeRefreshLayout() {
        //设置一组刷新过程中的颜色
        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.GREEN, Color.BLUE);

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                downFirstData();
                //更新结束
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


    }


    //BorderScrollView的滚动到底部的监听,用于加载更多数据
    private void initBorderScrollView() {
        mBorderScrollView.setOnBorderListener(new BorderScrollView.OnBorderListener() {
            @Override
            public void onBottom() {
//                Toast.makeText(mActivity, "已经滑动到底部了", Toast.LENGTH_SHORT).show();
                OkHttpUtils.onGetToDownLoadData(mActivity, Urls.ZHIHUBEFORE + (beforeData - 1), OkHttpUtils.TYPE_TEXT, new OkHttpUtils.OnCallBack() {
                    @Override
                    public void callBackUIString(String data) {
                        IndextItemBean indextItemBean = gson.fromJson(data, IndextItemBean.class);
//                        Log.e(TAG, "callBackUIString: " + indextItemBean.getDate());
                        beforeData = new Integer(indextItemBean.getDate());
//                        TextView textView = new TextView(mActivity);
//                        textView.setText(beforeData + "");
//                        textView.setTextColor(Color.DKGRAY);
//                        textView.setPadding(15, 5, 5, 5);
//                        mRecyclerView.addView(textView, itemList.size()-1);

                        itemList.addAll(indextItemBean.getStories());
                        recyclerAdapter.notifyDataSetChanged();


                    }

                    @Override
                    public void callBackUIByte(byte[] datas) {

                    }
                });

            }

            @Override
            public void onTop() {
//                Toast.makeText(mActivity, "已经拉动到顶部了", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //初始化RecyclerView,并设置RecyclerView的布局管理器
    private void initRecyclerView() {
        //When set to true, layouts from end to start.默认为false
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false) {
            //拦截RecyclerView的滑动事件，改变他的布局管理器
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        };

        mRecyclerView.setLayoutManager(linearLayoutManager);


    }

    //初始化各种对象
    private void initObject() {
        gson = new Gson();
        itemList = new ArrayList<>();
        headerList = new ArrayList<>();
        mRollpageView.setHintView(new ColorPointHintView(mActivity, Color.WHITE, mActivity.getResources().getColor(R.color.colorGray)));

    }


    //下载首页的数据,或刷新数据
    private void downFirstData() {
        OkHttpUtils.onGetToDownLoadData(mActivity, Urls.ZHIHUNEWS, OkHttpUtils.TYPE_TEXT, new OkHttpUtils.OnCallBack() {

            @Override
            public void callBackUIString(String data) {

                IndextItemBean indextItemBean = gson.fromJson(data, IndextItemBean.class);
                String date = indextItemBean.getDate();
                beforeData = new Integer(date);
                itemList.clear();
                headerList.clear();
                itemList.addAll(indextItemBean.getStories());
                headerList.addAll(indextItemBean.getTop_stories());
                if (rollPageViewAdapter == null) {
                    rollPageViewAdapter = new RollPageViewAdapter(headerList);
                    mRollpageView.setAdapter(rollPageViewAdapter);
                } else {
                    rollPageViewAdapter.notifyDataSetChanged();
                }
                if (recyclerAdapter == null) {
                    recyclerAdapter = new RecyclerAdapter(itemList);
                    mRecyclerView.setAdapter(recyclerAdapter);
                } else {
                    recyclerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void callBackUIByte(byte[] datas) {

            }
        });
    }


    class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private List<IndextItemBean.StoriesBean> mList;

        public RecyclerAdapter(List<IndextItemBean.StoriesBean> mList) {
            this.mList = mList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            View view = LayoutInflater.from(mActivity).inflate(R.layout.item_indext_recycler, parent, false);

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final IndextItemBean.StoriesBean storiesBean = mList.get(position);
            holder.tvTitle.setText(storiesBean.getTitle());
            holder.tvTitle.setTextColor(Color.BLACK);
            ImageLoaderUtil.displayImage(storiesBean.getImages().get(0), holder.imageContentIcon);
            holder.linearItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mActivity, storiesBean.getId() + storiesBean.getTitle(), Toast.LENGTH_SHORT).show();

                    WebContentActivity.startAction(mActivity, storiesBean.getId() + "");

                }
            });

        }

        @Override
        public int getItemCount() {
            return mList.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageContentIcon;
            private TextView tvTitle;
            private LinearLayout linearItem;

            public ViewHolder(View itemView) {
                super(itemView);

                imageContentIcon = ((ImageView) itemView.findViewById(R.id.imageContentIcon));
                tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
                linearItem = (LinearLayout) itemView.findViewById(R.id.linearItem);

            }
        }
    }


    class RollPageViewAdapter extends StaticPagerAdapter {

        private List<IndextItemBean.TopStoriesBean> list;

        public RollPageViewAdapter(List<IndextItemBean.TopStoriesBean> list) {
            this.list = list;
        }

        @Override
        public View getView(ViewGroup container, final int position) {

            RelativeLayout relativeLayout = new RelativeLayout(mActivity);
            ImageView imageView = new ImageView(mActivity);
            TextView tvTitle = new TextView(mActivity);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            tvTitle.setTextSize(23);
            tvTitle.setTextColor(Color.WHITE);
            tvTitle.setPadding(10, 10, 10, 10);
            RelativeLayout.LayoutParams lP1 = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lP1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            lP1.setMargins(15, 10, 15, 30);
            relativeLayout.addView(imageView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            relativeLayout.addView(tvTitle, lP1);

            tvTitle.setText(list.get(position).getTitle());
            ImageLoaderUtil.loadImage(list.get(position).getImage(), imageView);


            //事件点击处理
            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(mActivity, list.get(position).getId() + ":" + (position + 1), Toast.LENGTH_SHORT).show();

                    WebContentActivity.startAction(mActivity, list.get(position).getId() + "");
                }
            });


            return relativeLayout;
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

}
