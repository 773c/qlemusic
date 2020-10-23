package com.cjj.qlemusic.portal.dao;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;

import java.util.List; /**
 * 播放量Dao
 */
public interface BbsPlayDao {
    /**
     * 根据热点音乐id集合获取播放量
     * @param musicIdList
     * @return
     */
    List<BbsMusicOperation> selectPlayedCountByMusicIds(List<Long> musicIdList);

    /**
     * 根据音乐id获取播放量
     * @param musicId
     * @return
     */
    BbsMusicOperation selectPlayedCountByMusicId(Long musicId);

    /**
     * 存入播放量
     * @param musicOperation
     */
    void insertPlayedCount(BbsMusicOperation musicOperation);

    /**
     * 更新播放量
     * @param musicOperation
     */
    void updatePlayedCount(BbsMusicOperation musicOperation);
}
