package com.qianjia.zhihuribao.ui.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.qianjia.basemodel.view.BaseView;
import com.qianjia.zhihuribao.R;
import com.qianjia.zhihuribao.adapter.ThemesMenuAdapter;
import com.qianjia.zhihuribao.base.BaseFragment;
import com.qianjia.zhihuribao.bean.Theme;
import com.qianjia.zhihuribao.presenter.ThemesPresenter;
import com.qianjia.zhihuribao.ui.activity.MainActivity;

import butterknife.BindView;

/**
 * Created by Jiansion on 2017/3/20.
 * 栏目列表
 */

public class ThemeMenuFragment extends BaseFragment implements BaseView<Theme> {
    private static final String TAG = ThemeMenuFragment.class.getSimpleName();
    @BindView(R.id.mListView)
    ListView mListView;

    private View mHeardView;

    private TextView heardTextView;

    private ThemesPresenter presenter;

    private ThemesMenuAdapter adapter;

    private MainActivity activity;

    private ItemSelectListener listener;

    @Override
    protected int getLayoutId() {
        return R.layout.menu_listview;
    }

    @Override
    protected void initViews() {

        activity = (MainActivity) mActivity;
        listener = (ItemSelectListener) mActivity;

        mHeardView = LayoutInflater.from(activity).inflate(R.layout.menu_item, mListView, false);
        heardTextView = (TextView) mHeardView.findViewById(R.id.tv_menu);
        heardTextView.setText("首页");
        heardTextView.setTextColor(activity.getResources().getColor(R.color.colorPrimary));
        heardTextView.setOnClickListener(v -> {
            activity.closeMenu();
            listener.onItemSelectListener(-1, "首页");
        });

        mListView.addHeaderView(mHeardView);
        mListView.setDivider(null);
        mListView.setOnItemClickListener((parent, view, position, id) -> {
            Theme.OthersBean item = (Theme.OthersBean) adapter.getItem(position - 1);
            activity.closeMenu();
            listener.onItemSelectListener(item.getId(), item.getName());
        });

    }

    @Override
    protected void initData() {
        presenter = new ThemesPresenter(this);
        presenter.onGetThemes();

    }

    @Override
    public void onSuccess(Theme theme) {
        adapter = new ThemesMenuAdapter(theme.getOthers());
        mListView.setAdapter(adapter);
    }

    @Override
    public void onError(ErrorType type) {
    }


    public interface ItemSelectListener {
        void onItemSelectListener(int id, String title);
    }

}
