package com.cjj.qlemusic.security.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 管理员用户登录信息
 */
public class UmsAdminLogin implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员账号")
    @NotEmpty(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "管理员密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UmsAdminLogin{" +
                "account='" + account + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
