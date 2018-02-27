package com.custom.emptylayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by szhangbiao on 2018/2/22.
 */

public class LinearEmptyLayout extends LinearLayout{

    private LoadingView mLoadingView;
    private TextView mTitleTextView;
    private TextView mDetailTextView;
    protected Button mButton;

    private List<View> contentViews = new ArrayList<>();

    public LinearEmptyLayout (Context context) {
        this(context, null);
    }

    public LinearEmptyLayout (Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LinearEmptyLayout (Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER);
    }

    @Override
    public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getId()!=R.id.empty_view_loading&&child.getId()!=R.id.empty_view_title&&child.getId()!=R.id.empty_view_detail&&child.getId()!=R.id.empty_view_button){
            contentViews.add(child);
        }
    }

    private void init () {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_empty_view, this, true);
        mLoadingView = findViewById(R.id.empty_view_loading);
        mTitleTextView = findViewById(R.id.empty_view_title);
        mDetailTextView = findViewById(R.id.empty_view_detail);
        mButton = findViewById(R.id.empty_view_button);
    }

    @Override
    protected void onFinishInflate () {
        super.onFinishInflate();
        init();
        showNormal();
    }

    /**
     * 显示emptyView
     *
     * @param loading 是否要显示loading
     * @param titleText 标题的文字，不需要则传null
     * @param detailText 详情文字，不需要则传null
     * @param buttonText 按钮的文字，不需要按钮则传null
     * @param onButtonClickListener 按钮的onClick监听，不需要则传null
     */
    public void showStatus (boolean loading, String titleText, String detailText, String buttonText, OnClickListener onButtonClickListener) {
        setLoadingShowing(loading);
        setTitleText(titleText);
        setDetailText(detailText);
        setButton(buttonText, onButtonClickListener);

        setContentVisibility(!loading&& TextUtils.isEmpty(titleText)&&TextUtils.isEmpty(buttonText)&&onButtonClickListener==null);
    }

    /**
     * 用于显示emptyView并且只显示loading的情况，此时title、detail、button都被隐藏
     *
     * @param loading 是否显示loading
     */
    public void showStatus(boolean loading) {
        showStatus(loading,null,null,null,null);
    }

    /**
     * 用于显示纯文本的简单调用方法，此时loading、button均被隐藏
     *
     * @param titleText 标题的文字，不需要则传null
     * @param detailText 详情文字，不需要则传null
     */
    public void showStatus (String titleText, String detailText) {
        showStatus(false,titleText,detailText,null,null);
    }

    /**
     * 显示正常的view
     */
    public void showNormal(){
        hide();
        setContentVisibility(true);
    }
    /**
     * 根据 visible 控制View的显示
     * @param visible
     */
    private void setContentVisibility(boolean visible) {
        for (View v : contentViews) {
            v.setVisibility(visible ? View.VISIBLE : View.GONE);
        }
    }

    /**
     * 隐藏emptyView
     */
    public void hide () {
        setLoadingShowing(false);
        setTitleText(null);
        setDetailText(null);
        setButton(null, null);
    }

    public boolean isShowing () {
        return getVisibility() == VISIBLE;
    }

    public boolean isLoading () {
        return mLoadingView.getVisibility() == VISIBLE;
    }

    public void setLoadingShowing (boolean show) {
        mLoadingView.setVisibility(show ? VISIBLE : GONE);
    }

    public void setTitleText (String text) {
        mTitleTextView.setText(text);
        mTitleTextView.setVisibility(text != null ? VISIBLE : GONE);
    }

    public void setDetailText (String text) {
        mDetailTextView.setText(text);
        mDetailTextView.setVisibility(text != null ? VISIBLE : GONE);
    }

    public void setTitleColor (int color) {
        mTitleTextView.setTextColor(color);
    }

    public void setDetailColor (int color) {
        mDetailTextView.setTextColor(color);
    }

    public void setButton (String text, OnClickListener onClickListener) {
        mButton.setText(text);
        mButton.setVisibility(text != null ? VISIBLE : GONE);
        mButton.setOnClickListener(onClickListener);
    }
}
