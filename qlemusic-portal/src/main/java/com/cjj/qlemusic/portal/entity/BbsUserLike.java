package com.cjj.qlemusic.portal.entity;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 用户点赞实体类
 */
public class BbsUserLike implements Serializable{
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty(value = "被点赞id")
    @NotNull(message = "内容id不能为空")
    private Long musicId;

    @ApiModelProperty(value = "是否点赞")
    private boolean isLike;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    public boolean getIsLike() {
        return isLike;
    }

    public void setIsLike(boolean isLike) {
        this.isLike = isLike;
    }

    @Override
    public String toString() {
        return "BbsUserLike{" +
                "id=" + id +
                ", userId=" + userId +
                ", musicId=" + musicId +
                ", isLike=" + isLike +
                '}';
    }
}
