package com.yaoguang.greendao.entity;

/**
 * Created by zhongjh on 2017/11/7.
 */

public class OCR {


    /**
     * reason : 营业执照
     * result : {"company":"北京库斯曼科技有限公司","type":"有限责任公司自然人独资","address":"北京市朝阳区安定门外大街1号11层(安贞孵化器D325号)","holder":"孙先生","reg_capital":"1200万元","start_time":"2017年07月07日","business_term":"2017年07月07日至2047年07月06日","scope":"技术开发、技术推广、技术转让、技术咨询、技术服务；计算机系统服务；基础软件服务；应用软件服务(不含医用软件)；软件开发；产品设计；模型设计；包装装潢设计；教育咨询(不含出国留学咨询及中介服务)；公共关系服务；工艺美术设计；电脑图文设计、制作；企业策划；设计、制作、代理、发布广告；市场调查；企业管理咨询；组织文化艺术交流活动(不含演出)；文艺创作；承办展览展示活动；会议服务；翻译服务；数据处理(数据处理中的银行卡中心、PUE值在1.5以上的云计算数据中心除外)。","credit_code":"91110105M00G38F60"}
     * error_code : 200
     */

    private String reason;
    private ResultBean result;
    private int error_code;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public static class ResultBean {
        /**
         * company : 北京库斯曼科技有限公司
         * type : 有限责任公司自然人独资
         * address : 北京市朝阳区安定门外大街1号11层(安贞孵化器D325号)
         * holder : 孙先生
         * reg_capital : 1200万元
         * start_time : 2017年07月07日
         * business_term : 2017年07月07日至2047年07月06日
         * scope : 技术开发、技术推广、技术转让、技术咨询、技术服务；计算机系统服务；基础软件服务；应用软件服务(不含医用软件)；软件开发；产品设计；模型设计；包装装潢设计；教育咨询(不含出国留学咨询及中介服务)；公共关系服务；工艺美术设计；电脑图文设计、制作；企业策划；设计、制作、代理、发布广告；市场调查；企业管理咨询；组织文化艺术交流活动(不含演出)；文艺创作；承办展览展示活动；会议服务；翻译服务；数据处理(数据处理中的银行卡中心、PUE值在1.5以上的云计算数据中心除外)。
         * credit_code : 91110105M00G38F60
         */

        private String company;
        private String type;
        private String address;
        private String holder;
        private String reg_capital;
        private String start_time;
        private String business_term;
        private String scope;
        private String credit_code;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getHolder() {
            return holder;
        }

        public void setHolder(String holder) {
            this.holder = holder;
        }

        public String getReg_capital() {
            return reg_capital;
        }

        public void setReg_capital(String reg_capital) {
            this.reg_capital = reg_capital;
        }

        public String getStart_time() {
            return start_time;
        }

        public void setStart_time(String start_time) {
            this.start_time = start_time;
        }

        public String getBusiness_term() {
            return business_term;
        }

        public void setBusiness_term(String business_term) {
            this.business_term = business_term;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public String getCredit_code() {
            return credit_code;
        }

        public void setCredit_code(String credit_code) {
            this.credit_code = credit_code;
        }
    }
}
