package com.cjj.qlemusic.security.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 管理员用户信息
 */
public class UmsAdmin implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "管理员id")
    private Long id;

    @ApiModelProperty(value = "管理员昵称")
    private String name;

    @ApiModelProperty(value = "管理员账号")
    @NotEmpty(message = "账号不能为空")
    private String account;

    @ApiModelProperty(value = "管理员密码")
    @NotEmpty(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "盐")
    private String salt;

    @ApiModelProperty(value = "管理员头像")
    private String headIcon;

    @ApiModelProperty(value = "管理员备注")
    private String note;

    @ApiModelProperty(value = "是否可用")
    private boolean available;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "UmsAdmin{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", salt='" + salt + '\'' +
                ", headIcon='" + headIcon + '\'' +
                ", note='" + note + '\'' +
                ", available=" + available +
                '}';
    }
}
