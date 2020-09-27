package com.cjj.qlemusic.portal.entity;

import com.cjj.qlemusic.security.entity.UmsUser;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 音乐片段实体类
 */
public class BbsMusic implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "地址")
    private String audioUrl;

    @ApiModelProperty(value = "封面地址")
    private String audioAvatarUrl;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "播放时长")
    private String playTime;

    @ApiModelProperty(value = "用户信息")
    private UmsUser umsUser;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UmsUser getUmsUser() {
        return umsUser;
    }

    public void setUmsUser(UmsUser umsUser) {
        this.umsUser = umsUser;
    }

    @Override
    public String toString() {
        return "BbsMusic{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", audioUrl='" + audioUrl + '\'' +
                ", audioAvatarUrl='" + audioAvatarUrl + '\'' +
                ", startTime='" + startTime + '\'' +
                ", playTime='" + playTime + '\'' +
                ", umsUser=" + umsUser +
                '}';
    }
}
