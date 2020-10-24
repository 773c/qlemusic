package com.cjj.qlemusic.security.service;


import com.cjj.qlemusic.security.entity.UmsCollect;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 收藏管理Service
 */
public interface UmsCollectService {
    /**
     * 创建收藏夹
     * @param userId
     * @param umsCollect
     * @return
     */
    @Transactional
    Long createCollect(Long userId,UmsCollect umsCollect);

    /**
     * 获取收藏夹
     * @param id
     * @return
     */
    List<UmsCollect> getCollectList(Long id);

    /**
     * 添加到收藏夹
     * @param collectId
     * @param musicId
     * @return
     */
    int addCollect(Long collectId, Long musicId);

    /**
     * 从相应收藏夹中删除内容
     * @param collectMusicId
     * @return
     */
    int deleteCollectContent(Long collectMusicId);

    /**
     * 删除收藏夹
     * @param id
     * @return
     */
    int deleteCollect(Long id);

    /**
     * 删除收藏夹并移动内容
     * @param id
     * @param moveId
     * @return
     */
    @Transactional
    int deleteCollectAndMove(Long id,Long moveId);

    /**
     * 根据收藏夹id删除相应内容
     * @param id
     * @return
     */
    @Transactional
    int deleteCollectAndContent(Long id);

    /**
     * 批量移动收藏夹内容
     * @param collectMusicIds
     * @param id
     * @return
     */
    int batchMoveContent(List<Long> collectMusicIds, Long id);
}
