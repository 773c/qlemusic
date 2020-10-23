package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsReplyuserComment;
import com.cjj.qlemusic.portal.entity.BbsUserComment;
import com.cjj.qlemusic.security.entity.UmsUser;

import java.io.IOException;
import java.util.List;

/**
 * 评论缓存Service
 */
public interface BbsCommentCacheService {
    /**
     * 存入用户评论
     * @param bbsUserComment
     */
    void setUserComment(BbsUserComment bbsUserComment);

    /**
     * 删除用户评论
     */
    void delUserComment();

    /**
     * 存入回复评论
     * @param bbsReplyuserComment
     */
    void setReplyuserComment(BbsReplyuserComment bbsReplyuserComment);

    /**
     * 删除回复评论
     */
    void delReplyuserComment();

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
     * @return
     */
    List<BbsUserComment> getUserCommentList();

    /**
     * 获得回复评论
     * @return
     */
    List<BbsReplyuserComment> getReplyuserCommentList();

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
    List<UmsUser> getUserList() throws IOException ;

    /**
     * 存入用户信息
     * @param umsUser
     */
    void setUserInfoToComment(UmsUser umsUser);

    /**
     * 删除评论中的用户信息
     * @param userId
     */
    void delUserInfoToComment(Long userId);

    /**
     * 清除用户评论所有
     */
    void delUserCommentAll();

    /**
     * 清除回复评论所有
     */
    void delReplyuserCommentAll();
}
