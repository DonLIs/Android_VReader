package me.donlis.vreader.http;

import me.donlis.vreader.http.api.IMxnzp;
import me.donlis.vreader.http.api.IWanAndroid;

public final class HttpClient {

    public static IWanAndroid getWanAndroidServer(){
        return HttpBuilder.getInstance().create(IWanAndroid.class,API.API_WAN_ANDROID);
    }

    public static IMxnzp getMxnzpServer(){
        return HttpBuilder.getInstance().create(IMxnzp.class,API.API_MXNZP);
    }

}
