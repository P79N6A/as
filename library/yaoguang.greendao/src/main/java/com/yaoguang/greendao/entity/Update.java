package com.yaoguang.greendao.entity;

/**
 * Created by 韦理英
 * on 2017/5/27 0027.
 */

public class Update {

    /**
     * state : 200
     * result : {"linkUrl":"http://aohysoft2.gnway.cc:8084/api/downloads/app-debug.apk","versionInfo":"1.测试1。&2.测试2。&3.测试3。&4.测试4。","versionName":"司机端","versionCode":"1.0.0"}
     */

    private String state;
    private ResultBean result;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * linkUrl : http://aohysoft2.gnway.cc:8084/api/downloads/app-debug.apk
         * versionInfo : 1.测试1。&2.测试2。&3.测试3。&4.测试4。
         * versionName : 司机端
         * versionCode : 1.0.0
         */

        private String linkUrl;
        private String versionInfo;
        private String versionName;
        private int versionCode;

        public String getLinkUrl() {
            return linkUrl;
        }

        public void setLinkUrl(String linkUrl) {
            this.linkUrl = linkUrl;
        }

        public String getVersionInfo() {
            return versionInfo;
        }

        public void setVersionInfo(String versionInfo) {
            this.versionInfo = versionInfo;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }
    }
}
