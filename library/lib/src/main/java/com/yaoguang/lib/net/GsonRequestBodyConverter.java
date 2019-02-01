package com.yaoguang.lib.net;


import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.aesrsa.AES;
import com.yaoguang.lib.common.aesrsa.EncryUtil;
import com.yaoguang.lib.common.aesrsa.RSA;
import com.yaoguang.lib.common.aesrsa.RandomUtil;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Random;
import java.util.TreeMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Converter;

import static com.yaoguang.lib.net.SignInterceptor.clientPrivateKey;
import static com.yaoguang.lib.net.SignInterceptor.clientPublicKey;
import static com.yaoguang.lib.net.SignInterceptor.serverPublicKey;

/**
 * 自定义的请求数据源
 * @param <T>
 */
final class GsonRequestBodyConverter<T> implements Converter<T, RequestBody> {
    private static final MediaType MEDIA_TYPE = MediaType.parse("application/json; charset=UTF-8;");
    private static final Charset UTF_8 = Charset.forName("UTF-8");

    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonRequestBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public RequestBody convert(T value) throws IOException {
//        Buffer buffer = new Buffer();
//        Writer writer = new OutputStreamWriter(buffer.outputStream(), UTF_8);
//        JsonWriter jsonWriter = gson.newJsonWriter(writer);
//        adapter.write(jsonWriter, value);
//        jsonWriter.close();
//        return RequestBodyCustom.create(MEDIA_TYPE, buffer.readByteString());

//        // TreeMap里面的数据会按照key值自动升序排列
//        Gson gson = new Gson();
//        TreeMap<String, String> param_map = new TreeMap<>();
//        TreeMap<String, String> param_mapNew = new TreeMap<>();
//        // 随机值100000
//        Random random = new Random();
//        int i = random.nextInt(100);
//        param_map.put("data",gson.toJson(value));
//        String sign = EncryUtil.handleRSA(param_map, clientPrivateKey, ObjectUtils.parseString(i));
//        param_map.put("signRsa", sign);
//        param_map.put("signkey", ObjectUtils.parseString(i));
//
//        String info = gson.toJson(param_map);
//        //随机生成AES密钥
//        String aesKey = RandomUtil.getRandom(16);
//        //AES加密数据
//        String data = AES.encryptToBase64(info, aesKey);
//        // 使用RSA算法将商户自己随机生成的AESkey加密
//        String encryptkey = null;
//        try {
//            encryptkey = RSA.encrypt(aesKey, serverPublicKey);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        param_mapNew.put("clientPublicKey", clientPublicKey);
//        param_mapNew.put("encryptkey", encryptkey);
//        param_mapNew.put("data",data);
//        String postBody = gson.toJson(param_mapNew);
//        return RequestBodyCustom.create(MEDIA_TYPE, postBody);

        return RequestBodyCustom.create(MEDIA_TYPE, gson.toJson(value));
    }

}