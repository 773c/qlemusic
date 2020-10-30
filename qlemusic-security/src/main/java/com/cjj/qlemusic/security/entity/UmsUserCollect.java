package com.cjj.qlemusic.security.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 用户默认收藏夹
 */
public class UmsUserCollect implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "收藏夹名称")
    private String name;

    @ApiModelProperty(value = "收藏夹描述")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
