package com.yaoguang.company.businessorder2.common.businessmain.business.event;

import android.os.Bundle;

/**
 * Created by zhongjh on 2018/11/12.
 */
public class BusinessFragmentResultEvent {

    public BusinessFragmentResultEvent(int type, Bundle data) {
        this.type = type;
        this.data = data;
    }

    private int type;

    private Bundle data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Bundle getData() {
        return data;
    }

    public void setData(Bundle data) {
        this.data = data;
    }
}
