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

    @ApiModelProperty(value = "播放次数")
    private Integer playCount;

    @ApiModelProperty(value = "评论次数")
    private Integer commentCount;

    @ApiModelProperty(value = "用户评论次数")
    private Integer userCommentCount;

    @ApiModelProperty(value = "音乐id")
    private Long musicId;


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

    public Integer getPlayCount() {
        return playCount;
    }

    public void setPlayCount(Integer playCount) {
        this.playCount = playCount;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getUserCommentCount() {
        return userCommentCount;
    }

    public void setUserCommentCount(Integer userCommentCount) {
        this.userCommentCount = userCommentCount;
    }

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    @Override
    public String toString() {
        return "BbsMusicOperation{" +
                "id=" + id +
                ", likeCount=" + likeCount +
                ", playCount=" + playCount +
                ", commentCount=" + commentCount +
                ", userCommentCount=" + userCommentCount +
                ", musicId=" + musicId +
                '}';
    }
}
