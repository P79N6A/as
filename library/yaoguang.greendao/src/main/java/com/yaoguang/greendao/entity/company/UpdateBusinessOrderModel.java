package com.yaoguang.greendao.entity.company;

/**
 * UpdateFowardOrder 接口返回的类
 * Created by zhongjh on 2018/11/9.
 */
public class UpdateBusinessOrderModel {

    // 是否需要弹对话框，0带否，1代表是。必须有确定/否的两个按钮
    private String showDialog;
    // 对话框内容
    private String info;
    // 保存成功后的id
    private String id;

    public String getShowDialog() {
        return showDialog;
    }

    public void setShowDialog(String showDialog) {
        this.showDialog = showDialog;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
