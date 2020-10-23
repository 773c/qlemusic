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
import java.util.ArrayList;
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
    public List<BbsMusicOperation> getPlayOperationList(List<Long> musicIdList) throws IOException {
        List<BbsMusicOperation> playedCountList = new ArrayList<>();
        for (Long musicId:musicIdList){
            BbsMusicOperation bbsMusicOperation = new BbsMusicOperation();
            Integer playedCount = bbsPlayCacheService.getPlayedCount(musicId);
            if(playedCount == null){
                bbsMusicOperation = bbsPlayDao.selectPlayedCountByMusicId(musicId);
                if (bbsMusicOperation!=null && bbsMusicOperation.getPlayCount() != null) {
                    bbsPlayCacheService.setPlayedCount(
                            bbsMusicOperation.getMusicId(),
                            bbsMusicOperation.getPlayCount());
                }else {
                    bbsMusicOperation = new BbsMusicOperation();
                }
            }else {
                bbsMusicOperation.setMusicId(musicId);
                bbsMusicOperation.setPlayCount(playedCount);
            }
            playedCountList.add(bbsMusicOperation);
        }
//        //如果播放数量未命中缓存，则从数据库中查询
//        if (playedCountList.size() == 0 || playedCountList == null) {
//            //获取播放数量
//            playedCountList = bbsPlayDao.selectPlayedCountByMusicIds(musicIdList);
//            //将数据存入缓存
//            for (BbsMusicOperation playOperation : playedCountList) {
//                //这里做判断是因为，点赞，播放，评论数量在一张表
//                if (playOperation.getPlayCount() != null) {
//                    bbsPlayCacheService.setPlayedCount(
//                            playOperation.getMusicId(),
//                            playOperation.getPlayCount());
//                }
//            }
//        }
        return playedCountList;
    }

    @Override
    public void playedCountToDatabaseTimer() throws IOException {
        List<BbsMusicOperation> playedCountList = bbsPlayCacheService.getPlayedCountList();
        if(playedCountList == null)
            LOGGER.error("点赞数量此时为空");
        for(BbsMusicOperation musicOperation:playedCountList){
            if(musicOperation.getMusicId() != null){
                BbsMusicOperation musicOperationDao = bbsPlayDao.selectPlayedCountByMusicId(musicOperation.getMusicId());
                if(musicOperationDao == null){
                    //不存在，直接存入
                    bbsPlayDao.insertPlayedCount(musicOperation);
                } else {
                    //已存在，更新数量
                    Integer playCount = musicOperation.getPlayCount();
                    musicOperationDao.setPlayCount(playCount);
                    bbsPlayDao.updatePlayedCount(musicOperationDao);
                }
            }
            //从 Redis 中删除
            bbsPlayCacheService.delPlayedCount(musicOperation.getMusicId());
        }
    }
}
