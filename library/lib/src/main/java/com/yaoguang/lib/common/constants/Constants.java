package com.yaoguang.lib.common.constants;

import android.os.Environment;

import java.io.File;

/**
 * Created by 韦理英
 * on 2017/5/4 0004.
 */

public class Constants {
    public static final String WeatherData = "WeatherData";                 //上次缓存天气的时间的配置
    public static final String WeatherCity = "WeatherCity";                 //上次缓存天气的城市的配置
    public static final String Weather = "Weather";                         //上次缓存天气
    public static final String WeatherPic = "WeatherPic";                   //上次缓存天气图片

    public static final String ScreenWidth = "ScreenWidth";                 //屏幕宽度
    public static final String ScreenHeight = "ScreenHeight";               //屏幕高度
    public static final String StatusBarHeight = "StatusBarHeight";         //状态栏高度

    //推送 - 铃声 - 震动
    public static final String Sound = "Sound";
    public static final String Vibrate = "Vibrate";

    //网络的
    public static final String APP_WANTU_SIZE = "@400w_400h_90Q_2o.jpg";       // wantu缓存图片
    public static String imageName = System.currentTimeMillis() + ".jpg";   // 图片名

    public static final String TOKEN_ID = "token_id";                       // token的id标识，也是用户的id
    public static final String TOKEN_TOKEN = "token_token";                 //token值标识，服务器返回来的
    public static final String INSTALLATION_ID = "Installation_ID";                       // Installation ID / GUID

    public static final String RONG_CLOUD_TOKEN = "rongCloudToken";

    public static final String APP_DRIVER = "Driver";                       // 司机端标识
    public static final String APP_BOSS = "Boss";                           // boss端标识
    public static final String APP_COMPANY = "Company";                     // 公司端标识
    public static final String APP_SHIPPER = "Shipper";                     // 货主端标识
    public static final String USERNAME = "Username";                       // 帐号
    public static final String PASSWORD = "Password";                       // 密码
    public static final String AUTOLOGIN = "AutoLogin";                     // 参数：自动登录
    public static final String APP_ALIAS = "id";                            // 参数：推送别名id
    public static final String APP_TOPOFFICEID = "topOfficeId";
    public static final String LOCALVERIONCODE = "LocalVerionCode";
    public static final String FIRST_TIME_LAUNCH = "isFirstTimeLaunch";

    public static final String FLAG_ORDER = "Order";                        // 去订单中心
    public static final String FLAG_PERSONL_MESSAGE = "PersonlMessage";     // 去个人消息
    public static final String FLAG_PLATFORM_MESSAGE = "PlatformMessage";   // 去平台系统
    public static final String FLAG_ENTERPRISE_NEWS = "EnterpriseNews";     // 去企业消息
    public static final String FLAG_ORDER_DETAIL = "OrderDetail";           // 去订单详情
    public static final String FLAG_UNREAD_NUM = "未读数";                  // 去订单详情
    public static final String FLAG_REFRESH_PROGRESS = "FLAG_REFRESH_PROGRESS";          // 刷新进度
    public static final String FLAG_REFRESH_TOOLBAR = "刷新toolbar";        // 刷新进度
    public static final String FLAG_REFRESH_PAGE = "FLAG_REFRESH_PAGE";              // 通知 订单刷新
    //    public static final String HEAD = "http://119.23.146.102:8080/image/";  // 图片前缀
    public static final String HEAD = "https://";  // 图片前缀

    public static final File SDK_PATH = Environment.getExternalStorageDirectory();  // 本地sdk地址
    public static final String SDK_PATH_IMAGE = SDK_PATH + "/YGDriver/Images";      // 图片存放本地地址
    public static final String SDK_PATH_VIDEO = SDK_PATH + "/YGDriver/Video";       // 视频存放本地地址
    public static final String SDK_PATH_AUDIO = SDK_PATH + "/YGDriver/Audio";       // 录音存放本地地址
    public static final String SDK_PATH_AVATAR = SDK_PATH + "/YGDriver/Avatar";// 头像存放本地地址
    public static final String SD_IMAGES_PATH = SDK_PATH + File.separator + "DCIM" + File.separator + "Camera" + File.separator;
}
