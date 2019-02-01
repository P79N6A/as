package com.yaoguang.greendao.entity.company;


import com.yaoguang.greendao.entity.common.TruckBills;

/**
 * 更新货代工作单实体类
 * Created by LiYangBin on 2018/11/9.
 */
public class RecordUpdate {


    private TruckBills oldRecord; // 旧数据

    private TruckBills newRecord; // 新数据


    public TruckBills getOldRecord() {
        return oldRecord;
    }

    public void setOldRecord(TruckBills oldRecord) {
        this.oldRecord = oldRecord;
    }

    public TruckBills getNewRecord() {
        return newRecord;
    }

    public void setNewRecord(TruckBills newRecord) {
        this.newRecord = newRecord;
    }

}
