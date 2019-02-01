package com.yaoguang.appcommon.common.eventbus;

import android.content.Intent;

/**
 * 作者：韦理英
 * 时间： 2017/5/12 0012.
 */

public class MainActivityResult {
    private int requestCode;
    private int resultCode;
    private Intent data;

    public MainActivityResult(int requestCode, int resultCode, Intent data) {
        this.requestCode = requestCode;
        this.resultCode = resultCode;
        this.data = data;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getData() {
        return data;
    }
}
