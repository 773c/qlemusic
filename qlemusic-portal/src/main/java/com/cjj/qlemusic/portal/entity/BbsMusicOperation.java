package com.cjj.qlemusic.portal.entity;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 音乐点赞实体类
 */
public class BbsMusicOperation implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "点赞次数")
    private Integer likeCount;

    @ApiModelProperty(value = "音乐id")
    private Long musicId;

    @ApiModelProperty(value = "是否点赞")
    private boolean isLike;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
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
        return "BbsMusicOperation{" +
                "id=" + id +
                ", likeCount=" + likeCount +
                ", musicId=" + musicId +
                '}';
    }
}
