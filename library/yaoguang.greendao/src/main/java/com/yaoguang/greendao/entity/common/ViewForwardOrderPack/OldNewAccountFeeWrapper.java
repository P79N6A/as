package com.yaoguang.greendao.entity.common.ViewForwardOrderPack;


import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * 修改费用 新旧工作单费用包装
 * Created by LiYangBin on 2018/11/13.
 */
public class OldNewAccountFeeWrapper implements Parcelable {

    private String billsId; // 所属工作单(货代id / 拖车orderSn)

    private List<AccountFee> oldFees; // 原费用

    private List<AccountFee> newFees; // 新费用

    public String getBillsId() {
        return billsId;
    }

    public void setBillsId(String billsId) {
        this.billsId = billsId;
    }

    public List<AccountFee> getOldFees() {
        return oldFees;
    }

    public void setOldFees(List<AccountFee> oldFees) {
        this.oldFees = oldFees;
    }

    public List<AccountFee> getNewFees() {
        return newFees;
    }

    public void setNewFees(List<AccountFee> newFees) {
        this.newFees = newFees;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.billsId);
        dest.writeTypedList(this.oldFees);
        dest.writeTypedList(this.newFees);
    }

    public OldNewAccountFeeWrapper() {
    }

    protected OldNewAccountFeeWrapper(Parcel in) {
        this.billsId = in.readString();
        this.oldFees = in.createTypedArrayList(AccountFee.CREATOR);
        this.newFees = in.createTypedArrayList(AccountFee.CREATOR);
    }

    public static final Parcelable.Creator<OldNewAccountFeeWrapper> CREATOR = new Parcelable.Creator<OldNewAccountFeeWrapper>() {
        @Override
        public OldNewAccountFeeWrapper createFromParcel(Parcel source) {
            return new OldNewAccountFeeWrapper(source);
        }

        @Override
        public OldNewAccountFeeWrapper[] newArray(int size) {
            return new OldNewAccountFeeWrapper[size];
        }
    };
}
