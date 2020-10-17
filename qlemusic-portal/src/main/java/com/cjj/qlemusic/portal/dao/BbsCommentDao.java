package com.cjj.qlemusic.portal.dao;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsReplyuserComment;
import com.cjj.qlemusic.portal.entity.BbsUserComment;
import com.cjj.qlemusic.security.entity.UmsUser;

import java.util.List;
import java.util.Set; /**
 * 评论管理Dao
 */
public interface BbsCommentDao {
    /**
     * 存入用户评论
     * @param bbsUserComment
     */
    void insertUserComment(BbsUserComment bbsUserComment);

    /**
     * 存入回复评论
     * @param replyuserComment
     */
    void insertReplyuserComment(BbsReplyuserComment replyuserComment);

    /**
     * 查询是否存在用户点赞，播放量或评论记录
     * @param musicId
     * @return
     */
    BbsMusicOperation selectCommentedCountById(Long musicId);

    /**
     * 存入评论数量
     * @param commentedCount
     */
    void insertCommentedCount(BbsMusicOperation commentedCount);

    /**
     * 根据id获取所有评论数量
     * @param musicId
     * @return
     */
    Integer selectCommentedCountAll(Long musicId);

    /**
     * 更新评论数量
     * @param musicOperationDao
     */
    void updateCommentedCount(BbsMusicOperation musicOperationDao);

    /**
     * 根据相应的音乐id集合获取用户评论
     * @param musicIdList
     * @return
     */
    List<BbsUserComment> selectUserCommentByMusicIds(List<Long> musicIdList);

    /**
     * 根据相应的音乐id获取回复评论
     * @param musicIdList
     * @return
     */
    List<BbsReplyuserComment> selectReplyuserCommentByMusicIds(List<Long> musicIdList);

    /**
     * 根据相应的音乐id获取评论数量
     * @param musicIdList
     * @return
     */
    List<BbsMusicOperation> selectCommentedCountByMusicIds(List<Long> musicIdList);

    /**
     * 根据用户id、音乐id、创建时间查询用户评论
     * @param bbsUserComment
     * @return
     */
    BbsUserComment selectUserCommentByUMT(BbsUserComment bbsUserComment);

    /**
     * 根据用户id、音乐id、创建时间查询回复评论
     * @param replyuserComment
     * @return
     */
    BbsReplyuserComment selectReplyuserCommentByBRMT(BbsReplyuserComment replyuserComment);

    /**
     * 根据音乐id获取用户评论
     * @param musicId
     * @return
     */
    List<BbsUserComment> selectUserCommentByMusicId(Long musicId);

    /**
     * 根据音乐id获取回复评论
     * @param musicId
     * @return
     */
    List<BbsReplyuserComment> selectReplyuserCommentByMusicId(Long musicId);

    /**
     * 根据set集合获取用户信息
     * @param userIdSet
     * @return
     */
    List<UmsUser> selectUserByIds(Set<Long> userIdSet);
}
