package com.cjj.qlemusic.security.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体类
 */
public class UmsUserInfo implements Serializable{
    private final static long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    private Long id;

    @ApiModelProperty(value = "用户本网站唯一标识ID")
    @NotEmpty(message = "未知错误")
    private String uniqueId;

    @ApiModelProperty(value = "用户头像")
    private String avatar;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "注册日期")
    private Date createTime;

    @ApiModelProperty(value = "简介")
    private String description;

    @ApiModelProperty(value = "是否绑定手机号")
    private String telephone;

    @ApiModelProperty(value = "是否绑定邮箱")
    private String email;

    @ApiModelProperty(value = "是否绑定第三方")
    private String oauthId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUniqueId() {
        return uniqueId;
    }

    public void setUniqueId(String uniqueId) {
        this.uniqueId = uniqueId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOauthId() {
        return oauthId;
    }

    public void setOauthId(String oauthId) {
        this.oauthId = oauthId;
    }

    @Override
    public String toString() {
        return "UmsUserInfo{" +
                "id=" + id +
                ", uniqueId='" + uniqueId + '\'' +
                ", avatar='" + avatar + '\'' +
                ", name='" + name + '\'' +
                ", sex='" + sex + '\'' +
                ", createTime=" + createTime +
                ", description='" + description + '\'' +
                ", telephone='" + telephone + '\'' +
                ", email='" + email + '\'' +
                ", oauthId='" + oauthId + '\'' +
                '}';
    }
}
