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
}
