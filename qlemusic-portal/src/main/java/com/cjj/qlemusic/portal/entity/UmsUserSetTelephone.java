package com.cjj.qlemusic.portal.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 修改手机号实体类
 */
public class UmsUserSetTelephone implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "未知错误")
    private Long userId;

    @ApiModelProperty(value = "旧手机号")
    @NotEmpty(message = "未知错误")
    private String oldTelephone;

    @ApiModelProperty(value = "新手机号")
    @NotEmpty(message = "未知错误")
    private String newTelephone;

    @ApiModelProperty(value = "验证码")
    private String setVerify;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getOldTelephone() {
        return oldTelephone;
    }

    public void setOldTelephone(String oldTelephone) {
        this.oldTelephone = oldTelephone;
    }

    public String getNewTelephone() {
        return newTelephone;
    }

    public void setNewTelephone(String newTelephone) {
        this.newTelephone = newTelephone;
    }

    public String getSetVerify() {
        return setVerify;
    }

    public void setSetVerify(String setVerify) {
        this.setVerify = setVerify;
    }
}
