package com.yaoguang.lib.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 依附在TipView的周边Bitmap
 * Created by zhongjh on 2017/11/22.
 */
public class TipBitmap implements Parcelable {


    /**
     * 图片资源
     */
    private Integer resourceId;
    /**
     * 由上面的图片资源id转换为bitmap
     */
    private Bitmap bitmap;
    /**
     * 处于参照物的哪个位置 android.view.Gravity.CENTER
     */
    private int gravity;

    /**
     * 左边附加值,到了TipUtils工具处理最终变成图片的left
     */
    private float left;

    /**
     * 顶部附加值,到了TipUtils工具处理最终变成图片的top
     */
    private float top;
    /**
     * 右边附加值,暂时没用
     */
    private float right;
    /**
     * 底部附加值,暂时没用
     */
    private float bottom;


    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public int getGravity() {
        return gravity;
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
    }

    public float getLeft() {
        return left;
    }

    public void setLeft(float left) {
        this.left = left;
    }

    public float getTop() {
        return top;
    }

    public void setTop(float top) {
        this.top = top;
    }

    public float getRight() {
        return right;
    }

    public void setRight(float right) {
        this.right = right;
    }

    public float getBottom() {
        return bottom;
    }

    public void setBottom(float bottom) {
        this.bottom = bottom;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.resourceId);
        dest.writeInt(this.gravity);
        dest.writeFloat(this.left);
        dest.writeFloat(this.top);
        dest.writeFloat(this.right);
        dest.writeFloat(this.bottom);
    }

    public TipBitmap() {
    }

    protected TipBitmap(Parcel in) {
        this.resourceId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.gravity = in.readInt();
        this.left = in.readFloat();
        this.top = in.readFloat();
        this.right = in.readFloat();
        this.bottom = in.readFloat();
    }

    public static final Creator<TipBitmap> CREATOR = new Creator<TipBitmap>() {
        @Override
        public TipBitmap createFromParcel(Parcel source) {
            return new TipBitmap(source);
        }

        @Override
        public TipBitmap[] newArray(int size) {
            return new TipBitmap[size];
        }
    };
}
