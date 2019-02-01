package com.yaoguang.appcommon.phone.home.message.eventbus.main;

/**
 * 通知是否显示编辑的menu
 */
public class UpdateMenuEvent {

    public UpdateMenuEvent(boolean isShowMenu, int type) {
        this.isShowMenu = isShowMenu;
        this.type = type;
    }

    private boolean isShowMenu;

    public boolean isShowMenu() {
        return isShowMenu;
    }

    public void setShowMenu(boolean showMenu) {
        isShowMenu = showMenu;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    /**
     * 判断类型,0企业消息 1平台公告
     */
    private int type;

}
