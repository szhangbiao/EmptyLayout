package com.custom.emptylayout;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by szhangbiao on 2018/2/22.
 */

public class DisplayUtil {
    /**
     * 屏幕密度
     */
    public static float sDensity = 0f;
    /**
     * 单位转换: dp -> px
     */
    public static int dp2px(Context context, int dp) {
        return (int) (getDensity(context) * dp + 0.5);
    }

    public static float getDensity(Context context) {
        if (sDensity == 0f) {
            sDensity = getDisplayMetrics(context).density;
        }
        return sDensity;
    }
    /**
     * 获取 DisplayMetrics
     */
    public static DisplayMetrics getDisplayMetrics(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) context.getApplicationContext().getSystemService(Context.WINDOW_SERVICE))
            .getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }
}
