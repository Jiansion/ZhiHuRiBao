package com.qianjia.zhihuribao.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.adapter.ThemesMenuAdapter;
import com.qianjia.zhihuribao.base.BaseFragment;
import com.qianjia.zhihuribao.bean.Theme;
import com.qianjia.zhihuribao.presenter.ThemesPresenter;
import com.qianjia.zhihuribao.ui.activity.MainActivity;
import com.qianjia.zhihuribao.ui.view.BaseView;
import com.qianjia.zhihuribao.util.LogUtil;
import com.qianjia.zhihuribao.util.ToastUtil;

import butterknife.BindView;

/**
 * Created by Jiansion on 2017/3/20.
 * 栏目列表
 */

public class ThemeMenuFragment extends BaseFragment implements BaseView<Theme> {
    private static final String TAG = ThemeMenuFragment.class.getSimpleName();
    @BindView(R.id.mListView)
    ListView mListView;


    private ThemesMenuAdapter adapter;

    private MainActivity activity;

    private ItemSelectListener listener;

    public View selectView;


    @Override
    protected int getLayoutId() {
        return R.layout.menu_listview;
    }

    @Override
    protected void initViews() {

        activity = (MainActivity) mActivity;
        listener = (ItemSelectListener) mActivity;

        View mHeardView = LayoutInflater.from(activity).inflate(R.layout.menu_item, mListView, false);
        TextView heardTextView = (TextView) mHeardView.findViewById(R.id.tv_menu);
        heardTextView.setText("首页");
        heardTextView.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        heardTextView.setOnClickListener(v -> {
            activity.closeMenu();
            listener.onItemSelectListener(-1, "首页");
            if (selectView != null) {
                selectView.setBackgroundColor(0xFFFFFF);
            }
        });

        mListView.addHeaderView(mHeardView);
        mListView.setDivider(null);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            LogUtil.e(TAG, "当前选中" + position);
            Theme.OthersBean item = (Theme.OthersBean) adapter.getItem(position - 1);
            activity.closeMenu();
            listener.onItemSelectListener(item.getId(), item.getName());
            if (selectView != null) {
                selectView.setBackgroundColor(0xFFFFFF);
            }
            view.setBackgroundColor(0xd8979595);
            selectView = view;

        });
    }

    @Override
    protected void initData() {
        ThemesPresenter presenter = new ThemesPresenter(this);
        presenter.onGetThemes();

    }

    @Override
    public void onSuccess(Theme theme) {
        adapter = new ThemesMenuAdapter(theme.getOthers());
        mListView.setAdapter(adapter);
    }

    @Override
    public void onFail(String string) {
        ToastUtil.showToast(string);
    }

    public interface ItemSelectListener {
        void onItemSelectListener(int id, String title);
    }

}
