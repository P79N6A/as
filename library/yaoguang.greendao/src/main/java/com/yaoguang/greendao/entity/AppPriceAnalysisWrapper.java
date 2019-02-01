package com.yaoguang.greendao.entity;


/**
 * APP拖车报价包装类
 *
 * @author wangxiaohui
 * @date 2017年6月12日 上午11:15:16
 */
public class AppPriceAnalysisWrapper  {


    private PriceAnalysis priceAnalysis;
    // 柜型
    private String cont;

    // 始驳费
    private String startFee;

    // 达驳费
    private String endFee;

    // 海运费及其他费用
    private String otherFee;

    // 总费用
    private String totalFee;

    public String getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(String totalFee) {
        this.totalFee = totalFee;
    }

    public PriceAnalysis getPriceAnalysis() {
        return priceAnalysis;
    }

    public void setPriceAnalysis(PriceAnalysis priceAnalysis) {
        this.priceAnalysis = priceAnalysis;
    }

    public String getCont() {
        return cont;
    }

    public void setCont(String cont) {
        this.cont = cont;
    }

    public String getStartFee() {
        return startFee;
    }

    public void setStartFee(String startFee) {
        this.startFee = startFee;
    }

    public String getEndFee() {
        return endFee;
    }

    public void setEndFee(String endFee) {
        this.endFee = endFee;
    }

    public String getOtherFee() {
        return otherFee;
    }

    public void setOtherFee(String otherFee) {
        this.otherFee = otherFee;
    }

}
