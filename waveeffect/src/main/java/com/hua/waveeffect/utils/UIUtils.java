package com.hua.waveeffect.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.view.WindowManager;

/**
 * Created by ZHONG WEI  HUA on 2016/3/4.
 */
public class UIUtils {

    public static DisplayMetrics getScreenDisplayMetrics(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        wm.getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public static int getScreenWidthPixels(Context context) {
        return getScreenDisplayMetrics(context).widthPixels;
    }

    public static int getScreenHeightPixels(Context context) {
        return getScreenDisplayMetrics(context).heightPixels;
    }

    public static float getScreenDensity(Context context) {
        try{
            return getScreenDisplayMetrics(context).density;
        }catch (Exception e) {
            return DisplayMetrics.DENSITY_DEFAULT;
        }
    }

    public static int dip2px(Context context, int dip) {
        return (int)(dip * getScreenDensity(context) + 0.5f);
    }
}
