package com.yaoguang.lib.net.bean;

/**
 * 服务器的单个实体类
 * Created by zhongjh on 2017/3/30.
 */
public class BaseResponse<T> {

    private String state;

    private T result;

    private String message;

    private int totalCount;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

}
