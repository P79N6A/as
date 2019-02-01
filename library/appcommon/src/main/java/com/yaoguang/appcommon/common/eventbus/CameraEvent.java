package com.yaoguang.appcommon.common.eventbus;

/**
 * 作者：韦理英
 * 时间： 2017/5/12 0012.
 */

public class CameraEvent {
    private String path;
    private String name;
    private int type;

    public CameraEvent(String path, String name) {
        this.path = path;
        this.name = name;
    }

    public CameraEvent(int type) {
        this.type = type;
    }

    public CameraEvent(String path, String name, int type) {
        this.name = name;
        this.path = path;
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }
}
