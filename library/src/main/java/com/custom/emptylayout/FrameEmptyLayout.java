package com.custom.emptylayout;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import java.util.ArrayList;
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

    //正常加载时显示的view
    //private View normalView;
    private List<View> contentViews = new ArrayList<>();

    private boolean showLoading;
    private int errorImgId;
    private String errorMessage;
    private String errorBtnMessage;

    private int emptyImgId;
    private String emptyMessage;

    public FrameEmptyLayout(Context context) {
        this(context,null);
    }

    public FrameEmptyLayout(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public FrameEmptyLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.EmptyLayout);
        showLoading = arr.getBoolean(R.styleable.EmptyLayout_show_loading,false);
        //Error state attrs
        errorImgId = arr.getResourceId(R.styleable.EmptyLayout_error_image, -1);
        errorMessage = arr.getString(R.styleable.EmptyLayout_error_text);
        errorBtnMessage = arr.getString(R.styleable.EmptyLayout_error_retry_text);
        //Empty state attrs
        emptyImgId = arr.getResourceId(R.styleable.EmptyLayout_empty_image,-1);
        emptyMessage = arr.getString(R.styleable.EmptyLayout_empty_text);
        arr.recycle();
    }

    @Override
    public void addView(@NonNull View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        if (child.getTag() == null || (!child.getTag().equals(TAG_EMPTY) && !child.getTag().equals(TAG_ERROR)&&!child.getTag().equals(TAG_LOADING))){
            contentViews.add(child);
        }
    }
}
