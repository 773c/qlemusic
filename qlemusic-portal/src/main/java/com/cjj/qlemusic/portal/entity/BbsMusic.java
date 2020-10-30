package com.cjj.qlemusic.portal.entity;

import com.cjj.qlemusic.security.entity.UmsUserInfo;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 音乐片段实体类
 */
public class BbsMusic implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "收藏和音乐关联id")
    private Long collectMusicId;

    @ApiModelProperty(value = "标题")
    @NotEmpty(message = "标题不能为空")
    private String title;

    @ApiModelProperty(value = "地址")
    private String audioUrl;

    @ApiModelProperty(value = "封面地址")
    private String audioAvatarUrl;

    @ApiModelProperty(value = "开始时间")
    @NotEmpty(message = "开始时间不能为空")
    private String startTime;

    @ApiModelProperty(value = "播放时长")
    @NotEmpty(message = "播放时长不能为空")
    private String playTime;

    @ApiModelProperty(value = "发布时间")
    private Date releaseTime;

    @ApiModelProperty(value = "用户信息")
    private UmsUserInfo umsUserInfo;

    @ApiModelProperty(value = "点赞,查看,评论的次数")
    private BbsMusicOperation bbsMusicOperation;

    @ApiModelProperty(value = "用户点赞")
    private BbsUserLike bbsUserLike;

    @ApiModelProperty(value = "用户点赞集合")
    private List<BbsUserLike> bbsUserLikeList;

    @ApiModelProperty(value = "用户评论集合")
    private List<BbsUserComment> bbsUserCommentList;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCollectMusicId() {
        return collectMusicId;
    }

    public void setCollectMusicId(Long collectMusicId) {
        this.collectMusicId = collectMusicId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getAudioAvatarUrl() {
        return audioAvatarUrl;
    }

    public void setAudioAvatarUrl(String audioAvatarUrl) {
        this.audioAvatarUrl = audioAvatarUrl;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPlayTime() {
        return playTime;
    }

    public void setPlayTime(String playTime) {
        this.playTime = playTime;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public UmsUserInfo getUmsUserInfo() {
        return umsUserInfo;
    }

    public void setUmsUserInfo(UmsUserInfo umsUserInfo) {
        this.umsUserInfo = umsUserInfo;
    }

    public BbsMusicOperation getBbsMusicOperation() {
        return bbsMusicOperation;
    }

    public void setBbsMusicOperation(BbsMusicOperation bbsMusicOperation) {
        this.bbsMusicOperation = bbsMusicOperation;
    }

    public BbsUserLike getBbsUserLike() {
        return bbsUserLike;
    }

    public void setBbsUserLike(BbsUserLike bbsUserLike) {
        this.bbsUserLike = bbsUserLike;
    }

    public List<BbsUserLike> getBbsUserLikeList() {
        return bbsUserLikeList;
    }

    public void setBbsUserLikeList(List<BbsUserLike> bbsUserLikeList) {
        this.bbsUserLikeList = bbsUserLikeList;
    }

    public List<BbsUserComment> getBbsUserCommentList() {
        return bbsUserCommentList;
    }

    public void setBbsUserCommentList(List<BbsUserComment> bbsUserCommentList) {
        this.bbsUserCommentList = bbsUserCommentList;
    }
}
