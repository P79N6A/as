package com.yaoguang.greendao.entity.driver;

import com.yaoguang.greendao.entity.DriverGoodsAddr;

import java.util.List;

/**
 * Created by liyangbin on 2018/6/1.
 * 拆/装箱任务
 */
public class SonoTaskVo {

    // 拆装箱类型(0:装箱 1:拆箱)
    private Integer otherService;

    // 封号
    private String sealNo;

    // 运单号
    private String mBlNo;

    //装卸时间
    private String loadDate;

    //货名
    private String goodsName;

    //货物类型
    private String goodsType;

    //装卸地址信息
    List<DriverGoodsAddr> goodsAddrs;

    public Integer getOtherService() {
        return otherService;
    }

    public void setOtherService(Integer otherService) {
        this.otherService = otherService;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getmBlNo() {
        return mBlNo;
    }

    public void setmBlNo(String mBlNo) {
        this.mBlNo = mBlNo;
    }

    public String getLoadDate() {
        return loadDate;
    }

    public void setLoadDate(String loadDate) {
        this.loadDate = loadDate;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsType() {
        return goodsType;
    }

    public void setGoodsType(String goodsType) {
        this.goodsType = goodsType;
    }

    public List<DriverGoodsAddr> getGoodsAddrs() {
        return goodsAddrs;
    }

    public void setGoodsAddrs(List<DriverGoodsAddr> goodsAddrs) {
        this.goodsAddrs = goodsAddrs;
    }
}
