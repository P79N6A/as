package com.yaoguang.greendao.entity.driver;

import android.os.Parcel;
import android.os.Parcelable;

import com.yaoguang.greendao.entity.DriverBillsVo;

import java.util.ArrayList;

/**
 * 为了解决<List<List>>
 * Created by zhongjh on 2018/5/30.
 */
public class ListDriverBillsVo extends ArrayList<DriverBillsVo> implements Parcelable{
    private static final long serialVersionUID = -8516873361351845306L;

    public ListDriverBillsVo() {
        super();
    }

    protected ListDriverBillsVo(Parcel in) {
        in.readList(this, DriverBillsVo.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this);
    }

    public static final Parcelable.Creator<ListDriverBillsVo> CREATOR = new Parcelable.Creator<ListDriverBillsVo>() {
        public ListDriverBillsVo createFromParcel(Parcel in) {
            return new ListDriverBillsVo(in);
        }

        public ListDriverBillsVo[] newArray(int size) {
            return new ListDriverBillsVo[size];
        }
    };
}
