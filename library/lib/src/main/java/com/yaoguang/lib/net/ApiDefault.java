package com.yaoguang.lib.net;


import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.yaoguang.lib.BuildConfig;
import com.yaoguang.lib.base.BaseApplication;
import com.yaoguang.lib.common.NetWorkUtil;

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
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by baixiaokang on 16/3/9.
 * Update by zhongjh on 17/12/11. 这是保持默认的传递对象和返回对象，例如用于天气等等
 */
public class ApiDefault {

    public Retrofit retrofit;

    //构造方法私有
    private ApiDefault() {
        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile = new File(BaseApplication.getInstance().getBaseContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(767600, TimeUnit.MILLISECONDS)
                .connectTimeout(767600, TimeUnit.MILLISECONDS)
//                .addInterceptor(headInterceptor)
//                .addInterceptor(logInterceptor)
//                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(BuildConfig.ENDPOINT)
                .build();
    }

    public static ApiDefault getInstance() {
        return SingletonHolder.INSTANCE;
    }

//    Interceptor headInterceptor = (chain) -> chain.proceed(chain.request().newBuilder()
//            .addHeader("X-LC-Id", C.X_LC_Id)
//            .addHeader("X-LC-Key", C.X_LC_Key)
//            .addHeader("Content-Type", "application/json")
//            .build());

    private static class SingletonHolder {
        private static final ApiDefault INSTANCE = new ApiDefault();
    }

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
                        .header("Cache-Control", "public, only-if-cached, max-stale=2419200")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }


}