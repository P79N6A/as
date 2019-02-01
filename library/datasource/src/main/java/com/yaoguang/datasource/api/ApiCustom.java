package com.yaoguang.datasource.api;


import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.NetWorkUtil;
import com.yaoguang.lib.net.CustomConverterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by baixiaokang on 16/3/9.
 */
public class ApiCustom {

    //     内网
//    public static final String ENDPOINT = "http://aohysoft2.gnway.cc:8084/api/";
//    public static final String ENDPOINT = "http://192.168.1.11:8084/api/";
//    public static final String ENDPOINT = "http://192.168.1.233:8084/api/";
//    public static final String ENDPOINT = "http://192.168.1.176:8084/api/";
//    private static final String ENDPOINT = "http://192.168.1.87:8084/api/";
//    public static final String ENDPOINT = "http://yaoguangsoft.com:8084/api/";
//    private static final String ENDPOINT = "http://web.yaoguangsoft.com:8084/api/";
//    private static final String ENDPOINT = "http://192.168.1.87:8084/api/";

    public Retrofit retrofit;

    private static ApiCustom instance;

    //构造方法私有
    private ApiCustom(String url) {
        checkNotNull(url, "endpoint == null");

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile = new File(BaseApplication.getInstance().getBaseContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
//                .addInterceptor(headInterceptor)
//                .addInterceptor(logInterceptor)
//                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(CustomConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }

    public static ApiCustom getInstance(String url) {
        if (instance == null) {
            synchronized (ApiCustom.class) {
                if (instance == null) {
                    instance = new ApiCustom(url);
                }
            }
        }
        return instance;
    }

//    Interceptor headInterceptor = (chain) -> chain.proceed(chain.request().newBuilder()
//            .addHeader("X-LC-Id", C.X_LC_Id)
//            .addHeader("X-LC-Key", C.X_LC_Key)
//            .addHeader("Content-Type", "application/json")
//            .build());


    class HttpCacheInterceptor implements Interceptor {

        @Override
        public Response intercept(Chain chain) throws IOException {
            Request request = chain.request();
            if (!NetWorkUtil.isNetConnected(BaseApplication.getInstance().getBaseContext())) {
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build();
                Log.d("Okhttp", "no network");
            }

            Response originalResponse = chain.proceed(request);
            if (NetWorkUtil.isNetConnected(BaseApplication.getInstance().getBaseContext())) {
                //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
                String cacheControl = request.cacheControl().toString();
                return originalResponse.newBuilder()
                        .header("Cache-Control", cacheControl)
                        .removeHeader("Pragma")
                        .build();
            } else {
                return originalResponse.newBuilder()
                        .header("Cache-Control", "public, only-if-cached, max-stale=1")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }


}