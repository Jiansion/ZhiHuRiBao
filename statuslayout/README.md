## 类库的集合

- ### SweetAlertDialog (炫酷的提示 Dialog)
 使用方法如下
 https://github.com/pedant/sweet-alert-dialog/blob/master/README.md
 
``` stylus
  // 加载普通的 LoadingDialog
  SweetAlertDialog pDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
  pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
  pDialog.setTitleText("Loading");
  pDialog.setCancelable(false);
  pDialog.show();
```

- ### SwipeRefreshLayout (自定义的上下拉刷新控件)

使用方法与 support v4 lib 下的 SwipeRefreshLayout 一样,就是添加多了一个上拉加载更多的回调方法
``` stylus
// 设置拖动模式( 可以设置上下拉动,或禁止拉动)
mSwipeRefresh.setMode(SwipeRefreshLayout.Mode.BOTH);

//下拉刷新
mSwipeRefresh.setOnRefreshListener();

//上拉加载更多
mSwipeRefresh.setOnPullUpRefreshListener();
```

- ### StatusLayout (自定义的一个ViewGroup,用于显示页面多状态布局)
- StatusLayout 只能有一个直接的子控件
```  stylus
//显示 Loading 状态
mStatusLayout.showLoading();

 //显示空视图状态
 mStatusLayout.showEmpty(empty);
 
 //显示正常视图状态
 mStatusLayout.showContent();
 
 //显示发生错误时 的页面状态
 mStatusLayout.showError( err , OnclickListener);
```
