package com.cjj.qlemusic.portal.entity;

import com.cjj.qlemusic.security.entity.UmsUser;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.Date;

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
    private UmsUser umsUser;

    @ApiModelProperty(value = "点赞,查看,评论的次数")
    private BbsMusicOperation bbsMusicOperation;

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

    public UmsUser getUmsUser() {
        return umsUser;
    }

    public void setUmsUser(UmsUser umsUser) {
        this.umsUser = umsUser;
    }

    public BbsMusicOperation getBbsMusicOperation() {
        return bbsMusicOperation;
    }

    public void setBbsMusicOperation(BbsMusicOperation bbsMusicOperation) {
        this.bbsMusicOperation = bbsMusicOperation;
    }

    @Override
    public String toString() {
        return "BbsMusic{" +
                "id=" + id +
                ", collectMusicId=" + collectMusicId +
                ", title='" + title + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                ", audioAvatarUrl='" + audioAvatarUrl + '\'' +
                ", startTime='" + startTime + '\'' +
                ", playTime='" + playTime + '\'' +
                ", releaseTime=" + releaseTime +
                ", umsUser=" + umsUser +
                ", bbsMusicOperation=" + bbsMusicOperation +
                '}';
    }
}
