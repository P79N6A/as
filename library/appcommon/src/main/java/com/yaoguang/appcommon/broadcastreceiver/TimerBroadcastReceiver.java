//package com.yaoguang.appcommon.broadcastreceiver;
//
//import android.app.AlarmManager;
//import android.app.PendingIntent;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.os.SystemClock;
//
//import com.yaoguang.appcommon.service.UpdateCityWeatherService;
//
///**
// * 广播接收器
// * Created by zhongjh on 2017/7/25.
// */
//public class TimerBroadcastReceiver extends BroadcastReceiver {
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        //获取是否启用，一开始不启用获取天气，由首页获取
//        boolean enable = intent.getBooleanExtra("enable", true);
//        if (enable) {
//            intent.setClass(context, UpdateCityWeatherService.class);
//            context.startService(intent);
//        }
//
//        //重新启用自身
//        Intent intent2 = new Intent(context, TimerBroadcastReceiver.class);
//        intent2.putExtra("enable", true);
//        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 2, intent2, 0);
//        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, SystemClock.elapsedRealtime() + (1000 * 10800), pendingIntent);//10800
//    }
//
//}
