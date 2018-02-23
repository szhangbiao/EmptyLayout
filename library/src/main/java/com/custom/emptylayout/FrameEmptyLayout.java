package com.custom.emptylayout;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by szhangbiao on 2018/2/22.
 */

public class FrameEmptyLayout extends FrameLayout{
    private static final String TAG_LOADING = "EmptyOrNetErrorLayout.TAG_LOADING";
    private static final String TAG_EMPTY = "EmptyOrNetErrorLayout.TAG_EMPTY";
    private static final String TAG_ERROR = "EmptyOrNetErrorLayout.TAG_ERROR";

    private static final int STATUS_NORMAL = 1;
    private static final int STATUS_LOADING = 2;
    private static final int STATUS_EMPTY = 3;
    private static final int STATUS_ERROR = 4;

    //正在加载时显示的view
    private LoadingView loadingView;
    //空数据时显示的View
    private View emptyView;
    private ImageView emptyImageView;
    private TextView emptyTextView;

    //网络加载错误时显示的View
    private View errorView;
    private ImageView errorImageView;
    private TextView errorTextView;
    private Button errorRetry;

    private int mLayoutState=STATUS_NORMAL;
    private LayoutInflater inflater;
    private LayoutParams layoutParams;
    //正常加载时显示的view
    //private View normalView;
    private List<View> contentViews = new ArrayList<>();

    private int errorImgId;
    private String errorMessage;
    private String errorBtnMessage;

    private int emptyImgId;
    private String emptyMessage;

    private OnRetryClickListener retryListener;

    public FrameEmptyLayout(Context context) {
        this(context,null);
    }

