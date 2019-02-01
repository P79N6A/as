package com.yaoguang.appcommon.phone.activitys.start;

import android.os.Bundle;

import com.yaoguang.lib.base.BaseActivity;
import com.yaoguang.lib.common.SPUtils;
import com.yaoguang.lib.common.android.Installation;
import com.yaoguang.lib.common.constants.Constants;

/**
 * 用于判断用户是否登录中，如果登录了就直接进入main
 * 判断 是否 打开引导页
 * Created by 韦理英
 * on 2017/5/25.
 * Update by zhongjh:打开这个界面会判断是否缓存图片地址，如果缓存图片地址，那么后面会出现图片的界面
 * on 2017/12/26
 * Update by zhongjh:获取服务器公钥
 * on 2018/03/02
 */
public abstract class BaseStartActivity extends BaseActivity implements StartContact.View {

    StartContact.Presenter mPresenter = new StartPresenter(this, getCodtType());

    public abstract String getCodtType();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 获取公钥
        mPresenter.getCodeKey();

        // 存储唯一码
        SPUtils.getInstance().put(Constants.INSTALLATION_ID, Installation.id(getBaseContext()));
    }


}
