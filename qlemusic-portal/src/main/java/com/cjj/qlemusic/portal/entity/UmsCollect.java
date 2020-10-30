package com.cjj.qlemusic.portal.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * 收藏实体类
 */
public class UmsCollect implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏夹id")
    private Long id;

    @ApiModelProperty(value = "收藏夹名称")
    @NotEmpty(message = "收藏夹名称不能为空")
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

    @Override
    public String toString() {
        return "UmsCollect{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
