package com.qianjia.statuslayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AnimRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qianjia.dialog.ProgressWheel;

/**
 * Created by Jiansion on 2017/3/8.
 * View状态变化视图,有loading视图,error视图,empty视图
 */

public class StatusLayout extends LinearLayout {

    private static final String MSG_ONE_CHILD = "只能有一个子控件";
    //默认的切换动画开启
    private static final boolean DEFAULT_AND_ENABLED = true;
    //默认的入场动画
    private static final int DEFAULT_IN_ANIM = android.R.anim.fade_in;
    //默认的出场动画
    private static final int DEFAULT_OUT_ANIM = android.R.anim.fade_out;

    private boolean animationEnabled;
    @AnimRes
    private int inAnimation;
    @AnimRes
    private int outAnimation;

    private View content;

    private LinearLayout stContainer;
    private ProgressBar stProgress;
    private ImageView stImage;
    private TextView stMessage;
    private Button stButton;

    public StatusLayout(Context context) {
        this(context, null);
    }

    public StatusLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.StatusLayout, 0, 0);
        animationEnabled = a.getBoolean(R.styleable.StatusLayout_stfAnimationEnabled, DEFAULT_AND_ENABLED);
        inAnimation = a.getResourceId(R.styleable.StatusLayout_stfInAnimation, DEFAULT_IN_ANIM);
        outAnimation = a.getResourceId(R.styleable.StatusLayout_setOutAnimation, DEFAULT_OUT_ANIM);
        a.recycle();
    }

    /**
     * 用于判断是否开启切换动画
     *
     * @return AnimationEnabled
     */
    public boolean isAnimationEnabled() {
        return animationEnabled;
    }

    /**
     * @param animationEnabled 是否开启动画,默认开启动画
     */
    public void setAnimationEnabled(boolean animationEnabled) {
        this.animationEnabled = animationEnabled;
    }

    @AnimRes
    public int getInAnimation() {
        return inAnimation;
    }

    /**
     * 设置迁入动画
     *
     * @param inAnimation
     */
    public void setInAnimation(int inAnimation) {
        this.inAnimation = inAnimation;
    }

    @AnimRes
    public int getOutAnimation() {
        return outAnimation;
    }

    /**
     * 设置迁出动画
     *
     * @param outAnimation
     */
    public void setOutAnimation(int outAnimation) {
        this.outAnimation = outAnimation;
    }

    @Override//所有子视图被添加之后回调
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 1) throw new IllegalStateException(MSG_ONE_CHILD);

        if (isInEditMode()) return;//判断视图是否处于编辑模式,隐藏状态View
        setOrientation(VERTICAL);//视图垂直摆放
        content = getChildAt(0);//获取主视图,即正常时的内容视图

        //将状态视图添加到布局中
        LayoutInflater.from(getContext()).inflate(R.layout.status_layout, this, true);
        stContainer = (LinearLayout) findViewById(R.id.stContainer);
        stProgress = (ProgressBar) findViewById(R.id.stProgress);
        stImage = (ImageView) findViewById(R.id.stImage);
        stMessage = (TextView) findViewById(R.id.stMessage);
        stButton = (Button) findViewById(R.id.stButton);
    }

    /**
     * 显示内容视图
     */
    public void showContent() {
        if (isAnimationEnabled() && stContainer.getVisibility() == VISIBLE) {
            stContainer.clearAnimation();
            content.clearAnimation();
            Animation outAnim = createOutAnimation();
            stContainer.startAnimation(outAnim);
            stContainer.getAnimation().setAnimationListener(new CustomAnimationListener() {
                @Override
                public void onAnimationEnd(Animation animation) {
                    super.onAnimationEnd(animation);
                    stContainer.setVisibility(GONE);
                    content.setVisibility(VISIBLE);
                    content.startAnimation(createInAnimation());
                }
            });
        } else {
            stContainer.setVisibility(GONE);
            content.setVisibility(VISIBLE);
        }
    }

    /**
     * loading
     * showLoadingView
     */
    public void showLoading() {
        showLoading(R.string.stfLoadingMessage);
    }

    public void showLoading(@StringRes int resId) {
        showLoading(str(resId));
    }

    public void showLoading(String message) {
        showCustom(new CustomStateOptions()
                .message(message)
                .loading()
        );
    }

    /**
     * empty
     * showEmptyView
     */
    public void showEmpty() {
        showEmpty(R.string.stfEmptyMessage);
    }

    public void showEmpty(@StringRes int resId) {
        showEmpty(str(resId));
    }

    public void showEmpty(String message) {
        showCustom(new CustomStateOptions()
                .message(message)
                .image(R.drawable.stf_ic_empty)
        );
    }

    /**
     * error
     * showErrorView
     */
    public void showError(OnClickListener listener) {
        showError(R.string.stfErrorMessage, listener);
    }

    public void showError(@StringRes int resId, OnClickListener listener) {
        showError(str(resId), listener);
    }

    public void showError(String message, OnClickListener listener) {
        showCustom(new CustomStateOptions()
                .message(message)
                .image(R.drawable.stf_ic_error)
                .buttonClickListener(listener)
                .buttonText(str(R.string.stfButtonText))
        );

    }

    /**
     * 如果内容视图显示,先隐藏内容视图,后显示自定义视图
     *
     * @param options o
     */
    private void showCustom(final CustomStateOptions options) {
//        stContainer.clearAnimation();
//        content.clearAnimation();
//        if (isAnimationEnabled() && content.getVisibility() == VISIBLE) {
//            Animation outAnimation = createOutAnimation();
//            content.startAnimation(outAnimation);
//            content.getAnimation().setAnimationListener(new CustomAnimationListener() {
//                @Override
//                public void onAnimationEnd(Animation animation) {
//                    super.onAnimationEnd(animation);
//                    content.setVisibility(GONE);
//                    stContainer.setVisibility(VISIBLE);
//                    state(options);
//                    stContainer.startAnimation(createInAnimation());
//                }
//            });
//        } else {
        content.setVisibility(GONE);
        stContainer.setVisibility(VISIBLE);
        state(options);
//        }
    }

    private void state(CustomStateOptions options) {
        if (!TextUtils.isEmpty(options.getMessage())) {
            stMessage.setVisibility(VISIBLE);
            stMessage.setText(options.getMessage());
        } else {
            stMessage.setVisibility(GONE);
        }

        if (options.isLoading()) {
            stProgress.setVisibility(VISIBLE);
            stImage.setVisibility(GONE);
            stButton.setVisibility(GONE);
        } else {
            stProgress.setVisibility(GONE);
            if (options.getImageRes() != 0) {
                stImage.setVisibility(VISIBLE);
                stImage.setImageResource(options.getImageRes());
            } else {
                stImage.setVisibility(GONE);
            }

            if (options.getBtnClickListener() != null) {
                stButton.setVisibility(VISIBLE);
                stButton.setOnClickListener(options.getBtnClickListener());
                if (!TextUtils.isEmpty(options.getButtonText())) {
                    stButton.setText(options.getButtonText());
                }
            } else {
                stButton.setVisibility(GONE);
            }
        }


    }


    @NonNull
    private String str(@StringRes int resId) {
        return getContext().getString(resId);
    }

    private Animation createInAnimation() {
        return AnimationUtils.loadAnimation(getContext(), inAnimation);
    }

    //先出后进,当迁出动画执行完毕时再执行迁入动画
    private Animation createOutAnimation() {
        return AnimationUtils.loadAnimation(getContext(), outAnimation);
    }

}
