package com.yaoguang.company.businessorder2.common.businessmain.dynamic.event;

import com.yaoguang.greendao.entity.common.ViewForwardOrderPack.LogBillsTrack;

/**
 * 传递 工作单业务状态跟踪信息
 * Created by zhongjh on 2018/11/10.
 */
public class InitEvent {

    private String billType;// 0:货代 1：拖车
    protected String otherService;// 装箱（0） 拆箱（1）
    private LogBillsTrack logBillsTrack;

    public InitEvent(LogBillsTrack logBillsTrack) {
        this.logBillsTrack = logBillsTrack;
    }

    public LogBillsTrack getLogBillsTrack() {
        return logBillsTrack;
    }

    public void setLogBillsTrack(LogBillsTrack logBillsTrack) {
        this.logBillsTrack = logBillsTrack;
    }

    public String getBillType() {
        return billType;
    }

    public void setBillType(String billType) {
        this.billType = billType;
    }

    public String getOtherService() {
        return otherService;
    }

    public void setOtherService(String otherService) {
        this.otherService = otherService;
    }

}
