package com.yaoguang.lib.net;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;

/**
 * 处理null的gson
 * Created by zhongjh on 2017/6/29.
 */
public class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
    @SuppressWarnings("unchecked")
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
        Class<T> rawType = (Class<T>) type.getRawType();
        if (rawType != String.class) {
            if (rawType == Integer.class) {
                return (TypeAdapter<T>) new IntegerNullAdapter();
            }
            return null;
        }
        return (TypeAdapter<T>) new StringNullAdapter();
    }
}
