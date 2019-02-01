package com.yaoguang.appcommon.phone.activitys.login;


import com.yaoguang.appcommon.common.base.BaseSubmitProgressActivity;

/**
 * Created by 韦理英
 * on 2017/6/9 0009.
 */

public abstract class BaseLogin extends BaseSubmitProgressActivity {
    
    public abstract void toRegisterActivityCustom();


    public abstract void toForgetPasswordActivityCustom();

    /**
     * 一些自定义设置
     */
    public abstract void customSetting();

}
