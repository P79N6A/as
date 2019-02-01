package com.yaoguang.lib.net;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.BufferedSink;

/**
 * Created by zhongjh on 2017/8/17.
 */

public class RequestBodyCustom extends RequestBody {



    public RequestBodyCustom() {
        super();
    }

    @Override
    public MediaType contentType() {
        return null;
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        int a = 5;
        int b = 6;
    }


}
