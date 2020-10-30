package com.cjj.qlemusic.portal.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 绑定手机实体类
 */
public class UmsUserSetBindTelephone implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "未知错误")
    private Long userId;

    @ApiModelProperty(value = "手机号")
    @NotEmpty(message = "未知错误")
    private String telephone;

    @ApiModelProperty(value = "验证码")
    private String setVerify;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getSetVerify() {
        return setVerify;
    }

    public void setSetVerify(String setVerify) {
        this.setVerify = setVerify;
    }

    @Override
    public String toString() {
        return "UmsUserSetBindTelephone{" +
                "userId=" + userId +
                ", telephone='" + telephone + '\'' +
                ", setVerify='" + setVerify + '\'' +
                '}';
    }
}
