package com.yaoguang.lib.qinui;

/**
 * =====================================
 * 作    者: 韦理英
 * 版    本：1.2.9
 * 创建日期：2018/01/29
 * 描    述：七牛token
 * =====================================
 */

public class QiNiuToken {
    /**
     * code : 200
     * info : null
     * data : hs0oc8mcpVZO1uEFyade_J6WcPRIfaJQaxU5OveK:lOKeqmpC2yPmGuFWxvRviaNx9kY=:eyJzY29wZSI6ImltYWdlcyIsImRlYWRsaW5lIjoxNTE3MjIwNjM3fQ==
     */

    private int code;
    private Object info;
    private String data;
    private long time;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getInfo() {
        return info;
    }

    public void setInfo(Object info) {
        this.info = info;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
