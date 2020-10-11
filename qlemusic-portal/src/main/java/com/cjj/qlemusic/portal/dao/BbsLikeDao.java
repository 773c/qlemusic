package com.cjj.qlemusic.portal.dao;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsUserLike;
import org.apache.ibatis.annotations.Param;

/**
 * 点赞Dao
 */
public interface BbsLikeDao {
    /**
     * 存入用户喜欢的音乐
     * @param bbsUserLike
     */
    void insertUserLike(BbsUserLike bbsUserLike);

    /**
     * 获取点赞次数
     * @param id
     * @return
     */
    Long selectLikeCount(Long id);

    /**
     * 修改音乐点赞次数
     * @param id
     * @param bbsMusicOperation
     */
    void updateMusicLikeCount(@Param("id") Long id,@Param("bbsMusicOperation") BbsMusicOperation bbsMusicOperation);

    /**
     * 根据id和用户id查询是否存在
     * @param musicId
     * @param userId
     * @return
     */
    BbsUserLike selectUserLikeByLikedIdAndUserId(@Param("musicId") Long musicId,@Param("userId") Long userId);

    /**
     * 更新用户点赞表
     * @param userLikeDao
     */
    void updateUserLike(BbsUserLike userLikeDao);

    /**
     * 根据musicId查询是否存在
     * @param musicId
     * @return
     */
    BbsMusicOperation selectLikedCountById(Long musicId);

    /**
     * 更新点赞数量
     * @param musicOperationDao
     */
    void updateLikedCount(BbsMusicOperation musicOperationDao);

    /**
     * 插入点赞数量
     * @param musicOperation
     */
    void insertLikedCount(BbsMusicOperation musicOperation);
}
