package com.yaoguang.lib.base.interfaces;

import android.os.Bundle;
import android.os.Message;

/**
 * 按钮动画
 * Created by zhongjh on 2017/9/25.
 */
public interface BaseSubmitProgressView extends BaseView{

    /**
     * 按钮初始化
     */
    void setSubmitProgressInit();

    /**
     * 按钮成功动画
     */
    void setSubmitProgressOK();

    /**
     * 延时事件，比如延时1.5秒打开窗体
     * @param bundle 如果没有需要的数据传递，直接new Bundle()即可
     */
    void setSubmitProgressSuccess(Bundle bundle);

    /**
     * 按钮加载动画
     */
    void setSubmitProgressLoading();

    /**
     * 按钮异常动画
     */
    void setSubmitProgressError();


}
