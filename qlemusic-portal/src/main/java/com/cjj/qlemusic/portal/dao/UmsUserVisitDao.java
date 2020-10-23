package com.cjj.qlemusic.portal.dao;

import com.cjj.qlemusic.portal.entity.UmsUserOperation;

/**
 * 用户访问量Dao
 */
public interface UmsUserVisitDao {
    /**
     * 查询用户的访问数量是否存在
     * @param userId
     * @return
     */
    UmsUserOperation selectUserVisitedCountById(Long userId);

    /**
     * 存入用户访问数量
     * @param visitedOperation
     */
    void insertVisitedCount(UmsUserOperation visitedOperation);

    /**
     * 更新用户访问数量
     * @param visitedOperation
     */
    void updateVisitedCount(UmsUserOperation visitedOperation);
}
