package com.yaoguang.greendao.entity.company;


import com.yaoguang.greendao.DateUtils;



public class AppCompanyBanDanSonoWrapper {

    // 货柜id
    private String id;
    // 柜号
    private String contNo;
    // 封号
    private String sealNo;
    // 办单时间
    private String bdDate;
    // 船到时间
    private String cdDate;
    // 打单时间
    private String mtddDate;
    // 办单时间(文本)
    private String bdDateStr;
    // 船到时间(文本)
    private String cdDateStr;
    // 打单时间(文本)
    private String mtddDateStr;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContNo() {
        return contNo;
    }

    public void setContNo(String contNo) {
        this.contNo = contNo;
    }

    public String getSealNo() {
        return sealNo;
    }

    public void setSealNo(String sealNo) {
        this.sealNo = sealNo;
    }

    public String getBdDate() {
        return bdDate;
    }

    public void setBdDate(String bdDate) {
        // 格式化
        this.bdDate = bdDate;
    }

    public String getCdDate() {
        return cdDate;
    }

    public void setCdDate(String cdDate) {
        this.cdDate = cdDate;
    }

    public String getMtddDate() {
        return mtddDate;
    }

    public void setMtddDate(String mtddDate) {
        this.mtddDate = mtddDate;
    }

    public String getBdDateStr(){
        if (bdDateStr == null)
            bdDateStr = DateUtils.stringToString(bdDate,DateUtils.MM_DD_HH_MM);
        return bdDateStr;
    }

    public String getCdDateStr(){
        if (cdDateStr == null)
            cdDateStr = DateUtils.stringToString(cdDate,DateUtils.MM_DD_HH_MM);
        return cdDateStr;
    }

    public String getMtddDateStr(){
        if (mtddDateStr == null)
            mtddDateStr = DateUtils.stringToString(mtddDate,DateUtils.MM_DD_HH_MM);
        return mtddDateStr;
    }

}
