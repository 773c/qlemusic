package com.cjj.qlemusic.portal.service;

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
    void userComment(BbsUserComment bbsUserComment);

    /**
     * 回复评论
     * @param bbsReplyuserComment
     */
    void replyuserComment(BbsReplyuserComment bbsReplyuserComment);

    /**
     * 获取评论的用户
     * @param userIds
     */
    List<UmsUser> getUserByComment(List<Long> userIds);

    /**
     * 获取对应音乐的评论
     * @param musicIdList
     * @return
     */
    List<BbsUserComment> getCommentByMusicIds(List<Long> musicIdList) throws IOException;

    /**
     * 定时将用户评论存入数据库
     */
    void userCommentToDatabaseTimer() throws IOException;

    /**
     * 定时将回复评论存入数据库
     */
    void replyuserCommentToDatabaseTimer();

    /**
     * 定时将评论数量存入数据库
     * @throws IOException
     */
    void commentedCountToDatabaseTimer() throws IOException;

}
