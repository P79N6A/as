package com.yaoguang.greendao.entity.company;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LiYangBin on 2018/11/8.
 * 二维码扫码结果
 */
public class ScanCodeResult implements Parcelable {

    /**
     * 扫码状态标识
     */
    public static final int CODE_WAITING = 1000; // 等待扫码
    public static final int CODE_SUCCESS = 1001; // 扫码后确认
    public static final int CODE_SCANNED = 1002; // 已扫码
    public static final int CODE_FAILED = 1003; //  调用失败
    public static final int CODE_TIMEOUT = 1004; // 二维码失效

    private Integer code; // 进度标识

    private String info; // 提示

    private String key; // 本次操作随机码

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.code);
        dest.writeString(this.info);
        dest.writeString(this.key);
    }

    public ScanCodeResult() {
    }

    protected ScanCodeResult(Parcel in) {
        this.code = (Integer) in.readValue(Integer.class.getClassLoader());
        this.info = in.readString();
        this.key = in.readString();
    }

    public static final Parcelable.Creator<ScanCodeResult> CREATOR = new Parcelable.Creator<ScanCodeResult>() {
        @Override
        public ScanCodeResult createFromParcel(Parcel source) {
            return new ScanCodeResult(source);
        }

        @Override
        public ScanCodeResult[] newArray(int size) {
            return new ScanCodeResult[size];
        }
    };
}
