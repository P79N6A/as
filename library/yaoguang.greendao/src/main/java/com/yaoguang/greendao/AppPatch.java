package com.yaoguang.greendao;

/**
 * app补丁
 *
 * @author liyangbin
 * @time
 */
public class AppPatch {
    private Long id;
    /**
     * app类型（0-司机端，1-物流端，2-货主端）
     */
    private String appType;

    //    补丁名称
    private String name;
    //    补丁的下载url
    private String url;
    //    补丁md5值
    private String md5;
    //    补丁版本号
    private String version;
    //    app版本号
    private String appVersion;
    //    app hash值，避免低版本补丁应用到高版本app上
    private String appHash;
    //    记录创建人
    private String createdBy;
    //    记录创建时间
    private String created;
    //    记录修改时间
    private String upStringd;
    //    是否有效(0：无效，1：有效)
    private String isValid;
//    描述
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getAppHash() {
        return appHash;
    }

    public void setAppHash(String appHash) {
        this.appHash = appHash;
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

    public String getUpStringd() {
        return upStringd;
    }

    public void setUpStringd(String upStringd) {
        this.upStringd = upStringd;
    }

    public String getIsValid() {
        return isValid;
    }

    public void setIsValid(String isValid) {
        this.isValid = isValid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}