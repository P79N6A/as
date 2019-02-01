package com.yaoguang.interactor.common;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.yaoguang.lib.net.bean.BaseResponse;
import com.yaoguang.greendao.entity.Order;
import com.yaoguang.greendao.entity.Pages;
import com.yaoguang.greendao.entity.common.UserOffice;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by zhongjh on 2017/3/30.
 * gson操作类
 */
public class GsonUtils<T> {

    /**
     * gson一般转换类
     *
     * @param typeToken
     * @param typeToken1
     * @param clazz      类
     * @param strJson    json字符串
     * @return 实体类
     */
    public T convertOrder(String strJson) {
        //判断code是否错误


        return new Gson().fromJson(strJson, new TypeToken<BaseResponse<Pages<List<Order>>>>() {
        }.getType());
    }

    public UserOffice convertUserOffice(String strJson) {
        //判断code是否错误
        return new Gson().fromJson(strJson, UserOffice.class);
    }

    /**
     * gson一般转换类
     *
     * @param strJson json字符串
     * @param clazz   类
     * @param strGson 实体类.STRGSON
     * @return 实体类
     */
    public BaseResponse<T> convert(String strJson, final Class<T> clazz, String strGson) {
        //判断code是否错误
        Gson gson = new Gson();

        JsonParser parser = new JsonParser();
        JsonObject jObject = parser.parse(strJson).getAsJsonObject();

        //判断state
        BaseResponse<T> baseResponse = new BaseResponse<>();
        String state = jObject.get("state").getAsString();
        baseResponse.setState(state);
        if (state.equals("200")) {
            if (clazz.equals(String.class)) {
                T t = gson.fromJson(jObject.get("result"), clazz);
                baseResponse.setResult(t);
            } else {
                T t = gson.fromJson(jObject.getAsJsonObject("result").get(strGson), clazz);
                baseResponse.setResult(t);
            }
        } else {
            baseResponse.setMessage(jObject.get("result").getAsString());
        }

        return baseResponse;
    }

    public BaseResponse<T> convert(String strJson, Type typeOfT, String strGson) {
        //判断code是否错误
        Gson gson = new Gson();

        JsonParser parser = new JsonParser();
        JsonObject jObject = parser.parse(strJson).getAsJsonObject();

        //判断state
        BaseResponse<T> baseResponse = new BaseResponse<>();
        String state = jObject.get("state").getAsString();
        baseResponse.setState(state);
        if (state.equals("200")) {
            T t;
            if (strGson == null || strGson.equals("")) {
                t = gson.fromJson(jObject.get("result"), typeOfT);
            } else {
                t = gson.fromJson(jObject.getAsJsonObject("result").get(strGson), typeOfT);
            }
            baseResponse.setResult(t);
        } else {
            baseResponse.setMessage(jObject.get("result").getAsString());
        }

        return baseResponse;
    }

}
