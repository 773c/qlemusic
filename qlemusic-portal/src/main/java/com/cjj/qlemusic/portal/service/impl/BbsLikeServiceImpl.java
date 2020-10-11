package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.portal.dao.BbsLikeDao;
import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsUserLike;
import com.cjj.qlemusic.portal.service.BbsLikeCacheService;
import com.cjj.qlemusic.portal.service.BbsLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 点赞Service实现类
 */
@Service
public class BbsLikeServiceImpl implements BbsLikeService {

    @Autowired
    private BbsLikeDao bbsLikeDao;
    @Autowired
    private BbsLikeCacheService bbsLikeCacheService;


    @Override
    public int like(BbsUserLike bbsUserLike) throws IOException {
        int count;
        //将用户点赞的内容添加到缓存redis
        bbsLikeCacheService.setUserLike(bbsUserLike.getMusicId(),bbsUserLike.getUserId(),bbsUserLike.getIsLike());
        //将被点赞的内容的点赞数自增加1
        bbsLikeCacheService.incrementLikedCount(bbsUserLike.getMusicId());
        count = 1;
        return count;
    }

    @Override
    public void userLikeToDatabaseTimer() throws IOException {
        List<BbsUserLike> userLikeList = bbsLikeCacheService.getUserLikeList();
        for(BbsUserLike userLike:userLikeList){
            System.out.println("userLikeToDatabaseTimer:"+userLike);
            //查询数据库中是否存在
            BbsUserLike userLikeDao = bbsLikeDao.selectUserLikeByLikedIdAndUserId(userLike.getMusicId(), userLike.getUserId());
            if(userLikeDao == null){
                //没有记录，直接存入
                bbsLikeDao.insertUserLike(userLike);
            }else {
                //有记录，需要更新
                userLikeDao.setIsLike(userLike.getIsLike());
                bbsLikeDao.updateUserLike(userLikeDao);
            }
        }
    }

    @Override
    public void likedCountToDatabaseTimer() throws IOException {
        List<BbsMusicOperation> likedCountList = bbsLikeCacheService.getLikedCountList();
        for(BbsMusicOperation musicOperation:likedCountList){
            BbsMusicOperation musicOperationDao = bbsLikeDao.selectLikedCountById(musicOperation.getMusicId());
            if(musicOperationDao == null){
                //不存在，直接存入
                bbsLikeDao.insertLikedCount(musicOperation);
            } else {
                //已存在，更新数量
                Integer likeCount = musicOperation.getLikeCount() + musicOperationDao.getLikeCount();
                musicOperationDao.setLikeCount(likeCount);
                bbsLikeDao.updateLikedCount(musicOperationDao);
            }
        }
    }
}
