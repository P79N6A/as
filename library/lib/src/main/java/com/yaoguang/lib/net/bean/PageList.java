package com.yaoguang.lib.net.bean;

import java.util.ArrayList;

/**
 * 服务器分页规范
 * Created by zhongjh on 2017/6/6.
 */
public class PageList<T> {

    private int pageNo;
    private int pageSize;
    private int totalCount;

    private ArrayList<T> result;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public ArrayList<T> getResult() {
        return result;
    }

    public void setResult(ArrayList<T> result) {
        this.result = result;
    }

}
