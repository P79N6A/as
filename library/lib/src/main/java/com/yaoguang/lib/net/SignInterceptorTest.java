package com.yaoguang.lib.net;

import com.google.gson.Gson;
import com.yaoguang.lib.common.ObjectUtils;
import com.yaoguang.lib.common.aesrsa.AES;
import com.yaoguang.lib.common.aesrsa.EncryUtil;
import com.yaoguang.lib.common.aesrsa.RSA;
import com.yaoguang.lib.common.aesrsa.RandomUtil;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.Random;
import java.util.TreeMap;

import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 加密的参数
 * Created by zhongjh on 2018/2/7.
 */
public class SignInterceptorTest implements Interceptor {

    public static final String serverPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyGffCqoC1vCDLeBvjfuHdw4jo" +
            "hGvubOpQjEhhPzW1PbLSRKsNBLgj+eDGOiZE9BwmEwqy16sMOq0kMlhewTQlRrLJ" +
            "Nlw3L0iogs9WTIGm3el1SuZLyMnMksnV0NCsuq538cPMNppZRwARb7NXmpmh0KM7" +
            "9fJ/1xqnpo1tgRcv4wIDAQAB";

    public static final String clientPrivateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAKbNojYr8KlqKD/y" +
            "COd7QXu3e4TsrHd4sz3XgDYWEZZgYqIjVDcpcnlztwomgjMj9xSxdpyCc85GOGa0" +
            "lva1fNZpG6KXYS1xuFa9G7FRbaACoCL31TRv8t4TNkfQhQ7e2S7ZktqyUePWYLlz" +
            "u8hx5jXdriErRIx1jWK1q1NeEd3NAgMBAAECgYAws7Ob+4JeBLfRy9pbs/ovpCf1" +
            "bKEClQRIlyZBJHpoHKZPzt7k6D4bRfT4irvTMLoQmawXEGO9o3UOT8YQLHdRLitW" +
            "1CYKLy8k8ycyNpB/1L2vP+kHDzmM6Pr0IvkFgnbIFQmXeS5NBV+xOdlAYzuPFkCy" +
            "fUSOKdmt3F/Pbf9EhQJBANrF5Uaxmk7qGXfRV7tCT+f27eAWtYi2h/gJenLrmtke" +
            "Hg7SkgDiYHErJDns85va4cnhaAzAI1eSIHVaXh3JGXcCQQDDL9ns78LNDr/QuHN9" +
            "pmeDdlQfikeDKzW8dMcUIqGVX4WQJMptviZuf3cMvgm9+hDTVLvSePdTlA9YSCF4" +
            "VNPbAkEAvbe54XlpCKBIX7iiLRkPdGiV1qu614j7FqUZlAkvKrPMeywuQygNXHZ+" +
            "HuGWTIUfItQfSFdjDrEBBuPMFGZtdwJAV5N3xyyIjfMJM4AfKYhpN333HrOvhHX1" +
            "xVnsHOew8lGKnvMy9Gx11+xPISN/QYMa24dQQo5OAm0TOXwbsF73MwJAHzqaKZPs" +
            "EN08JunWDOKs3ZS+92maJIm1YGdYf5ipB8/Bm3wElnJsCiAeRqYKmPpAMlCZ5x+Z" +
            "AsuC1sjcp2r7xw==";

    public static final String clientPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCmzaI2K/Cpaig/8gjne0F7t3uE" +
            "7Kx3eLM914A2FhGWYGKiI1Q3KXJ5c7cKJoIzI/cUsXacgnPORjhmtJb2tXzWaRui" +
            "l2EtcbhWvRuxUW2gAqAi99U0b/LeEzZH0IUO3tku2ZLaslHj1mC5c7vIceY13a4h" +
            "K0SMdY1itatTXhHdzQIDAQAB";

    public SignInterceptorTest() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        // 判断该url是否获取服务器公钥，如果是直接获取公钥的，则不需要加密
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        String timestamp = Long.toString(System.currentTimeMillis() / 1000);
        // 转码并且拼接timestamp
        String url = URLDecoder.decode(request.url().toString(), "UTF-8") + "&timestamp=" + timestamp;


        // 获取参数列表
        String[] parts = url.split("\\?");

        // TreeMap里面的数据会按照key值自动升序排列
        TreeMap<String, String> param_map = new TreeMap<>();

        // 获取参数对
        String[] param_pairs = parts[1].split("&");
        for (String pair : param_pairs) {
            String[] param = pair.split("=");
            switch (param.length) {
                case 0:
                    // 没有key,value的参数不进行处理
                    continue;
                case 1:
                    if (param[0].equals("") || param[0] == null){
                        continue;
                    }else {
                        // 如果只有key
                        param_map.put(param[0], null);
                    }
                    break;
                case 2:
                    param_map.put(param[0], param[1]);
                    break;
            }
        }

        // 生成RSA签名
        // 随机值100000
        Random random = new Random();
        int i = random.nextInt(100);
        String sign = EncryUtil.handleRSA(param_map, clientPrivateKey, ObjectUtils.parseString(i));
        param_map.put("signkey", ObjectUtils.parseString(i));
        param_map.put("signRsa", sign);
        Gson gson = new Gson();
        String info = gson.toJson(param_map);
        //随机生成AES密钥
        String aesKey = RandomUtil.getRandom(16);
        //AES加密数据
        String data = AES.encryptToBase64(info, aesKey);

        // 使用RSA算法将商户自己随机生成的AESkey加密
        String encryptkey = null;
        try {
            encryptkey = RSA.encrypt(aesKey, serverPublicKey);
        } catch (Exception e) {
            e.printStackTrace();
        }

        HttpUrl.Builder httpUrlBuilder = request.url().newBuilder();
        Headers.Builder headerBuilder = request.headers().newBuilder();


        // 清除旧参数
        for (String s : param_map.keySet()) {
            httpUrlBuilder.removeAllQueryParameters(s);
        }

        // 添加参数
        TreeMap<String, String> param_mapNew = new TreeMap<>();
        param_mapNew.put("clientPublicKey", clientPublicKey);
        param_mapNew.put("encryptkey", encryptkey);
        param_mapNew.put("data", data);

        assert encryptkey != null;
        httpUrlBuilder.addQueryParameter("data", gson.toJson(param_mapNew));

        for (String s : param_map.keySet()) {
            httpUrlBuilder.addQueryParameter(s, param_map.get(s));
        }


        requestBuilder.headers(headerBuilder.build());
        requestBuilder.url(httpUrlBuilder.build());
        request = requestBuilder.build();
        return chain.proceed(request);
    }


}
