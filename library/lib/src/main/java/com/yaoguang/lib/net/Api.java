package com.yaoguang.lib.net;


import android.util.Log;
import android.widget.Toast;

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

/**
 * Created by baixiaokang on 16/3/9.
 * Update by zhongjh on 17/12/11. 这是封装了传递数据和返回数据的一些方法
 * url = "http://www.huoyunji.com:8084/api/driver/order/count/downloads.do?driverId=54ed0af08938455388c15867adad653d&dateScopeType=2";
 */
public class Api {
    // 外网ip
//        public static final String ENDPOINT = "http://www.huoyunji.com:8084/api/";

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

    //构造方法私有
    protected Api() {
//        // 判断用户类型,用户类型(0-司机，1-物流，2-货主)
//        String userTypeDeviceToken = "-1";
//        switch (BaseApplication.getAppType()) {
//            case Constants.APP_COMPANY:
//                userTypeDeviceToken = "1";
//                break;
//            case Constants.APP_SHIPPER:
//                userTypeDeviceToken = "2";
//                break;
//            case Constants.APP_DRIVER:
//                userTypeDeviceToken = "0";
//                break;
//        }

//        CommonParamsInterceptor commonParamsInterceptor =
//                new CommonParamsInterceptor.Builder()
//                        .addQueryParam("appTypeDeviceToken", "android")// app类型（android，ios）
//                        .addQueryParam("appVersionDeviceToken", PackageUtil.getPackageVersion(BaseApplication.getInstance()))// app版本
////                        .addQueryParam("userDeviceToken", BaseApplication.getInstance().getToken())// 唯一性校验token
////                        .addQueryParam("userIdDeviceToken", BaseApplication.getInstance().getId())// 用户id
//                        .addQueryParam("userTypeDeviceToken", userTypeDeviceToken)// 用户类型(0-司机，1-物流，2-货主)
//                        .build();

        HttpLoggingInterceptor logInterceptor = new HttpLoggingInterceptor();
        logInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile = new File(BaseApplication.getInstance().getBaseContext().getCacheDir(), "cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); //100Mb
        SignInterceptor signInterceptor=new SignInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(7676, TimeUnit.MILLISECONDS)
                .connectTimeout(7676, TimeUnit.MILLISECONDS)
//                .addInterceptor(commonParamsInterceptor)
                .addInterceptor(signInterceptor)
//                .addInterceptor(headInterceptor)
//                .addInterceptor(logInterceptor)
//                .addNetworkInterceptor(new HttpCacheInterceptor())
                .cache(cache)
                .build();

        String url = BuildConfig.ENDPOINT;
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(CustomConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build();
    }


    public static Api getInstance() {
        return SingletonHolder.INSTANCE;
    }

//    Interceptor headInterceptor = (chain) -> chain.proceed(chain.request().newBuilder()
//            .addHeader("X-LC-Id", C.X_LC_Id)
//            .addHeader("X-LC-Key", C.X_LC_Key)
//            .addHeader("Content-Type", "application/json")
//            .build());

    private static class SingletonHolder {
        private static final Api INSTANCE = new Api();
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
                        .header("Cache-Control", "public, only-if-cached, max-stale=1")
                        .removeHeader("Pragma")
                        .build();
            }
        }
    }


}