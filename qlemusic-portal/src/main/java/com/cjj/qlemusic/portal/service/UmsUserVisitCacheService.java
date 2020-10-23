package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.UmsUserOperation;

import java.io.IOException;
import java.util.List;

/**
 * 用户访问量缓存Service
 */
public interface UmsUserVisitCacheService {
    /**
     * 用户访问数量自增
     * @param userId
     */
    void incrementVisitedCount(Long userId);

    /**
     * 存入用户访问数量
     * @param userId
     * @param visitCount
     */
    void setVisitedCount(Long userId,Integer visitCount);

    /**
     * 获取用户访问数量
     * @param userId
     * @return
     */
    Integer getVisitedCount(Long userId);

    /**
     * 获取用户访问数量集合
     * @return
     */
    List<UmsUserOperation> getVisitedCountList() throws IOException;

    /**
     * 删除用户数量
     * @param userId
     */
    void delVisitedCount(Long userId);
}
