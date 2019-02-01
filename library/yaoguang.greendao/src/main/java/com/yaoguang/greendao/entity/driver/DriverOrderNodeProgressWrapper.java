package com.yaoguang.greendao.entity.driver;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * 自己封装的节点获取 《节点的最终数据》
 * Created by zhongjh on 2018/4/27.
 */
public class DriverOrderNodeProgressWrapper implements Parcelable {


    private ArrayList<DriverOrderNodeWrapper> currentList;
    private ArrayList<DriverOrderNodeList> finishedList;

    public ArrayList<DriverOrderNodeWrapper> getCurrentList() {
        return currentList;
    }

    public void setCurrentList(ArrayList<DriverOrderNodeWrapper> currentList) {
        this.currentList = currentList;
    }

    public ArrayList<DriverOrderNodeList> getFinishedList() {
        return finishedList;
    }

    public void setFinishedList(ArrayList<DriverOrderNodeList> finishedList) {
        this.finishedList = finishedList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.currentList);
        dest.writeTypedList(this.finishedList);
    }

    public DriverOrderNodeProgressWrapper() {
    }

    protected DriverOrderNodeProgressWrapper(Parcel in) {
        this.currentList = in.createTypedArrayList(DriverOrderNodeWrapper.CREATOR);
        this.finishedList = in.createTypedArrayList(DriverOrderNodeList.CREATOR);
    }

    public static final Parcelable.Creator<DriverOrderNodeProgressWrapper> CREATOR = new Parcelable.Creator<DriverOrderNodeProgressWrapper>() {
        @Override
        public DriverOrderNodeProgressWrapper createFromParcel(Parcel source) {
            return new DriverOrderNodeProgressWrapper(source);
        }

        @Override
        public DriverOrderNodeProgressWrapper[] newArray(int size) {
            return new DriverOrderNodeProgressWrapper[size];
        }
    };
}
