package com.yaoguang.greendao.entity.driver;

/**
 * 订单节点
 *
 * @author liyangbin
 * @date 2017年5月16日上午9:55:37
 */
public class DriverOrderNodeDetail {


    private String id;
    // 节点id
    private String nodeId;
    // 货柜id
    private String sonoId;
    // 柜号
    private String contNo;
    // 封号
    private String sealNo;
    // 描述
    private String remark;
    // 照片
    private String picture;
    // 视频
    private String videoUrl;
    // 音频
    private String audioUrl;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public String getSonoId() {
        return sonoId;
    }

    public void setSonoId(String sonoId) {
        this.sonoId = sonoId;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }
}