package com.yaoguang.greendao.entity;


import android.os.Parcel;


import com.arlib.floatingsearchview.suggestions.model.SearchSuggestion;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * 一个共用的搜索框数据源
 */
@Entity
public class PublicSearchData implements SearchSuggestion {

    public PublicSearchData(Parcel source) {
        this.id = source.readLong();
        this.name = source.readString();
        this.type = source.readInt();
    }

    @Generated(hash = 1530655051)
    public PublicSearchData(Long id, String name, int type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    @Generated(hash = 1527043148)
    public PublicSearchData() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Id(autoincrement = true)
    public Long id;
    private String name;
    private int type;

    @Override
    public String getBody() {
        return name;
    }

    public static final Creator<PublicSearchData> CREATOR = new Creator<PublicSearchData>() {
        @Override
        public PublicSearchData createFromParcel(Parcel in) {
            return new PublicSearchData(in);
        }

        @Override
        public PublicSearchData[] newArray(int size) {
            return new PublicSearchData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeInt(type);
    }


}