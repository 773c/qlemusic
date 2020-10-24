package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsUserLike;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 点赞Service
 */
public interface BbsLikeService {
    /**
     * 点赞
     * @param bbsUserLike
     * @throws IOException
     */
    Map<String,Object> like(BbsUserLike bbsUserLike) throws IOException;

    /**
     * 根据相应音乐id获取用户点赞
     * @param musicIdList
     * @return
     */
    List<BbsUserLike> getUserLikeList(List<Long> musicIdList) throws IOException;

    /**
     * 根据相应音乐id获取点赞数量
     * @param musicIdList
     * @return
     */
    List<BbsMusicOperation> getLikeOperationList(List<Long> musicIdList) throws IOException;

    /**
     * 定时将用户点赞数据缓存存入数据库中
     */
    void userLikeToDatabaseTimer() throws IOException;

    /**
     * 定时将点赞数量数据缓存存入数据库中
     */
    void likedCountToDatabaseTimer() throws IOException;

    /**
     * 从数据中获取点赞数据
     */
    void getLikeDataFromDao();
}
