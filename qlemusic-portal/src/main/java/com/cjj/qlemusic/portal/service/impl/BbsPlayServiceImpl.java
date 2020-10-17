package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.portal.dao.BbsPlayDao;
import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.service.BbsPlayCacheService;
import com.cjj.qlemusic.portal.service.BbsPlayService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 播放量Service实现类
 */
@Service
public class BbsPlayServiceImpl implements BbsPlayService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BbsPlayServiceImpl.class);

    @Autowired
    private BbsPlayDao bbsPlayDao;
    @Autowired
    private BbsPlayCacheService bbsPlayCacheService;

    @Override
    public int playBbsMusic(Long musicId) {
        int count;
        //存入缓存
        bbsPlayCacheService.incrementPlayedCount(musicId);
        count = 1;
        return count;
    }

    @Override
    public void playedCountToDatabaseTimer() throws IOException {
        List<BbsMusicOperation> playedCountList = bbsPlayCacheService.getPlayedCountList();
        if(playedCountList == null)
            LOGGER.error("点赞数量此时为空");
        for(BbsMusicOperation musicOperation:playedCountList){
            if(musicOperation.getMusicId() != null){
                Long id = bbsPlayDao.selectPlayedCountByMusicId(musicOperation.getMusicId());
                System.out.println("播放量id："+id);
                if(id == null){
                    //不存在，直接存入
                    bbsPlayDao.insertPlayedCount(musicOperation);
                } else {
                    //已存在，更新数量
                    Integer playCount = musicOperation.getPlayCount();
                    musicOperation.setPlayCount(playCount);
                    bbsPlayDao.updatePlayedCount(musicOperation);
                }
            }
            //从 Redis 中删除
            bbsPlayCacheService.delPlayedCount(musicOperation.getMusicId());
        }
    }
}
