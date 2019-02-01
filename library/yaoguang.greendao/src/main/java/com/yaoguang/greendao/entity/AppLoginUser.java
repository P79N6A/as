package com.yaoguang.greendao.entity;


/**
 * 物流端登录包装对象
 *
 * @author wangxiaohui
 * @date 2017年12月1日 下午5:56:10
 */
public class AppLoginUser {

    // 联系人
    private AppUserWrapper user;

    // 联系人手机
    private UserApply userApply;

    /**
     * type  0 登录成功 1 待审核 2 拒绝 3 信息未完善
     */
    private int type;

    private String userId;

    public AppUserWrapper getUser() {
        return user;
    }

    public void setUser(AppUserWrapper user) {
        this.user = user;
    }

    public UserApply getUserApply() {
        return userApply;
    }

    public void setUserApply(UserApply userApply) {
        this.userApply = userApply;
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}