package me.donlis.vreader.http;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import me.donlis.common.util.Utils;
import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpUtils {

    private long CONNECTTIMEOUT = 25;

    private long READTIMEOUT = 25;

    private long WRITETIMEOUT = 25;

    private Context mContext;

    private Gson gson;

    private HttpUtils(){
        mContext = Utils.getContext();
    }

    public static HttpUtils getInstance(){
        return HttpHolder.instance;
    }

    private static class HttpHolder{
        private static HttpUtils instance = new HttpUtils();
    }

    public Retrofit build(String url){
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(url);
        builder.client(getOkHttpClient());
        builder.addConverterFactory(GsonConverterFactory.create(getGson()));
        builder.addCallAdapterFactory(RxJava2CallAdapterFactory.create());
        return builder.build();
    }

    private OkHttpClient getOkHttpClient(){
        File httpCacheDirectory = new File(mContext.getCacheDir(), "httpcache");
        // 50 MB
        int cacheSize = 50 * 1024 * 1024;
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        builder.connectTimeout(CONNECTTIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(READTIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(WRITETIMEOUT, TimeUnit.SECONDS);

        //缓存
        builder.addInterceptor(new AddCacheInterceptor(mContext));
        builder.cache(cache);

        return builder.build();
    }

    private Gson getGson(){
        if(gson == null){
            GsonBuilder builder = new GsonBuilder()
                    .setLenient()
                    .serializeNulls();
            gson = builder.create();
        }
        return gson;
    }

    private class AddCacheInterceptor implements Interceptor {
        private Context context;

        AddCacheInterceptor(Context context) {
            super();
            this.context = context;
        }

        @Override
        public Response intercept(Chain chain) throws IOException {

            CacheControl.Builder cacheBuilder = new CacheControl.Builder();
            cacheBuilder.maxAge(0, TimeUnit.SECONDS);
            cacheBuilder.maxStale(365, TimeUnit.DAYS);
            CacheControl cacheControl = cacheBuilder.build();
            Request request = chain.request();
            if (!CheckNetwork.check(context)) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build();
            }
            Response originalResponse = chain.proceed(request);
            if (CheckNetwork.check(context)) {
                // read from cache
                int maxAge = 0;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=" + maxAge)
                        .build();
            } else {
                // tolerate 4-weeks stale
                int maxStale = 60 * 60 * 24 * 28;
                return originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
        }
    }

}
