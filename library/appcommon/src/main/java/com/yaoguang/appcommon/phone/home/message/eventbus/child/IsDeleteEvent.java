package com.yaoguang.appcommon.phone.home.message.eventbus.child;

/**
 * 通知检测是否可以删除，设置成 全选 还是 取消全选
 * Created by zhongjh on 2017/7/20.
 */
public class IsDeleteEvent {

    /**
     *
     * @param isDelete 是否可以删除
     * @param type 判断类型,0企业消息 1平台公告
     */
    public IsDeleteEvent(boolean isDelete,int type) {
        this.isDelete = isDelete;
        this.type = type;
    }

    private boolean isDelete;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    private int type;



    public boolean getIsDelete() {
        return isDelete;
    }

}
