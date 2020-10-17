package com.cjj.qlemusic.portal.service;

import java.io.IOException;

/**
 * 播放量Service
 */
public interface BbsPlayService {
    /**
     * 添加播放量
     * @param musicId
     * @return
     */
    int playBbsMusic(Long musicId);

    /**
     * 定时将播放数量数据缓存存入数据库中
     */
    void playedCountToDatabaseTimer() throws IOException;
}
