package me.donlis.common.util;

import android.content.Context;
import android.content.SharedPreferences;

public class ShareUtils {
    private static Context context;
    private static final String globalFileName = "GLOBAL";

    private ShareUtils(){
        throw new UnsupportedOperationException("can't instantiate");
    }

    public static void init(Context context){
        ShareUtils.context = context.getApplicationContext();
    }

    public static SharedPreferences getPreferences(){
        if(context != null){
            return context.getSharedPreferences(globalFileName, Context.MODE_PRIVATE);
        }
        throw new NullPointerException("should init first");
    }

    public static void apply(SharedPreferences.Editor editor){
        if(editor == null){
            return;
        }
        editor.apply();
    }

    public static void setStringValue(String key, String value){
        if(key == null || key.equals("")){
            return;
        }
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putString(key,value);
        apply(edit);
    }

    public static void setBoolValue(String key, boolean value){
        if(key == null || key.equals("")){
            return;
        }
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putBoolean(key,value);
        apply(edit);
    }

    public static void setIntValue(String key, int value){
        if(key == null || key.equals("")){
            return;
        }
        SharedPreferences.Editor edit = getPreferences().edit();
        edit.putInt(key,value);
        apply(edit);
    }

    public static String getStringValue(String key, String defaultValue){
        if(key == null || key.equals("")){
            return defaultValue;
        }
        return getPreferences().getString(key,defaultValue);
    }

    public static boolean getBoolValue(String key, boolean defaultValue){
        if(key == null || key.equals("")){
            return defaultValue;
        }
        return getPreferences().getBoolean(key,defaultValue);
    }

    public static int getIntValue(String key, int defaultValue){
        if(key == null || key.equals("")){
            return defaultValue;
        }
        return getPreferences().getInt(key,defaultValue);
    }

}
