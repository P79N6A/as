package com.yaoguang.lib.entity;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import java.util.ArrayList;

/**
 * 功能导航类
 * Created by zhongjh on 2017/11/22.
 */
public class TipView implements Parcelable {

    /**
     * 作为位置的参照物View
     */
    private View view;

    /**
     * 图片资源id
     */
    private Integer resourceId;

    /**
     * 由上面的图片资源id转换为bitmap
     */
    private Bitmap bitmap;

    /**
     * 依附在TipView的周边Bitmap
     */
    private ArrayList<TipBitmap>  tipBitmaps;

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


    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

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

    public ArrayList<TipBitmap> getTipBitmaps() {
        return tipBitmaps;
    }

    public void setTipBitmaps(ArrayList<TipBitmap> tipBitmaps) {
        this.tipBitmaps = tipBitmaps;
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
        dest.writeTypedList(this.tipBitmaps);
        dest.writeFloat(this.left);
        dest.writeFloat(this.top);
        dest.writeFloat(this.right);
        dest.writeFloat(this.bottom);
    }

    public TipView() {
    }

    protected TipView(Parcel in) {
        this.resourceId = (Integer) in.readValue(Integer.class.getClassLoader());
        this.tipBitmaps = in.createTypedArrayList(TipBitmap.CREATOR);
        this.left = in.readFloat();
        this.top = in.readFloat();
        this.right = in.readFloat();
        this.bottom = in.readFloat();
    }

    public static final Creator<TipView> CREATOR = new Creator<TipView>() {
        @Override
        public TipView createFromParcel(Parcel source) {
            return new TipView(source);
        }

        @Override
        public TipView[] newArray(int size) {
            return new TipView[size];
        }
    };
}
