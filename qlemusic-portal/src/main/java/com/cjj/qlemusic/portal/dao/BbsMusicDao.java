package com.cjj.qlemusic.portal.dao;

import com.cjj.qlemusic.portal.entity.BbsMusic;

import java.util.List;

/**
 * 音乐片段Dao
 */
public interface BbsMusicDao {
    /**
     * 获取推荐的音乐片段
     * @return
     */
    List<BbsMusic> selectRecommendList();
}
