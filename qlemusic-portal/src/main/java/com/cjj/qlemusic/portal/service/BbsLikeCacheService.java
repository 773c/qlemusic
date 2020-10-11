package com.cjj.qlemusic.portal.service;


import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsUserLike;

import java.io.IOException;
import java.util.List;

/**
 * 点赞缓存Service
 */
public interface BbsLikeCacheService {
    /**
     * 保存用户点赞的内容（音乐）
     * @param likedId
     * @param userId
     * @param isLike
     */
    void setUserLike(Long likedId,Long userId,boolean isLike);

    /**
     * 获取用户点赞的内容（音乐）
     * @param likedId
     * @param userId
     * @return
     */
    Object getUserLike(Long likedId,Long userId);

    /**
     * 删除用户点赞的内容（音乐）
     * @param likedId
     * @param userId
     */
    void delUserLike(Long likedId,Long userId);

    /**
     * 自增加1
     * @param likedId
     */
    void incrementLikedCount(Long likedId);

    /**
     * 自减1
     * @param likedId
     */
    void decrementLikedCount(Long likedId);

    /**
     * 获取被点赞的数量
     * @param likedId
     * @return
     */
    Object getLikedCount(Long likedId);

    /**
     * 删除被点赞的记录数
     * @param likedId
     */
    void delLikedCount(Long likedId);

    /**
     * 获取用户点赞的内容（音乐）
     * @return
     */
    List<BbsUserLike> getUserLikeList() throws IOException;

    /**
     * 获取被点赞的数量集合
     * @return
     */
    List<BbsMusicOperation> getLikedCountList() throws IOException;
}
