package com.yaoguang.appcommon.common.eventbus;

/**
 * 作者：韦理英
 * 时间： 2017/5/12 0012.
 */

public class CameraResultEvent {
    private String file;
    private int type;

    public CameraResultEvent(String file, int type) {
        this.file = file;
        this.type = type;
    }

    public String getFile() {
        return file;
    }

    public int getType() {
        return type;
    }
}
