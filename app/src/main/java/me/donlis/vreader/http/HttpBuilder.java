package me.donlis.vreader.http;

import static me.donlis.vreader.http.API.API_WAN_ANDROID;

public class HttpBuilder {

    private Object wanandroidHttps;

    private HttpBuilder(){}

    public static HttpBuilder getInstance(){
        return HttpBuildHolder.instance;
    }

    private static class HttpBuildHolder{
        private static HttpBuilder instance = new HttpBuilder();
    }

    public <T> T create(Class<T> tClass,String url){

        switch (url){

            case API_WAN_ANDROID:
                if(wanandroidHttps == null){
                    synchronized (HttpBuilder.class){
                        if(wanandroidHttps == null){
                            wanandroidHttps = HttpUtils.getInstance().build(url).create(tClass);
                        }
                    }
                }
                return (T) wanandroidHttps;

            default:
                return null;

        }
    }

}
