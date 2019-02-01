package com.yaoguang.greendao.entity.driver;

import java.util.List;

/**
 * Created by zhongjh on 2019/1/8.
 */

public class test {


    /**
     * state : 200
     * result : [{"name":"出车","nodeType":0,"mark":"出车","address":null,"finishStatus":3,"nodes":[{"id":"0d1e16e4987a4d7cb7a9b81d6022a05d","driverOrderId":"DSZWL201812271017424541","orderSn":"TC_SZWL18120006","orderSnSecond":null,"parentNumber":0,"parentName":"出车","childNumber":0,"childName":"出车","sono1Id":null,"sono2Id":null,"addressId":null,"detailFlag":0,"detailSuccess":1,"remark":null,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":null,"isValid":1,"driverOrderNodeList":[{"id":"2b5321e653574649b9e237b131eccd26","driverOrderId":"DSZWL201812271017424541","orderNodeId":"0d1e16e4987a4d7cb7a9b81d6022a05d","orderSn":null,"sono1Id":null,"sono2Id":null,"number":0,"nodeName":"出车","lon":113.39576199,"lat":23.12808811,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":null,"isFinished":1,"detailFlag":0,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:17:53","isValid":1,"currentOrder":null}],"truckGoodsAddr":{"id":null,"flag":null,"address":null,"contacts":null,"mobile":null,"tel":null,"remarks":null,"goodsUnit":null,"unitFax":null,"goodsPriority":null,"site":null,"area":null,"location":null,"importAddrId":null},"isEditable":0}]},{"name":"广州本港","nodeType":1,"mark":"提柜","address":"","finishStatus":3,"nodes":[{"id":"f7ca625d5db24dbda896fdfd628829c6","driverOrderId":"DSZWL201812271017424541","orderSn":null,"orderSnSecond":null,"parentNumber":1,"parentName":"提柜","childNumber":0,"childName":"广州本港","sono1Id":"29ef791e278645a286095ddac0d4d01f","sono2Id":null,"addressId":"广州本港","detailFlag":1,"detailSuccess":1,"remark":null,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":null,"isValid":1,"driverOrderNodeList":[{"id":"7117514b0b24443a8c3cab0c7eac05ae","driverOrderId":"DSZWL201812271017424541","orderNodeId":"f7ca625d5db24dbda896fdfd628829c6","orderSn":null,"sono1Id":null,"sono2Id":null,"number":0,"nodeName":"到达广州本港","lon":113.39576199,"lat":23.12808811,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":"正在前往提柜点,到达这里","isFinished":1,"detailFlag":1,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:17:57","isValid":1,"currentOrder":null},{"id":"231c3f47586e4d13873b207419484cd0","driverOrderId":"DSZWL201812271017424541","orderNodeId":"f7ca625d5db24dbda896fdfd628829c6","orderSn":null,"sono1Id":null,"sono2Id":null,"number":2,"nodeName":"离开广州本港","lon":113.39576199,"lat":23.12808811,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":"正在离开提柜点,离开这里","isFinished":1,"detailFlag":0,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:17:59","isValid":1,"currentOrder":null}],"truckGoodsAddr":{"id":"广州本港","flag":null,"address":"","contacts":null,"mobile":null,"tel":null,"remarks":null,"goodsUnit":null,"unitFax":null,"goodsPriority":null,"site":{"lat":23.077015,"lon":113.509225},"area":[{"lat":23.080281,"lon":113.51157},{"lat":23.080913,"lon":113.505647},{"lat":23.077834,"lon":113.504188},{"lat":23.075149,"lon":113.507965},{"lat":23.076649,"lon":113.514059}],"location":null,"importAddrId":null},"isEditable":0},{"id":"b01eecf692cf4ceb8275453c08e8ab88","driverOrderId":"DSZWL201812271017424541","orderSn":null,"orderSnSecond":null,"parentNumber":1,"parentName":"提柜","childNumber":1,"childName":"广州本港","sono1Id":null,"sono2Id":"e200b069213e4cf59555408ab77f1241","addressId":"广州本港","detailFlag":1,"detailSuccess":1,"remark":null,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":null,"isValid":1,"driverOrderNodeList":[{"id":"4602813180c64cfc8360bc2b910411ec","driverOrderId":"DSZWL201812271017424541","orderNodeId":"b01eecf692cf4ceb8275453c08e8ab88","orderSn":null,"sono1Id":null,"sono2Id":null,"number":0,"nodeName":"到达广州本港","lon":113.39576199,"lat":23.12808811,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":"正在前往提柜点,到达这里","isFinished":1,"detailFlag":1,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:17:58","isValid":1,"currentOrder":null},{"id":"e5e1a0b595c742a699960e3597821ac7","driverOrderId":"DSZWL201812271017424541","orderNodeId":"b01eecf692cf4ceb8275453c08e8ab88","orderSn":null,"sono1Id":null,"sono2Id":null,"number":2,"nodeName":"离开广州本港","lon":113.39576199,"lat":23.12808811,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":"正在离开提柜点,离开这里","isFinished":1,"detailFlag":0,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:17:59","isValid":1,"currentOrder":null}],"truckGoodsAddr":{"id":"广州本港","flag":null,"address":"","contacts":null,"mobile":null,"tel":null,"remarks":null,"goodsUnit":null,"unitFax":null,"goodsPriority":null,"site":{"lat":23.077015,"lon":113.509225},"area":[{"lat":23.080281,"lon":113.51157},{"lat":23.080913,"lon":113.505647},{"lat":23.077834,"lon":113.504188},{"lat":23.075149,"lon":113.507965},{"lat":23.076649,"lon":113.514059}],"location":null,"importAddrId":null},"isEditable":0}]},{"name":"新秀丽","nodeType":2,"mark":"装货","address":"广东省广州市天河区车陂街道3号","finishStatus":3,"nodes":[{"id":"0b79586365f94f238c0ec2ae0e0ebbf0","driverOrderId":"DSZWL201812271017424541","orderSn":"TC_SZWL18120006","orderSnSecond":null,"parentNumber":2,"parentName":"装货","childNumber":0,"childName":"新秀丽","sono1Id":"29ef791e278645a286095ddac0d4d01f","sono2Id":"e200b069213e4cf59555408ab77f1241","addressId":"ac2e24fec0e2407cac4fff8e84df0a1e","detailFlag":1,"detailSuccess":1,"remark":null,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":null,"isValid":1,"driverOrderNodeList":[{"id":"dea28e82dc3449d69c93eebe6175e70d","driverOrderId":"DSZWL201812271017424541","orderNodeId":"0b79586365f94f238c0ec2ae0e0ebbf0","orderSn":"TC_SZWL18120006","sono1Id":null,"sono2Id":null,"number":0,"nodeName":"到达新秀丽","lon":113.39570611,"lat":23.12812039,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":"正在前往装货门点,到达这里","isFinished":1,"detailFlag":1,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:20:22","isValid":1,"currentOrder":"TC_SZWL18120006"},{"id":"5f1c2cdd34fa4bab88f6180d6855b257","driverOrderId":"DSZWL201812271017424541","orderNodeId":"0b79586365f94f238c0ec2ae0e0ebbf0","orderSn":"TC_SZWL18120006","sono1Id":null,"sono2Id":null,"number":2,"nodeName":"离开新秀丽","lon":113.39570611,"lat":23.12812039,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":"正在离开装货门点,离开这里","isFinished":1,"detailFlag":0,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:20:24","isValid":1,"currentOrder":"TC_SZWL18120006"}],"truckGoodsAddr":{"id":"ac2e24fec0e2407cac4fff8e84df0a1e","flag":0,"address":"广东省广州市天河区车陂街道3号","contacts":"泡参","mobile":"13650905161","tel":"020-4556285","remarks":"小心火烛，需回款","goodsUnit":"新秀丽","unitFax":"020-4556285","goodsPriority":"0","site":{"lat":23.113573,"lon":113.400009},"area":[{"lat":23.112675,"lon":113.400986},{"lat":23.112675,"lon":113.399032},{"lat":23.114471,"lon":113.399032},{"lat":23.114471,"lon":113.400986}],"location":"{\"area\":[{\"lat\":23.112675,\"lon\":113.400986},{\"lat\":23.112675,\"lon\":113.399032},{\"lat\":23.114471,\"lon\":113.399032},{\"lat\":23.114471,\"lon\":113.400986}],\"site\":{\"lat\":23.113573,\"lon\":113.400009}}","importAddrId":"27b3776f089d4edabc3164b6b1a0c63a"},"isEditable":0}]},{"name":"青岛黄岛港","nodeType":3,"mark":"还柜","address":null,"finishStatus":3,"nodes":[{"id":"78bda44ebf0b4fd29d532d5b8453360b","driverOrderId":"DSZWL201812271017424541","orderSn":null,"orderSnSecond":null,"parentNumber":3,"parentName":"还柜","childNumber":0,"childName":"青岛黄岛港","sono1Id":"29ef791e278645a286095ddac0d4d01f","sono2Id":null,"addressId":"青岛黄岛港","detailFlag":1,"detailSuccess":1,"remark":null,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":null,"isValid":1,"driverOrderNodeList":[{"id":"5bb35595866a45f1a5ac876b28b4459e","driverOrderId":"DSZWL201812271017424541","orderNodeId":"78bda44ebf0b4fd29d532d5b8453360b","orderSn":null,"sono1Id":null,"sono2Id":null,"number":0,"nodeName":"到达青岛黄岛港","lon":113.39570611,"lat":23.12812039,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":"正在前往还柜点,到达这里","isFinished":1,"detailFlag":1,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:21:24","isValid":1,"currentOrder":null},{"id":"c9a1e714d7db49c0ae943e59b44f0ebf","driverOrderId":"DSZWL201812271017424541","orderNodeId":"78bda44ebf0b4fd29d532d5b8453360b","orderSn":null,"sono1Id":null,"sono2Id":null,"number":2,"nodeName":"离开青岛黄岛港","lon":113.39570611,"lat":23.12812039,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":"正在离开还柜点,离开这里","isFinished":1,"detailFlag":0,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:22:33","isValid":1,"currentOrder":null}],"truckGoodsAddr":{"id":"青岛黄岛港","flag":null,"address":null,"contacts":null,"mobile":null,"tel":null,"remarks":null,"goodsUnit":null,"unitFax":null,"goodsPriority":null,"site":null,"area":null,"location":null,"importAddrId":null},"isEditable":0},{"id":"6a7f4846bfdb421fb2dd4fd897bc70f3","driverOrderId":"DSZWL201812271017424541","orderSn":null,"orderSnSecond":null,"parentNumber":3,"parentName":"还柜","childNumber":1,"childName":"青岛黄岛港","sono1Id":null,"sono2Id":"e200b069213e4cf59555408ab77f1241","addressId":"青岛黄岛港","detailFlag":1,"detailSuccess":1,"remark":null,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":null,"isValid":1,"driverOrderNodeList":[{"id":"6ae162f0861a404ca111143a2b8921f8","driverOrderId":"DSZWL201812271017424541","orderNodeId":"6a7f4846bfdb421fb2dd4fd897bc70f3","orderSn":null,"sono1Id":null,"sono2Id":null,"number":0,"nodeName":"到达青岛黄岛港","lon":113.39570611,"lat":23.12812039,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":"正在前往还柜点,到达这里","isFinished":1,"detailFlag":1,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:21:24","isValid":1,"currentOrder":null},{"id":"72fb2c6f8bad447198a954d39eabd23c","driverOrderId":"DSZWL201812271017424541","orderNodeId":"6a7f4846bfdb421fb2dd4fd897bc70f3","orderSn":null,"sono1Id":null,"sono2Id":null,"number":2,"nodeName":"离开青岛黄岛港","lon":113.39570611,"lat":23.12812039,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":"正在离开还柜点,离开这里","isFinished":1,"detailFlag":0,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:22:33","isValid":1,"currentOrder":null}],"truckGoodsAddr":{"id":"青岛黄岛港","flag":null,"address":null,"contacts":null,"mobile":null,"tel":null,"remarks":null,"goodsUnit":null,"unitFax":null,"goodsPriority":null,"site":null,"area":null,"location":null,"importAddrId":null},"isEditable":0}]},{"name":"完成","nodeType":4,"mark":"完成","address":null,"finishStatus":3,"nodes":[{"id":"1f7dd15f8ac24d539d16efd5bb620a52","driverOrderId":"DSZWL201812271017424541","orderSn":"TC_SZWL18120006","orderSnSecond":null,"parentNumber":4,"parentName":"完成","childNumber":0,"childName":"完成","sono1Id":null,"sono2Id":null,"addressId":null,"detailFlag":0,"detailSuccess":1,"remark":null,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":null,"isValid":1,"driverOrderNodeList":[{"id":"67db07efb51b4dd5bf4988c3423b6b69","driverOrderId":"DSZWL201812271017424541","orderNodeId":"1f7dd15f8ac24d539d16efd5bb620a52","orderSn":null,"sono1Id":null,"sono2Id":null,"number":0,"nodeName":"完成","lon":113.39570611,"lat":23.12812039,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":null,"isFinished":1,"detailFlag":0,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:22:35","isValid":1,"currentOrder":null}],"truckGoodsAddr":{"id":null,"flag":null,"address":null,"contacts":null,"mobile":null,"tel":null,"remarks":null,"goodsUnit":null,"unitFax":null,"goodsPriority":null,"site":null,"area":null,"location":null,"importAddrId":null},"isEditable":0}]}]
     */

