package com.yaoguang.greendao.entity;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * 里程及油耗说明表
 *
 * @author heyonggang
 * @date 2016年9月5日下午2:29:01
 */
public class TruckOil implements Parcelable {

    private String id;

    private String billsId;

    private String roadmap;

    private Double distanceF;

    private Double distanceE;

    private Double distFee;

    private Double distS;

    private Double oilRateF;

    private Double oilRateE;

    private Double oilRate;

    private Double distE;

    private Double oilQtyF;

    private Double oilQtyE;

    private Double oilQty;

    private Double oilWearL;

    private Double oilWearS;

    private String fOilCard;

    private Integer isSelf;

    private String createdBy;

    private String createdDeptId;

    private String created;

    private String updatedBy;

    private String updatedDeptId;

    private String updated;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBillsId() {
        return billsId;
    }

    public void setBillsId(String billsId) {
        this.billsId = billsId;
    }

    public String getRoadmap() {
        return roadmap;
    }

    public void setRoadmap(String roadmap) {
        this.roadmap = roadmap;
    }

    public Double getDistanceF() {
        return distanceF;
    }

    public void setDistanceF(Double distanceF) {
        this.distanceF = distanceF;
    }

    public Double getDistanceE() {
        return distanceE;
    }

    public void setDistanceE(Double distanceE) {
        this.distanceE = distanceE;
    }

    public Double getDistFee() {
        return distFee;
    }

    public void setDistFee(Double distFee) {
        this.distFee = distFee;
    }

    public Double getDistS() {
        return distS;
    }

    public void setDistS(Double distS) {
        this.distS = distS;
    }

    public Double getOilRateF() {
        return oilRateF;
    }

    public void setOilRateF(Double oilRateF) {
        this.oilRateF = oilRateF;
    }

    public Double getOilRateE() {
        return oilRateE;
    }

    public void setOilRateE(Double oilRateE) {
        this.oilRateE = oilRateE;
    }

    public Double getOilRate() {
        return oilRate;
    }

    public void setOilRate(Double oilRate) {
        this.oilRate = oilRate;
    }

    public Double getDistE() {
        return distE;
    }

    public void setDistE(Double distE) {
        this.distE = distE;
    }

    public Double getOilQtyF() {
        return oilQtyF;
    }

    public void setOilQtyF(Double oilQtyF) {
        this.oilQtyF = oilQtyF;
    }

    public Double getOilQtyE() {
        return oilQtyE;
    }

    public void setOilQtyE(Double oilQtyE) {
        this.oilQtyE = oilQtyE;
    }

    public Double getOilQty() {
        return oilQty;
    }

    public void setOilQty(Double oilQty) {
        this.oilQty = oilQty;
    }

    public Double getOilWearL() {
        return oilWearL;
    }

    public void setOilWearL(Double oilWearL) {
        this.oilWearL = oilWearL;
    }

    public Double getOilWearS() {
        return oilWearS;
    }

    public void setOilWearS(Double oilWearS) {
        this.oilWearS = oilWearS;
    }

    public String getfOilCard() {
        return fOilCard;
    }

    public void setfOilCard(String fOilCard) {
        this.fOilCard = fOilCard;
    }

    public Integer getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Integer isSelf) {
        this.isSelf = isSelf;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDeptId() {
        return createdDeptId;
    }

    public void setCreatedDeptId(String createdDeptId) {
        this.createdDeptId = createdDeptId;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public String getUpdatedDeptId() {
        return updatedDeptId;
    }

    public void setUpdatedDeptId(String updatedDeptId) {
        this.updatedDeptId = updatedDeptId;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.billsId);
        dest.writeString(this.roadmap);
        dest.writeValue(this.distanceF);
        dest.writeValue(this.distanceE);
        dest.writeValue(this.distFee);
        dest.writeValue(this.distS);
        dest.writeValue(this.oilRateF);
        dest.writeValue(this.oilRateE);
        dest.writeValue(this.oilRate);
        dest.writeValue(this.distE);
        dest.writeValue(this.oilQtyF);
        dest.writeValue(this.oilQtyE);
        dest.writeValue(this.oilQty);
        dest.writeValue(this.oilWearL);
        dest.writeValue(this.oilWearS);
        dest.writeString(this.fOilCard);
        dest.writeValue(this.isSelf);
        dest.writeString(this.createdBy);
        dest.writeString(this.createdDeptId);
        dest.writeString(this.created);
        dest.writeString(this.updatedBy);
        dest.writeString(this.updatedDeptId);
        dest.writeString(this.updated);
    }

    public TruckOil() {
    }

    protected TruckOil(Parcel in) {
        this.id = in.readString();
        this.billsId = in.readString();
        this.roadmap = in.readString();
        this.distanceF = (Double) in.readValue(Double.class.getClassLoader());
        this.distanceE = (Double) in.readValue(Double.class.getClassLoader());
        this.distFee = (Double) in.readValue(Double.class.getClassLoader());
        this.distS = (Double) in.readValue(Double.class.getClassLoader());
        this.oilRateF = (Double) in.readValue(Double.class.getClassLoader());
        this.oilRateE = (Double) in.readValue(Double.class.getClassLoader());
        this.oilRate = (Double) in.readValue(Double.class.getClassLoader());
        this.distE = (Double) in.readValue(Double.class.getClassLoader());
        this.oilQtyF = (Double) in.readValue(Double.class.getClassLoader());
        this.oilQtyE = (Double) in.readValue(Double.class.getClassLoader());
        this.oilQty = (Double) in.readValue(Double.class.getClassLoader());
        this.oilWearL = (Double) in.readValue(Double.class.getClassLoader());
        this.oilWearS = (Double) in.readValue(Double.class.getClassLoader());
        this.fOilCard = in.readString();
        this.isSelf = (Integer) in.readValue(Integer.class.getClassLoader());
        this.createdBy = in.readString();
        this.createdDeptId = in.readString();
        this.created = in.readString();
        this.updatedBy = in.readString();
        this.updatedDeptId = in.readString();
        this.updated = in.readString();
    }

    public static final Parcelable.Creator<TruckOil> CREATOR = new Parcelable.Creator<TruckOil>() {
        @Override
        public TruckOil createFromParcel(Parcel source) {
            return new TruckOil(source);
        }

        @Override
        public TruckOil[] newArray(int size) {
            return new TruckOil[size];
        }
    };
}