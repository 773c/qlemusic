package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsReplyuserComment;
import com.cjj.qlemusic.portal.entity.BbsUserComment;
import com.cjj.qlemusic.security.entity.UmsUserInfo;

import java.io.IOException;
import java.util.List;

/**
 * 评论缓存Service
 */
public interface BbsCommentCacheService {
    /**
     * 存入用户评论（目前根据发表时间来排序）
     * @param bbsUserComment
     */
    void setUserComment(BbsUserComment bbsUserComment);

    /**
     * 获取用户评论
     * @param musicId
     * @return
     */
    Object getUserComment(Long musicId);

    /**
     * 删除对应音乐id的用户评论
     * @param musicId
     */
    void delUserCommentByMusic(Long musicId);

    /**
     * 存入回复评论（目前根据发表时间来排序）
     * @param bbsReplyuserComment
     */
    void setReplyuserComment(BbsReplyuserComment bbsReplyuserComment);

    /**
     * 删除对应音乐id的回复评论
     * @param musicId
     */
    void delReplyuserCommentByMusic(Long musicId);

    /**
     * 评论自增加1
     * @param commentedId
     */
    void incrementCommentCount(Long commentedId);

    /**
     * 评论自减1(这里commentedId为音乐id)
     * @param commentedId
     */
    void decrementCommentCount(Long commentedId);

    /**
     * 删除评论数量(这里commentedId为音乐id)
     * @param commentedId
     */
    void delCommentedCount(Long commentedId);

    /**
     * 获取评论数量
     * @param commentedId
     * @return
     */
    Integer getCommentedCount(Long commentedId);

    /**
     * 存入评论数量(这里commentedId为音乐id)
     * @param commentedId
     * @param commentCount
     */
    void setCommentedCount(Long commentedId,Integer commentCount);

    /**
     * 获得用户评论
     * @param musicId
     * @return
     */
    List<BbsUserComment> getUserCommentList(Long musicId);

    /**
     * 获得回复评论
     * @param musicId
     * @return
     */
    List<BbsReplyuserComment> getReplyuserCommentList(Long musicId);

    /**
     * 获取评论数量
     * @return
     * @throws IOException
     */
    List<BbsMusicOperation> getCommentedCountList() throws IOException;

    /**
     * 获取用户信息
     * @return
     */
    List<UmsUserInfo> getUserInfoList() throws IOException ;

    /**
     * 存入用户信息
     * @param umsUserInfo
     */
    void setUserInfoToComment(UmsUserInfo umsUserInfo);

    /**
     * 删除评论中的用户信息
     * @param userId
     */
    void delUserInfoToComment(Long userId);
}
