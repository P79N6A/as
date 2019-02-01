package com.yaoguang.datasource.api;

import com.yaoguang.greendao.entity.OCR;

import java.util.HashMap;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * 第三方api
 * Created by zhongjh on 2017/11/7.
 */
public interface ThirdApi {

    @Multipart
    @POST("{url}")
    Observable<OCR> upLoadsImg(@Path("url") String url, @Header("Authorization") String Authorization, @Part MultipartBody.Part photo);


    //    @POST("{url}")
    @POST("http://idcardocrs.market.alicloudapi.com/OCR?")
    Observable<OCR> upLoadsImg(@Header("Authorization") String Authorization, @Header("Host") String Host, @Header("X-Ca-Timestamp") String XCaTimestamp, @Header("gateway_channel") String http,
                               @Header("X-Ca-Request-Mode") String debug, @Header("X-Ca-Key") String XCaKey, @Header("X-Ca-Stage") String RELEASE, @Header("Content-Type") String ContentType, @Query("pic") String pic);

    @POST("http://idcardocrs.market.alicloudapi.com/OCR?")
    Observable<OCR> upLoadsImg(@Header("Authorization") String Authorization, @Header("Content-Type") String ContentType, @Body String pic);

    @FormUrlEncoded
    @POST("http://idcardocrs.market.alicloudapi.com/businessOCR?")
    Observable<OCR> upLoadsImg(@Header("Authorization") String Authorization, @FieldMap HashMap<String, String> hashMap);

//    @POST("https://dm-58.data.aliyun.com/rest/160601/ocr/ocr_business_license.json")
//    Observable<String> upLoadsImg2(@Header("Authorization") String Authorization, @Body String bodys);
}


