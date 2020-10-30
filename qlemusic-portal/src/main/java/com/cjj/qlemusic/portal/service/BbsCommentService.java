package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsReplyuserComment;
import com.cjj.qlemusic.portal.entity.BbsUserComment;
import com.cjj.qlemusic.security.entity.UmsUserInfo;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List; /**
 * 评论管理Service
 */
public interface BbsCommentService {
    /**
     * 用户评论
     * @param bbsUserComment
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    int userComment(BbsUserComment bbsUserComment) throws IOException;

    /**
     * 回复评论
     * @param bbsReplyuserComment
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    int replyuserComment(BbsReplyuserComment bbsReplyuserComment) throws IOException;

    /**
     * 获取评论的用户
     * @param userIds
     */
    List<UmsUserInfo> getUserByComment(List<Long> userIds);

    /**
     * 获取对应音乐id的评论
     * @param musicId
     * @return
     */
    List<BbsUserComment> getCommentByMusicId(Long musicId) throws IOException;

    /**
     * 设置评论的用户信息
     * @param umsUserInfo
     */
    void setUserInfo(UmsUserInfo umsUserInfo) throws IOException;

    /**
     * 获取对应音乐id的评论数量
     * @param musicIdList
     * @return
     */
    List<BbsMusicOperation> getCommentOperationList(List<Long> musicIdList) throws IOException;

    /**
     * 发送消息
     * @param userId
     */
    void sendMsgTip(Long userId);

    /**
     * 定时将评论数量存入数据库
     * @throws IOException
     */
    void commentedCountToDatabaseTimer() throws IOException;

}
