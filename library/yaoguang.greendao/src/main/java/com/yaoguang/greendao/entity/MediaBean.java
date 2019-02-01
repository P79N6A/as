package com.yaoguang.greendao.entity;

/**
 * 图片与视频 bean
 * Created by 韦理英 on 2017/10/26 0026.
 */

public class MediaBean {
    //  0 图片 1视频
    int type;
    String url;
    byte[] Video;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public byte[] getVideo() {
        return Video;
    }

    public void setVideo(byte[] video) {
        Video = video;
    }
}
