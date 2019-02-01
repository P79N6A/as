package com.yaoguang.appcommon.common.eventbus;


/**
 * Created by Administrator on 2017/7/19 0019.
 */

public class ContactFragmentEvent extends BaseEvent {
    protected String id;
    protected String cancelId;
    protected int mType;
    protected String mCodeType;

    public ContactFragmentEvent(String type) {
        super(type);
    }

    public ContactFragmentEvent(String type, String id,String cancelId, String mCodeType, int mType) {
        super(type);
        this.id = id;
        this.mCodeType = mCodeType;
        this.mType = mType;
        this.cancelId = cancelId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getmCodeType() {
        return mCodeType;
    }

    public void setmCodeType(String mCodeType) {
        this.mCodeType = mCodeType;
    }

    public int getmType() {
        return mType;
    }

    public void setmType(int mType) {
        this.mType = mType;
    }

    public String getCancelId() {
        return cancelId;
    }
}
