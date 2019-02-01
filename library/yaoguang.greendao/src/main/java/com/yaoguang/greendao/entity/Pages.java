package com.yaoguang.greendao.entity;

/**
 * Created by Administrator on 2017/7/12 0012.
 */

public class Pages<T> {
    /**
     * pageNo : 1
     * pageSize : 10
     * orderBy :
     * order : asc
     * autoCount : true
     * result : [{"id":"3e3d94af8b034b00a42abc644a1f49f5","mark":0,"orderId":"DGZYG201707111015091","driverId":"95add1076a8d4baca136d9037dad9b6b","entrustCompany":"广州瑶光软件科技有限公司","phone":"020-22003300","entrustPeople":"gzyg","mobile":"13800138000","orderStatus":0,"truckBillFirstType":0,"truckBillSecondType":0,"deliveryTime":"05-27 00:00 装货 ","vehiclePrice":2000,"contQty":"1x20TK","shipCompanyOne":"中远海","shipCompanyTwo":"","deliveryRoute":["东莞理文码头","佛山南海","宿迁码头"],"vehicleFlag":0,"createTime":"2017-07-11 10:22:24","receiveTime":"","finishTime":"","truckBillsFirst":"GZYG201705311126121_001","truckBillsSecond":"","remark":"","refuseRemark":"","orderType":"吉出重回","orderMark":""}]
     * totalCount : 1
     * nextPage : 2
     * hasNext : false
     * prePage : 1
     * totalPages : 1
     * hasPre : false
     * inverseOrder : desc
     * first : 0
     * firstSetted : true
     * pageSizeSetted : true
     * orderBySetted : false
     */

    private int pageNo;
    private int pageSize;
    private String orderBy;
    private String order;
    private boolean autoCount;
    private int totalCount;
    private int nextPage;
    private boolean hasNext;
    private int prePage;
    private int totalPages;
    private boolean hasPre;
    private String inverseOrder;
    private int first;
    private boolean firstSetted;
    private boolean pageSizeSetted;
    private boolean orderBySetted;
    private T result;

    public int getPageNo() {
        return pageNo;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
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

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public boolean isAutoCount() {
        return autoCount;
    }

    public void setAutoCount(boolean autoCount) {
        this.autoCount = autoCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isHasNext() {
        return hasNext;
    }

    public void setHasNext(boolean hasNext) {
        this.hasNext = hasNext;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public boolean isHasPre() {
        return hasPre;
    }

    public void setHasPre(boolean hasPre) {
        this.hasPre = hasPre;
    }

    public String getInverseOrder() {
        return inverseOrder;
    }

    public void setInverseOrder(String inverseOrder) {
        this.inverseOrder = inverseOrder;
    }

    public int getFirst() {
        return first;
    }

    public void setFirst(int first) {
        this.first = first;
    }

    public boolean isFirstSetted() {
        return firstSetted;
    }

    public void setFirstSetted(boolean firstSetted) {
        this.firstSetted = firstSetted;
    }

    public boolean isPageSizeSetted() {
        return pageSizeSetted;
    }

    public void setPageSizeSetted(boolean pageSizeSetted) {
        this.pageSizeSetted = pageSizeSetted;
    }

    public boolean isOrderBySetted() {
        return orderBySetted;
    }

    public void setOrderBySetted(boolean orderBySetted) {
        this.orderBySetted = orderBySetted;
    }
}
