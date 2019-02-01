//package com.yaoguang.appcommon.service;
//
//import android.app.Service;
//import android.content.Context;
//import android.content.Intent;
//import android.os.IBinder;
//import android.support.annotation.Nullable;
//import android.util.Log;
//
//import com.yaoguang.lib.common.ObjectUtils;
//import com.yaoguang.greendao.entity.AliWeather;
//import com.yaoguang.map.location.impl.LocationManager;
//import com.yaoguang.lib.net.ApiDefault;
//import com.yaoguang.datasource.api.WeatherApi;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//
//import io.reactivex.Observer;
//import io.reactivex.android.schedulers.AndroidSchedulers;
//import io.reactivex.disposables.Disposable;
//import io.reactivex.schedulers.Schedulers;
//
///**
// * 天气服务
// * Created by zhongjh on 2017/7/25.
// */
//public class UpdateCityWeatherService extends Service {
//
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        // 应该在Service中开启线程去执行耗时任务，这样就可以有效地避免ANR(Application Not Responding)的出现。
//        // 先检查网络是否连接，如果有网再执行否则关闭本次更新，等待下一个闹钟
//        if (isNetWorkAvailable()) {
//            // Log.d(TAG, "测试");
////            new Thread(mRunnable).start();
//            setWeather(UpdateCityWeatherService.this);
//        }
//
//        stopSelf();// 发送广播后，停止自己，优化资源
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    public void setWeather(Context context) {
//        LocationManager locationManager = new LocationManager();
//        locationManager.init(context);
//        locationManager.setComeback(location -> {
//            locationManager.destroyLocation();
//            WeatherApi weatherApi = ApiDefault.getInstance().retrofit.create(WeatherApi.class);
//            weatherApi.getWeather2("APPCODE ebbefba3b9224a5389e9f18298e954a0", "3", ObjectUtils.parseString(location.getLat()), ObjectUtils.parseString(location.getLon()), "0", "0", "0", "0", "0")
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribeList(new Observer<AliWeather>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//                            int a = 5;
//                        }
//
//                        @Override
//                        public void onNext(AliWeather aliWeather) {
//                            Intent intent = new Intent("android.intent.action.MY_BROADCAST");
//                            intent.putExtra("aliWeather", aliWeather);
//                            intent.putExtra("msg", "1");
//                            sendBroadcast(intent);
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            int a = 5;
//                        }
//
//                        @Override
//                        public void onComplete() {
//                            int a = 5;
//                        }
//                    });
//        });
//    }
//
//    private boolean isNetWorkAvailable() {
//        String result = null;
//
//        try {
//            String e = "www.baidu.com";
//            Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + e);
//            InputStream input = p.getInputStream();
//            BufferedReader in = new BufferedReader(new InputStreamReader(input));
//            StringBuilder stringBuffer = new StringBuilder();
//            String content = "";
//
//            while ((content = in.readLine()) != null) {
//                stringBuffer.append(content);
//            }
//
//            Log.d("------ping-----", "result content : " + stringBuffer.toString());
//            int status = p.waitFor();
//            if (status != 0) {
//                result = "failed";
//                return false;
//            }
//
//            result = "success";
//        } catch (IOException var13) {
//            result = "IOException";
//            return false;
//        } catch (InterruptedException var14) {
//            result = "InterruptedException";
//            return false;
//        } finally {
//            Log.d("----result---", "result = " + result);
//        }
//
//        return true;
//    }
//
//}
