package com.cjj.qlemusic.security.entity;

import com.cjj.qlemusic.security.validator.LoginValidator;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 用户登录实体类
 */
public class UmsUserLogin implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "手机号")
    @NotEmpty(message = "手机号不能为空")
    private String telephone;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "验证码")
    private String verify;

    @ApiModelProperty(value = "登录方式")
    @NotEmpty(message = "未知错误")
    private String credentialType;

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerify() {
        return verify;
    }

    public void setVerify(String verify) {
        this.verify = verify;
    }

    public String getCredentialType() {
        return credentialType;
    }

    public void setCredentialType(String credentialType) {
        this.credentialType = credentialType;
    }
}
