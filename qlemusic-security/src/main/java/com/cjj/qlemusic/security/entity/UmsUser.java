package com.cjj.qlemusic.security.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * qq信息实体类
 */
public class UmsUser implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "手机号，邮箱，第三方唯一登录标识")
    @NotEmpty(message = "账号不能为空")
    private String identity;

    @ApiModelProperty(value = "登录密码，第三方令牌")
    private String credential;

    @ApiModelProperty(value = "加密盐")
    private String salt;

    @ApiModelProperty(value = "登录类型")
    private String loginType;

    @ApiModelProperty(value = "用户信息")
    private UmsUserInfo umsUserInfo;

    @ApiModelProperty(value = "qq授权过期时间")
    private Long expiredTime;

    @ApiModelProperty(value = "是否可用")
    private boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentity() {
        return identity;
    }

    public void setIdentity(String identity) {
        this.identity = identity;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public UmsUserInfo getUmsUserInfo() {
        return umsUserInfo;
    }

    public void setUmsUserInfo(UmsUserInfo umsUserInfo) {
        this.umsUserInfo = umsUserInfo;
    }

    public Long getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Long expiredTime) {
        this.expiredTime = expiredTime;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "UmsUser{" +
                "id=" + id +
                ", identity='" + identity + '\'' +
                ", credential='" + credential + '\'' +
                ", salt='" + salt + '\'' +
                ", loginType='" + loginType + '\'' +
                ", umsUserInfo=" + umsUserInfo +
                ", expiredTime=" + expiredTime +
                ", available=" + available +
                '}';
    }
}
