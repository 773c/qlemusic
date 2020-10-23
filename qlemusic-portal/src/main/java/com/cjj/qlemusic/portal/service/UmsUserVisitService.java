package com.cjj.qlemusic.portal.service;

import java.io.IOException;

/**
 * 用户访问量Service
 */
public interface UmsUserVisitService {
    /**
     * 添加用户访问量
     * @param id
     * @return
     */
    int userVisit(Long id);

    /**
     * 获取用户访问数量
     * @param id
     */
    Integer getVisitCount(Long id);

    /**
     * 定时将点赞数量数据缓存存入数据库中
     */
    void visitedCountToDatabaseTimer() throws IOException;
}
