package com.cjj.qlemusic.portal.entity;

import com.cjj.qlemusic.security.entity.UmsUserInfo;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * 回复评论实体类
 */
public class BbsReplyuserComment implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户未登录")
    private Long userId;

    @ApiModelProperty(value = "回复用户id")
    @NotNull(message = "评论失败")
    private Long replyuserId;

    @ApiModelProperty(value = "音乐id")
    @NotNull(message = "评论失败")
    private Long musicId;

    @ApiModelProperty(value = "评论行数")
    @NotNull(message = "评论失败")
    private Long rowId;

    @ApiModelProperty(value = "回复评论内容")
    @NotEmpty(message = "评论内容不能为空")
    private String content;

    @ApiModelProperty(value = "回复第一时间")
    private Date createTime;

    @ApiModelProperty(value = "被回复的用户发表时间")
    @NotNull(message = "未知错误")
    private Date replyuserCreateTime;

    @ApiModelProperty(value = "用户信息")
    private UmsUserInfo umsUserInfo;

    @ApiModelProperty(value = "是否可用")
    private boolean available;

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

    public Long getReplyuserId() {
        return replyuserId;
    }

    public void setReplyuserId(Long replyuserId) {
        this.replyuserId = replyuserId;
    }

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    public Long getRowId() {
        return rowId;
    }

    public void setRowId(Long rowId) {
        this.rowId = rowId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReplyuserCreateTime() {
        return replyuserCreateTime;
    }

    public void setReplyuserCreateTime(Date replyuserCreateTime) {
        this.replyuserCreateTime = replyuserCreateTime;
    }

    public UmsUserInfo getUmsUserInfo() {
        return umsUserInfo;
    }

    public void setUmsUserInfo(UmsUserInfo umsUserInfo) {
        this.umsUserInfo = umsUserInfo;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "BbsReplyuserComment{" +
                "id=" + id +
                ", userId=" + userId +
                ", replyuserId=" + replyuserId +
                ", musicId=" + musicId +
                ", rowId=" + rowId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", replyuserCreateTime=" + replyuserCreateTime +
                ", umsUserInfo=" + umsUserInfo +
                ", available=" + available +
                '}';
    }
}
