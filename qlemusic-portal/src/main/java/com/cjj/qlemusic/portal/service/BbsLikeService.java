package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.BbsUserLike;

import java.io.IOException;

/**
 * 点赞Service
 */
public interface BbsLikeService {
    /**
     * 点赞
     * @param bbsUserLike
     * @throws IOException
     */
    int like(BbsUserLike bbsUserLike) throws IOException;

    /**
     * 定时将用户点赞数据缓存存入数据库中
     */
    void userLikeToDatabaseTimer() throws IOException;

    /**
     * 定时将点赞数量数据缓存存入数据库中
     */
    void likedCountToDatabaseTimer() throws IOException;

}
