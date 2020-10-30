package com.cjj.qlemusic.portal.dao;


import com.cjj.qlemusic.portal.entity.UmsCollect;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏管理Dao
 */
public interface UmsCollectDao {
    /**
     * 创建收藏夹
     * @param umsCollect
     * @return
     */
    Long createCollect(UmsCollect umsCollect);

    /**
     * 添加用户和收藏夹的联系
     * @param id
     * @param collectId
     */
    void insertUserAndCollect(@Param("id") Long id, @Param("collectId") Long collectId);

    /**
     * 获取收藏夹
     * @param id
     * @return
     */
    List<UmsCollect> selectCollectList(Long id);

    /**
     * 添加到收藏夹
     * @param collectId
     * @param musicId
     * @return
     */
    int insertCollect(@Param("collectId")Long collectId, @Param("musicId") Long musicId);

    /**
     * 从相应收藏夹中删除内容
     * @param collectMusicId
     */
    void deleteCollectContent(Long collectMusicId);

    /**
     * 删除收藏夹
     * @param id
     */
    void deleteCollect(Long id);

    /**
     * 移动内容
     * @param id
     * @param moveId
     */
    void updateMoveCollectContent(@Param("id") Long id, @Param("moveId") Long moveId);

    /**
     * 根据收藏夹id删除相应内容
     * @param id
     */
    void deleteCollectAndContentByCollectId(Long id);

    /**
     * 批量移动收藏夹内容
     * @param collectMusicIds
     * @param id
     */
    void updateBatchMoveContent(@Param("collectMusicIds") List<Long> collectMusicIds, @Param("id") Long id);

    /**
     * 判断收藏夹是否存在
     * @param name
     * @return
     */
    UmsCollect selectIsExistCollect(String name);
}
