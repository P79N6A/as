package com.yaoguang.appcommon.common.eventbus;

/**
 * Created by Administrator on 2017/5/27 0027.
 */
public class NotificationEvent {
    private String toPage;
    private String toPageId;

    public NotificationEvent(String toPage, String toPageId) {
        this.toPage = toPage;
        this.toPageId = toPageId;
    }

    public NotificationEvent(String toPage) {
        this.toPage = toPage;
    }

    public String getToPage() {
        return toPage;
    }

    public String getToPageId() {
        return toPageId;
    }



}