    public FrameEmptyLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FrameEmptyLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.FrameEmptyLayout);
        //Error state attrs
        errorImgId = arr.getResourceId(R.styleable.FrameEmptyLayout_error_image, -1);
        errorMessage = arr.getString(R.styleable.FrameEmptyLayout_error_text);
        errorBtnMessage = arr.getString(R.styleable.FrameEmptyLayout_error_retry_text);
        //Empty state attrs
        emptyImgId = arr.getResourceId(R.styleable.FrameEmptyLayout_empty_image,-1);
        emptyMessage = arr.getString(R.styleable.FrameEmptyLayout_empty_text);
        arr.recycle();
    }

    @Override
    public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null || (!child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR)&&!child.getTag().equals(TAG_LOADING))){
            contentViews.add(child);
        }
    }
    /**
     * Hide all other states and show loading
     */
    public void showLoading() {
        switchState(STATUS_LOADING, 0, null, null, null);
    }
    /**
     * Hide all other states and show loading
     */
    public void showLoading(List<Integer> skipIds) {
        switchState(STATUS_LOADING, 0, null, null, skipIds);
    }

    /**
     * Hide all other states and show content
     */
    public void showContent() {
        switchState(STATUS_NORMAL, 0, null, null, null);
    }

    /**
     * Hide all other states and show content
     */
    public void showContent(List<Integer> skipIds) {
        switchState(STATUS_NORMAL, 0, null, null, skipIds);
    }

    /**
     * Show empty view when there are not data to show
     */
    public void showEmpty() {
        switchState(STATUS_EMPTY, 0, null, null, null);
    }

    /**
     * Show empty view when there are not data to show
     */
    public void showEmpty(@DrawableRes int emptyResId, String emptyTextTitle) {
        switchState(STATUS_EMPTY, emptyResId, emptyTextTitle, null, null);
    }
    /**
     * Show empty view when there are not data to show
     */
    public void showEmpty(@DrawableRes int emptyResId, String emptyTextTitle,List<Integer> skipIds) {
        switchState(STATUS_EMPTY, emptyResId, emptyTextTitle, null, skipIds);
    }
    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     */
    public void showError() {
        switchState(STATUS_ERROR, 0, null, null,null);
    }

    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     */
    public void showError(int emptyResId, String errorTitle, String retryText) {
        switchState(STATUS_ERROR, emptyResId, errorTitle, retryText, null);
    }
    /**
     * Show error view with a button when something goes wrong and prompting the user to try again
     */
    public void showError(int emptyResId, String errorTitle, String retryText,List<Integer> skipIds) {
        switchState(STATUS_ERROR, emptyResId, errorTitle, retryText, skipIds);
    }

    /**
     * Check if normal is shown
     *
     * @return boolean
     */
    public boolean isNormalShow() {
        return mLayoutState == STATUS_NORMAL;
    }

    /**
     * Check if empty state is shown
     *
     * @return boolean
     */
    public boolean isDataEmpty() {
        return mLayoutState == STATUS_EMPTY;
    }

    /**
     * Check if error state is shown
     *
     * @return boolean
     */
    public boolean isNetError() {
        return mLayoutState == STATUS_ERROR;
    }
    /**
     * Check if loading state is shown
     *
     * @return boolean
     */
    public boolean isShowLoading() {
        return mLayoutState == STATUS_LOADING;
    }


    private void switchState(int layoutState, int resImgId, String txtTip, String contentTip, List<Integer> skipIds) {
        this.mLayoutState = layoutState;
        switch (mLayoutState) {
            case STATUS_NORMAL:
                //Hide all state views to display content
                hideEmptyView();
                hideErrorView();
                hideLoadingView();
                setContentVisibility(true, skipIds);
                break;
            case STATUS_LOADING:
                hideEmptyView();
                hideErrorView();
                setLoadingView();
                setContentVisibility(false, skipIds);
                break;
            case STATUS_EMPTY:
                hideErrorView();
                setEmptyView(resImgId, txtTip);
                hideLoadingView();
                setContentVisibility(false, skipIds);
                break;
            case STATUS_ERROR:
                hideEmptyView();
                setErrorView(resImgId, txtTip, contentTip);
                hideLoadingView();
                setContentVisibility(false, skipIds);
                break;
        }
    }

    /**
     * 正在加载显示的view
     */
    private void setLoadingView() {
        if(loadingView == null){
            loadingView = (LoadingView) inflater.inflate(R.layout.layout_loading, this,false);
            loadingView.setTag(TAG_LOADING);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;
            addView(loadingView, layoutParams);
        }else{
            loadingView.setVisibility(VISIBLE);
        }
    }
    /**
     * 显示空数据view
     * @param resImgId
     * @param txtTip
     */
    private void setEmptyView(int resImgId, String txtTip) {
        if (emptyView == null) {
            emptyView = inflater.inflate(R.layout.layout_empty, this,false);
            emptyView.setTag(TAG_EMPTY);
            emptyImageView = emptyView.findViewById(R.id.iv_empty_icon);
            emptyTextView = emptyView.findViewById(R.id.tv_empty_text);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;

            addView(emptyView, layoutParams);
        } else {
            emptyView.setVisibility(VISIBLE);
        }
        if (resImgId >0&& emptyImageView != null) {
            emptyImageView.setImageResource(resImgId);
        } else if (emptyImgId >0 && emptyImageView != null) {
            emptyImageView.setImageResource(emptyImgId);
        }

        if (!TextUtils.isEmpty(txtTip) && emptyTextView != null) {
            emptyTextView.setText(txtTip);
        } else if (!TextUtils.isEmpty(emptyMessage) && emptyTextView != null) {
            emptyTextView.setText(emptyMessage);
        }
    }

    /**
     * 显示出错View
     * @param resImgId
     * @param txtTip
     * @param contentTip
     */
    private void setErrorView(int resImgId, String txtTip, String contentTip) {
        if (errorView == null) {
            errorView = inflater.inflate(R.layout.layout_error, this, false);
            errorView.setTag(TAG_ERROR);

            errorImageView = errorView.findViewById(R.id.iv_error_icon);
            errorTextView = errorView.findViewById(R.id.tv_error_text);
            errorRetry = errorView.findViewById(R.id.tv_error_retry);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            layoutParams.gravity = Gravity.CENTER;

            addView(errorView, layoutParams);
        } else {
            errorView.setVisibility(VISIBLE);
        }

        if (resImgId >0 && errorImageView != null) {
            errorImageView.setImageResource(resImgId);
        } else if (errorImgId >0 && errorImageView != null) {
            errorImageView.setImageResource(errorImgId);
        }

        if (!TextUtils.isEmpty(txtTip) && errorTextView != null) {
            errorTextView.setText(txtTip);
        } else if (!TextUtils.isEmpty(errorMessage) && errorTextView != null) {
            errorTextView.setText(errorMessage);
        }

        if (!TextUtils.isEmpty(contentTip)&& errorRetry != null) {
            errorRetry.setVisibility(View.VISIBLE);
            errorRetry.setText(contentTip);
        } else if (!TextUtils.isEmpty(errorBtnMessage)&& errorRetry != null){
            errorRetry.setVisibility(View.VISIBLE);
            errorRetry.setText(errorBtnMessage);
        }else{
            if(errorRetry!=null){
                errorRetry.setVisibility(View.GONE);
            }
        }
        if(errorRetry!=null){
            errorRetry.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(retryListener!=null){
                        retryListener.onClick();
                    }
                }
            });
        }
    }

    /**
     * 根据 visible 控制View的显示
     * @param visible
     * @param skipIds
     */
    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        if(skipIds==null){
            skipIds= Collections.emptyList();
        }
        for (View v : contentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }

    /**
     * 隐藏空数据view
     */
    private void hideEmptyView() {
        if (emptyView != null) {
            emptyView.setVisibility(GONE);
        }
    }

    /**
     * 隐藏加载出错view
     */
    private void hideErrorView() {
        if (errorView != null) {
            errorView.setVisibility(GONE);
        }
    }

    /**
     * 网络请求后隐藏loadingView
     */
    private void hideLoadingView() {
        if (loadingView != null) {
            loadingView.setVisibility(GONE);
        }
    }

    public void setRetryListener(OnRetryClickListener retryListener) {
        this.retryListener = retryListener;
    }

    public interface OnRetryClickListener {
        void onClick();
    }
}