    private String state;
    private List<ResultBean> result;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean {
        /**
         * name : 出车
         * nodeType : 0
         * mark : 出车
         * address : null
         * finishStatus : 3
         * nodes : [{"id":"0d1e16e4987a4d7cb7a9b81d6022a05d","driverOrderId":"DSZWL201812271017424541","orderSn":"TC_SZWL18120006","orderSnSecond":null,"parentNumber":0,"parentName":"出车","childNumber":0,"childName":"出车","sono1Id":null,"sono2Id":null,"addressId":null,"detailFlag":0,"detailSuccess":1,"remark":null,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":null,"isValid":1,"driverOrderNodeList":[{"id":"2b5321e653574649b9e237b131eccd26","driverOrderId":"DSZWL201812271017424541","orderNodeId":"0d1e16e4987a4d7cb7a9b81d6022a05d","orderSn":null,"sono1Id":null,"sono2Id":null,"number":0,"nodeName":"出车","lon":113.39576199,"lat":23.12808811,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":null,"isFinished":1,"detailFlag":0,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:17:53","isValid":1,"currentOrder":null}],"truckGoodsAddr":{"id":null,"flag":null,"address":null,"contacts":null,"mobile":null,"tel":null,"remarks":null,"goodsUnit":null,"unitFax":null,"goodsPriority":null,"site":null,"area":null,"location":null,"importAddrId":null},"isEditable":0}]
         */

        private String name;
        private int nodeType;
        private String mark;
        private Object address;
        private int finishStatus;
        private List<NodesBean> nodes;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNodeType() {
            return nodeType;
        }

        public void setNodeType(int nodeType) {
            this.nodeType = nodeType;
        }

        public String getMark() {
            return mark;
        }

        public void setMark(String mark) {
            this.mark = mark;
        }

        public Object getAddress() {
            return address;
        }

        public void setAddress(Object address) {
            this.address = address;
        }

        public int getFinishStatus() {
            return finishStatus;
        }

        public void setFinishStatus(int finishStatus) {
            this.finishStatus = finishStatus;
        }

        public List<NodesBean> getNodes() {
            return nodes;
        }

        public void setNodes(List<NodesBean> nodes) {
            this.nodes = nodes;
        }

        public static class NodesBean {
            /**
             * id : 0d1e16e4987a4d7cb7a9b81d6022a05d
             * driverOrderId : DSZWL201812271017424541
             * orderSn : TC_SZWL18120006
             * orderSnSecond : null
             * parentNumber : 0
             * parentName : 出车
             * childNumber : 0
             * childName : 出车
             * sono1Id : null
             * sono2Id : null
             * addressId : null
             * detailFlag : 0
             * detailSuccess : 1
             * remark : null
             * createdBy : 060c26053db249c2878e7fe18d38ae16
             * created : 2018-12-27 10:17:43
             * updated : null
             * isValid : 1
             * driverOrderNodeList : [{"id":"2b5321e653574649b9e237b131eccd26","driverOrderId":"DSZWL201812271017424541","orderNodeId":"0d1e16e4987a4d7cb7a9b81d6022a05d","orderSn":null,"sono1Id":null,"sono2Id":null,"number":0,"nodeName":"出车","lon":113.39576199,"lat":23.12808811,"address":"广东省广州市天河区车陂北街大岗二巷靠近富力·天禧","remark":null,"isFinished":1,"detailFlag":0,"detailSuccess":0,"createdBy":"060c26053db249c2878e7fe18d38ae16","created":"2018-12-27 10:17:43","updated":"2018-12-27 10:17:53","isValid":1,"currentOrder":null}]
             * truckGoodsAddr : {"id":null,"flag":null,"address":null,"contacts":null,"mobile":null,"tel":null,"remarks":null,"goodsUnit":null,"unitFax":null,"goodsPriority":null,"site":null,"area":null,"location":null,"importAddrId":null}
             * isEditable : 0
             */

            private String id;
            private String driverOrderId;
            private String orderSn;
            private Object orderSnSecond;
            private int parentNumber;
            private String parentName;
            private int childNumber;
            private String childName;
            private Object sono1Id;
            private Object sono2Id;
            private Object addressId;
            private int detailFlag;
            private int detailSuccess;
            private Object remark;
            private String createdBy;
            private String created;
            private Object updated;
            private int isValid;
            private TruckGoodsAddrBean truckGoodsAddr;
            private int isEditable;
            private List<DriverOrderNodeListBean> driverOrderNodeList;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDriverOrderId() {
                return driverOrderId;
            }

            public void setDriverOrderId(String driverOrderId) {
                this.driverOrderId = driverOrderId;
            }

            public String getOrderSn() {
                return orderSn;
            }

            public void setOrderSn(String orderSn) {
                this.orderSn = orderSn;
            }

            public Object getOrderSnSecond() {
                return orderSnSecond;
            }

            public void setOrderSnSecond(Object orderSnSecond) {
                this.orderSnSecond = orderSnSecond;
            }

            public int getParentNumber() {
                return parentNumber;
            }

            public void setParentNumber(int parentNumber) {
                this.parentNumber = parentNumber;
            }

            public String getParentName() {
                return parentName;
            }

            public void setParentName(String parentName) {
                this.parentName = parentName;
            }

            public int getChildNumber() {
                return childNumber;
            }

            public void setChildNumber(int childNumber) {
                this.childNumber = childNumber;
            }

            public String getChildName() {
                return childName;
            }

            public void setChildName(String childName) {
                this.childName = childName;
            }

            public Object getSono1Id() {
                return sono1Id;
            }

            public void setSono1Id(Object sono1Id) {
                this.sono1Id = sono1Id;
            }

            public Object getSono2Id() {
                return sono2Id;
            }

            public void setSono2Id(Object sono2Id) {
                this.sono2Id = sono2Id;
            }

            public Object getAddressId() {
                return addressId;
            }

            public void setAddressId(Object addressId) {
                this.addressId = addressId;
            }

            public int getDetailFlag() {
                return detailFlag;
            }

            public void setDetailFlag(int detailFlag) {
                this.detailFlag = detailFlag;
            }

            public int getDetailSuccess() {
                return detailSuccess;
            }

            public void setDetailSuccess(int detailSuccess) {
                this.detailSuccess = detailSuccess;
            }

            public Object getRemark() {
                return remark;
            }

            public void setRemark(Object remark) {
                this.remark = remark;
            }

            public String getCreatedBy() {
                return createdBy;
            }

            public void setCreatedBy(String createdBy) {
                this.createdBy = createdBy;
            }

            public String getCreated() {
                return created;
            }

            public void setCreated(String created) {
                this.created = created;
            }

            public Object getUpdated() {
                return updated;
            }

            public void setUpdated(Object updated) {
                this.updated = updated;
            }

            public int getIsValid() {
                return isValid;
            }

            public void setIsValid(int isValid) {
                this.isValid = isValid;
            }

            public TruckGoodsAddrBean getTruckGoodsAddr() {
                return truckGoodsAddr;
            }

            public void setTruckGoodsAddr(TruckGoodsAddrBean truckGoodsAddr) {
                this.truckGoodsAddr = truckGoodsAddr;
            }

            public int getIsEditable() {
                return isEditable;
            }

            public void setIsEditable(int isEditable) {
                this.isEditable = isEditable;
            }

            public List<DriverOrderNodeListBean> getDriverOrderNodeList() {
                return driverOrderNodeList;
            }

            public void setDriverOrderNodeList(List<DriverOrderNodeListBean> driverOrderNodeList) {
                this.driverOrderNodeList = driverOrderNodeList;
            }

            public static class TruckGoodsAddrBean {
                /**
                 * id : null
                 * flag : null
                 * address : null
                 * contacts : null
                 * mobile : null
                 * tel : null
                 * remarks : null
                 * goodsUnit : null
                 * unitFax : null
                 * goodsPriority : null
                 * site : null
                 * area : null
                 * location : null
                 * importAddrId : null
                 */

                private Object id;
                private Object flag;
                private Object address;
                private Object contacts;
                private Object mobile;
                private Object tel;
                private Object remarks;
                private Object goodsUnit;
                private Object unitFax;
                private Object goodsPriority;
                private Object site;
                private Object area;
                private Object location;
                private Object importAddrId;

                public Object getId() {
                    return id;
                }

                public void setId(Object id) {
                    this.id = id;
                }

                public Object getFlag() {
                    return flag;
                }

                public void setFlag(Object flag) {
                    this.flag = flag;
                }

                public Object getAddress() {
                    return address;
                }

                public void setAddress(Object address) {
                    this.address = address;
                }

                public Object getContacts() {
                    return contacts;
                }

                public void setContacts(Object contacts) {
                    this.contacts = contacts;
                }

                public Object getMobile() {
                    return mobile;
                }

                public void setMobile(Object mobile) {
                    this.mobile = mobile;
                }

                public Object getTel() {
                    return tel;
                }

                public void setTel(Object tel) {
                    this.tel = tel;
                }

                public Object getRemarks() {
                    return remarks;
                }

                public void setRemarks(Object remarks) {
                    this.remarks = remarks;
                }

                public Object getGoodsUnit() {
                    return goodsUnit;
                }

                public void setGoodsUnit(Object goodsUnit) {
                    this.goodsUnit = goodsUnit;
                }

                public Object getUnitFax() {
                    return unitFax;
                }

                public void setUnitFax(Object unitFax) {
                    this.unitFax = unitFax;
                }

                public Object getGoodsPriority() {
                    return goodsPriority;
                }

                public void setGoodsPriority(Object goodsPriority) {
                    this.goodsPriority = goodsPriority;
                }

                public Object getSite() {
                    return site;
                }

                public void setSite(Object site) {
                    this.site = site;
                }

                public Object getArea() {
                    return area;
                }

                public void setArea(Object area) {
                    this.area = area;
                }

                public Object getLocation() {
                    return location;
                }

                public void setLocation(Object location) {
                    this.location = location;
                }

                public Object getImportAddrId() {
                    return importAddrId;
                }

                public void setImportAddrId(Object importAddrId) {
                    this.importAddrId = importAddrId;
                }
            }

            public static class DriverOrderNodeListBean {
                /**
                 * id : 2b5321e653574649b9e237b131eccd26
                 * driverOrderId : DSZWL201812271017424541
                 * orderNodeId : 0d1e16e4987a4d7cb7a9b81d6022a05d
                 * orderSn : null
                 * sono1Id : null
                 * sono2Id : null
                 * number : 0
                 * nodeName : 出车
                 * lon : 113.39576199
                 * lat : 23.12808811
                 * address : 广东省广州市天河区车陂北街大岗二巷靠近富力·天禧
                 * remark : null
                 * isFinished : 1
                 * detailFlag : 0
                 * detailSuccess : 0
                 * createdBy : 060c26053db249c2878e7fe18d38ae16
                 * created : 2018-12-27 10:17:43
                 * updated : 2018-12-27 10:17:53
                 * isValid : 1
                 * currentOrder : null
                 */

                private String id;
                private String driverOrderId;
                private String orderNodeId;
                private Object orderSn;
                private Object sono1Id;
                private Object sono2Id;
                private int number;
                private String nodeName;
                private double lon;
                private double lat;
                private String address;
                private Object remark;
                private int isFinished;
                private int detailFlag;
                private int detailSuccess;
                private String createdBy;
                private String created;
                private String updated;
                private int isValid;
                private Object currentOrder;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getDriverOrderId() {
                    return driverOrderId;
                }

                public void setDriverOrderId(String driverOrderId) {
                    this.driverOrderId = driverOrderId;
                }

                public String getOrderNodeId() {
                    return orderNodeId;
                }

                public void setOrderNodeId(String orderNodeId) {
                    this.orderNodeId = orderNodeId;
                }

                public Object getOrderSn() {
                    return orderSn;
                }

                public void setOrderSn(Object orderSn) {
                    this.orderSn = orderSn;
                }

                public Object getSono1Id() {
                    return sono1Id;
                }

                public void setSono1Id(Object sono1Id) {
                    this.sono1Id = sono1Id;
                }

                public Object getSono2Id() {
                    return sono2Id;
                }

                public void setSono2Id(Object sono2Id) {
                    this.sono2Id = sono2Id;
                }

                public int getNumber() {
                    return number;
                }

                public void setNumber(int number) {
                    this.number = number;
                }

                public String getNodeName() {
                    return nodeName;
                }

                public void setNodeName(String nodeName) {
                    this.nodeName = nodeName;
                }

                public double getLon() {
                    return lon;
                }

                public void setLon(double lon) {
                    this.lon = lon;
                }

                public double getLat() {
                    return lat;
                }

                public void setLat(double lat) {
                    this.lat = lat;
                }

                public String getAddress() {
                    return address;
                }

                public void setAddress(String address) {
                    this.address = address;
                }

                public Object getRemark() {
                    return remark;
                }

                public void setRemark(Object remark) {
                    this.remark = remark;
                }

                public int getIsFinished() {
                    return isFinished;
                }

                public void setIsFinished(int isFinished) {
                    this.isFinished = isFinished;
                }

                public int getDetailFlag() {
                    return detailFlag;
                }

                public void setDetailFlag(int detailFlag) {
                    this.detailFlag = detailFlag;
                }

                public int getDetailSuccess() {
                    return detailSuccess;
                }

                public void setDetailSuccess(int detailSuccess) {
                    this.detailSuccess = detailSuccess;
                }

                public String getCreatedBy() {
                    return createdBy;
                }

                public void setCreatedBy(String createdBy) {
                    this.createdBy = createdBy;
                }

                public String getCreated() {
                    return created;
                }

                public void setCreated(String created) {
                    this.created = created;
                }

                public String getUpdated() {
                    return updated;
                }

                public void setUpdated(String updated) {
                    this.updated = updated;
                }

                public int getIsValid() {
                    return isValid;
                }

                public void setIsValid(int isValid) {
                    this.isValid = isValid;
                }

                public Object getCurrentOrder() {
                    return currentOrder;
                }

                public void setCurrentOrder(Object currentOrder) {
                    this.currentOrder = currentOrder;
                }
            }
        }
    }
}
