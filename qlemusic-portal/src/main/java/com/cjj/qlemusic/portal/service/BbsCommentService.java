package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsReplyuserComment;
import com.cjj.qlemusic.portal.entity.BbsUserComment;
import com.cjj.qlemusic.security.entity.UmsUser;

import java.io.IOException;
import java.util.List; /**
 * 评论管理Service
 */
public interface BbsCommentService {
    /**
     * 用户评论
     * @param bbsUserComment
     */
    int userComment(BbsUserComment bbsUserComment);

    /**
     * 回复评论
     * @param bbsReplyuserComment
     */
    int replyuserComment(BbsReplyuserComment bbsReplyuserComment);

    /**
     * 获取评论的用户
     * @param userIds
     */
    List<UmsUser> getUserByComment(List<Long> userIds);

    /**
     * 获取对应音乐id的评论
     * @param musicIdList
     * @return
     */
    List<BbsUserComment> getCommentByMusicIds(List<Long> musicIdList) throws IOException;

    /**
     * 设置评论数量
     * @param musicId
     */
    void setCommentedCount(Long musicId);

    /**
     * 获取对应音乐id的评论数量
     * @param musicIdList
     * @return
     */
    List<BbsMusicOperation> getCommentOperationList(List<Long> musicIdList) throws IOException;

    /**
     * 定时将评论数量存入数据库
     * @throws IOException
     */
    void commentedCountToDatabaseTimer() throws IOException;

}
