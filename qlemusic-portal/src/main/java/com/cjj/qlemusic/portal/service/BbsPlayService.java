package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;

import java.io.IOException;
import java.util.List;

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
     * 获取相应音乐id的播放数量
     * @param musicIdList
     * @return
     */
    List<BbsMusicOperation> getPlayOperationList(List<Long> musicIdList) throws IOException;

    /**
     * 定时将播放数量数据缓存存入数据库中
     */
    void playedCountToDatabaseTimer() throws IOException;
}
