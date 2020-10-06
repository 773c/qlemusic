package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.BbsMusic;

import java.util.List;

/**
 * 音乐片段Service
 */
public interface BbsMusicService {
    /**
     * 获取推荐的音乐片段
     * @return
     */
    List<BbsMusic> getRecommendList();

    /**
     * 获取我的音乐片段
     * @param id
     * @return
     */
    List<BbsMusic> getMyMusicList(Long id);

    /**
     * 获取相应收藏夹的内容
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param collectId
     * @return
     */
    List<BbsMusic> getMusicByCollectId(Integer pageNum,Integer pageSize,Long userId, Long collectId);
}
