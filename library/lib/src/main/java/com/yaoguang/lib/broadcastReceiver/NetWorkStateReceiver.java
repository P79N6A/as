package com.yaoguang.lib.broadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.yaoguang.lib.base.BaseApplication;

/**
 * 获取网络状态的 广播接收器
 * Created by zhongjh on 2017/7/27.
 */
public class NetWorkStateReceiver extends BroadcastReceiver {

    // 目前暂定至少有wifi或者同时有wifi和连接 = 0;
    protected int type = -1;

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("网络状态发生变化");
        //检测API是不是小于23，因为到了API23之后getNetworkInfo(int networkType)方法被弃用
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP) {

            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取ConnectivityManager对象对应的NetworkInfo对象
            //获取WIFI连接的信息
            NetworkInfo wifiNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            //获取移动数据连接的信息
            NetworkInfo dataNetworkInfo = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if (wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                Toast.makeText(BaseApplication.getInstance(), "WIFI已连接,移动数据已连接", Toast.LENGTH_SHORT).show();
                type = 0;
            } else if (wifiNetworkInfo.isConnected() && !dataNetworkInfo.isConnected()) {
                Toast.makeText(BaseApplication.getInstance(), "WIFI已连接,移动数据已断开", Toast.LENGTH_SHORT).show();
                type = 0;
            } else if (!wifiNetworkInfo.isConnected() && dataNetworkInfo.isConnected()) {
                Toast.makeText(BaseApplication.getInstance(), "WIFI已断开,移动数据已连接", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(BaseApplication.getInstance(), "WIFI已断开,移动数据已断开", Toast.LENGTH_SHORT).show();
            }

        } else {
            //API大于23时使用下面的方式进行网络监听
            System.out.println("API level 大于23");
            //获得ConnectivityManager对象
            ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

            //获取所有网络连接的信息
            Network[] networks = connMgr.getAllNetworks();
            //用于存放网络连接信息
            StringBuilder sb = new StringBuilder();
            //通过循环将网络信息逐个取出来
            for (int i = 0; i < networks.length; i++) {
                //获取ConnectivityManager对象对应的NetworkInfo对象
                NetworkInfo networkInfo = connMgr.getNetworkInfo(networks[i]);
                if (networkInfo == null) {
                    return;
                }
                if (networkInfo.isConnected()) {
                    sb.append("已连接").append(networkInfo.getTypeName());
                    type = 0;
                } else {
                    sb.append(networkInfo.getTypeName()).append("已断开");
                }
//                sb.append(networkInfo.getTypeName() + " connect is " + networkInfo.isConnected());
            }
            if (sb.toString().equals("")) {
                Toast.makeText(BaseApplication.getInstance(), "没有任何网络连接，", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(BaseApplication.getInstance(), sb.toString(), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
