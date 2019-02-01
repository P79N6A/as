package com.yaoguang.lib.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.yaoguang.lib.common.aesrsa.AES;
import com.yaoguang.lib.common.aesrsa.EncryUtil;
import com.yaoguang.lib.common.aesrsa.RSA;
import com.yaoguang.lib.net.bean.BaseResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import static com.yaoguang.lib.net.SignInterceptor.clientPrivateKey;

/**
 * 自定义返回的数据源
 * 自定义Convert,用于判断200
 * Created by zhongjh on 2017/6/1.
 */
public class CustomResponseBodyConverter<T> implements Converter<ResponseBody, BaseResponse<T>> {

    private final Type type;

    public CustomResponseBodyConverter(Type type) {
        this.type = type;
    }


    @Override
    public BaseResponse<T> convert(ResponseBody value) throws IOException {

        BaseResponse<T> baseResponse = new BaseResponse<>();

        // 解密后的字符串
        String valueStr = null;

        // （1）开始解密
        JsonParser parser = new JsonParser();
        JSONObject jObjectAES;

        try {
            jObjectAES = new JSONObject(value.string());


            // 判断是否有data,如果没有，就需要解密，如果有，就不需要解密
            if (!jObjectAES.isNull("data")) {
                // 验签
                boolean passSign;
                try {
                    passSign = EncryUtil.checkDecryptAndSign(jObjectAES.getString("data"), jObjectAES.getString("encryptkey"), jObjectAES.getString("serverPublicKey"), clientPrivateKey);
                    if (passSign) {
                        // 验签通过
                        String aeskey = RSA.decrypt(jObjectAES.getString("encryptkey"), clientPrivateKey);
                        valueStr = AES.decryptFromBase64(jObjectAES.getString("data"), aeskey);
                    } else {
                        System.out.println("验签失败");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // 不需要解密
                valueStr = jObjectAES.toString();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        e("json", valueStr);

        // （2）开始解析
        JSONObject jObject;
        JSONObject jDataObject;

        try {
            jObject = new JSONObject(valueStr);

            // 判断是否有data,如果没有，就可以直接使用，否则就先取data里面的数据
            if (jObject.isNull("data")) {
                jDataObject = jObject;
            } else {
                jDataObject = new JSONObject(jObject.getString("data"));
            }

            baseResponse.setState(jDataObject.getString("state"));

            if (baseResponse.getState().equals("200")) {
//            Gson gson = new Gson();
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss:SSS").registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
                if (type.equals(String.class)) {
                    T t = gson.fromJson(jDataObject.getString("result"), type);
                    baseResponse.setResult(t);
                } else {
                    if (jDataObject.getString("result").equals("\"\"")) {
                        baseResponse.setResult(null);
                    } else {
                        Type typeChild = ((ParameterizedType) type).getActualTypeArguments()[0];
                        T t;
                        //判断如果是String 或者 List类型的，就直接get方式获取
                        if (typeChild.equals(String.class)) {
//                            t = (T) gson.toJson(jDataObject.getString("result"));
                            t = (T) jDataObject.getString("result");
                        } else {
//                                || (typeChild instanceof ParameterizedType && ((ParameterizedType) typeChild).getRawType() != null && ((ParameterizedType) typeChild).getRawType().equals(List.class))
                            t = gson.fromJson(jDataObject.getString("result"), typeChild);
                        }
                        baseResponse.setResult(t);
                    }
                }
            } else {
                baseResponse.setMessage(jDataObject.getString("result"));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return baseResponse;
    }

    /**
     * 截断输出日志，原因是超过4000会打印不全
     *
     * @param msg 文本
     */
    public static void e(String tag, String msg) {
        if (tag == null || tag.length() == 0
                || msg == null || msg.length() == 0)
            return;

        int segmentSize = 3 * 1024;
        long length = msg.length();
        if (length <= segmentSize) {// 长度小于等于限制直接打印
            Log.e(tag, msg);
        } else {
            while (msg.length() > segmentSize) {// 循环分段打印日志
                String logContent = msg.substring(0, segmentSize);
                msg = msg.replace(logContent, "");
                Log.e(tag, logContent);
            }
            Log.e(tag, msg);// 打印剩余日志
        }
    }

}