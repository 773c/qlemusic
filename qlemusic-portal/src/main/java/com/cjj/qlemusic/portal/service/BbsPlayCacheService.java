package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;

import java.io.IOException;
import java.util.List;

/**
 * 播放量缓存Service
 */
public interface BbsPlayCacheService {
    /**
     * 播放量自增加1
     * @param musicId
     */
    void incrementPlayedCount(Long musicId);

    /**
     * 存入播放量
     * @param musicId
     * @param playCount
     */
    void setPlayedCount(Long musicId, Integer playCount);

    /**
     * 清除播放量缓存
     * @param musicId
     */
    void delPlayedCount(Long musicId);

    /**
     * 获取播放量
     * @param musicId
     * @return
     */
    Integer getPlayedCount(Long musicId);

    /**
     * 获取播放量集合
     * @return
     */
    List<BbsMusicOperation> getPlayedCountList() throws IOException;

}
