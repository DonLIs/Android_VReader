package me.donlis.common.util;

import android.content.Context;

public class Utils {

    private static Context mContext;

    private Utils(){
        throw new UnsupportedOperationException("can't instantiate");
    }

    public static void init(Context context){
        Utils.mContext = context.getApplicationContext();
    }

    public static Context getContext(){
        if(mContext != null) return mContext;
        throw new NullPointerException("should init first");
    }

    public static int dip2px(float dp) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

}
