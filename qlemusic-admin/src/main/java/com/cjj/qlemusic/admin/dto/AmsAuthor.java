package com.cjj.qlemusic.admin.dto;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class AmsAuthor implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "歌手ID")
    private Long id;

    @ApiModelProperty(value = "歌手姓名")
    private String name;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "歌手头像")
    private String headIcon;

    @ApiModelProperty(value = "歌手简介")
    private String description;

    @ApiModelProperty(value = "首字母")
    private String prefix;

    @ApiModelProperty(value = "粉丝数量")
    private Integer fansSum;

    @ApiModelProperty(value = "语种")
    private String language;

    @ApiModelProperty(value = "标签")
    private String tag;

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

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHeadIcon() {
        return headIcon;
    }

    public void setHeadIcon(String headIcon) {
        this.headIcon = headIcon;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public Integer getFansSum() {
        return fansSum;
    }

    public void setFansSum(Integer fansSum) {
        this.fansSum = fansSum;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
