package com.yaoguang.appcommon.phone.home.message.eventbus.child;

/**
 * 刷新数据
 * Created by zhongjh on 2017/11/27.
 */
public class RefreshDataEvent {

    public String getCodeType() {
        return codeType;
    }

    public void setCodeType(String codeType) {
        this.codeType = codeType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String codeType;
    private String id;
    private String position;
    private String type;

    /**
     * 刷新event
     */
    public RefreshDataEvent(String codeType, String id, String position, String type) {
        this.codeType = codeType;
        this.id = id;
        this.position = position;
        this.type = type;
    }


}
