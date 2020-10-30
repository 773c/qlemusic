package com.cjj.qlemusic.portal.entity;

import com.cjj.qlemusic.security.entity.UmsUserInfo;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户评论实体类
 */
public class BbsUserComment implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "用户id")
    @NotNull(message = "用户未登录")
    private Long userId;

    @ApiModelProperty(value = "音乐id")
    @NotNull(message = "评论失败")
    private Long musicId;

    @ApiModelProperty(value = "评论行数")
    private Integer rowId;

    @ApiModelProperty(value = "评论内容")
    @NotEmpty(message = "评论内容不能为空")
    private String content;

    @ApiModelProperty(value = "发表第一次时间")
    private Date createTime;

    @ApiModelProperty(value = "发表更新时间")
    private Date updateTime;

    @ApiModelProperty(value = "用户信息")
    @NotNull(message = "用户信息错误")
    private UmsUserInfo umsUserInfo;

    @ApiModelProperty(value = "回复评论集合")
    private List<BbsReplyuserComment> bbsReplyuserCommentList;

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

    public Long getMusicId() {
        return musicId;
    }

    public void setMusicId(Long musicId) {
        this.musicId = musicId;
    }

    public Integer getRowId() {
        return rowId;
    }

    public void setRowId(Integer rowId) {
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public UmsUserInfo getUmsUserInfo() {
        return umsUserInfo;
    }

    public void setUmsUserInfo(UmsUserInfo umsUserInfo) {
        this.umsUserInfo = umsUserInfo;
    }

    public List<BbsReplyuserComment> getBbsReplyuserCommentList() {
        return bbsReplyuserCommentList;
    }

    public void setBbsReplyuserCommentList(List<BbsReplyuserComment> bbsReplyuserCommentList) {
        this.bbsReplyuserCommentList = bbsReplyuserCommentList;
    }

    public boolean getAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    @Override
    public String toString() {
        return "BbsUserComment{" +
                "id=" + id +
                ", userId=" + userId +
                ", musicId=" + musicId +
                ", rowId=" + rowId +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", umsUserInfo=" + umsUserInfo +
                ", bbsReplyuserCommentList=" + bbsReplyuserCommentList +
                ", available=" + available +
                '}';
    }
}
