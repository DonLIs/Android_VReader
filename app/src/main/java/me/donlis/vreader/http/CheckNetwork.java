package me.donlis.vreader.http;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CheckNetwork {

    private CheckNetwork(){}

    public static boolean check(Context context){
        if(context == null){
            return false;
        }
        try {
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = connMgr.getActiveNetworkInfo();
            return info != null && info.isConnected();
        }catch (Exception e){
            e.printStackTrace();
        }

        return false;
    }

}
