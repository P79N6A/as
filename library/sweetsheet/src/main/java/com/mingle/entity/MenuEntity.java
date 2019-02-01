package com.mingle.entity;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.DrawableRes;
import android.support.annotation.ColorInt;


/**
 * @author zzz40500
 * @version 1.0
 * @date 2015/8/5.
 * @github: https://github.com/zzz40500
 */
public class MenuEntity implements Parcelable {

    public @DrawableRes int iconId;
    public @ColorInt int titleColor;
    public CharSequence title;
    public Drawable icon;
    public String value;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.iconId);
        dest.writeInt(this.titleColor);
        dest.writeString(this.title.toString());
        dest.writeString(this.value);
    }

    public MenuEntity() {
    }

    protected MenuEntity(Parcel in) {
        this.iconId = in.readInt();
        this.titleColor = in.readInt();
        this.title = in.readParcelable(CharSequence.class.getClassLoader());
        this.icon = in.readParcelable(Drawable.class.getClassLoader());
        this.value = in.readString();
    }

    public static final Creator<MenuEntity> CREATOR = new Creator<MenuEntity>() {
        @Override
        public MenuEntity createFromParcel(Parcel source) {
            return new MenuEntity(source);
        }

        @Override
        public MenuEntity[] newArray(int size) {
            return new MenuEntity[size];
        }
    };
}
