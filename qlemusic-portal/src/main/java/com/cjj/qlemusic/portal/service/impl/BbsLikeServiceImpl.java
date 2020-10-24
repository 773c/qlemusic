package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.common.exception.Asserts;
import com.cjj.qlemusic.portal.dao.BbsLikeDao;
import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsUserLike;
import com.cjj.qlemusic.portal.service.BbsLikeCacheService;
import com.cjj.qlemusic.portal.service.BbsLikeService;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 点赞Service实现类
 */
@Service
public class BbsLikeServiceImpl implements BbsLikeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BbsLikeServiceImpl.class);

    @Autowired
    private BbsLikeDao bbsLikeDao;
    @Autowired
    private BbsLikeCacheService bbsLikeCacheService;


    @Override
    public Map<String,Object> like(BbsUserLike bbsUserLike) throws IOException {
        Object isLike = bbsLikeCacheService.getUserLike(bbsUserLike.getMusicId(), bbsUserLike.getUserId());
        //当缓存中存在，说明该用户已经点赞过
        if(isLike != null){
            //如果isLike为true，则此时取消点赞
            if(isLike.equals(true)){
                //将用户点赞的内容添加到缓存redis
                bbsLikeCacheService.setUserLike(bbsUserLike.getMusicId(),bbsUserLike.getUserId(),false);
                //将被点赞的内容的点赞数自减1
                bbsLikeCacheService.decrementLikedCount(bbsUserLike.getMusicId());
            }else {
                bbsLikeCacheService.setUserLike(bbsUserLike.getMusicId(),bbsUserLike.getUserId(),true);
                //将被点赞的内容的点赞数自增加1
                bbsLikeCacheService.incrementLikedCount(bbsUserLike.getMusicId());
            }
        }
        //否则，缓存不存在（数据库可能有或没有数据），根据前端传来的isLike进行赋值
        else{
            if(bbsUserLike.getIsLike() == true){
                //将被点赞的内容的点赞数自增加1
                bbsLikeCacheService.incrementLikedCount(bbsUserLike.getMusicId());
            }else {
                //将被点赞的内容的点赞数自减1
                bbsLikeCacheService.decrementLikedCount(bbsUserLike.getMusicId());
            }
            //将用户点赞的内容添加到缓存redis
            bbsLikeCacheService.setUserLike(bbsUserLike.getMusicId(),bbsUserLike.getUserId(),bbsUserLike.getIsLike());
        }
        Map<String,Object> data = new HashMap<>();
        data.put("isLike",bbsLikeCacheService.getUserLike(bbsUserLike.getMusicId(),bbsUserLike.getUserId()));
        data.put("likeCount",bbsLikeCacheService.getLikedCount(bbsUserLike.getMusicId()));
        return data;
    }

    @Override
    public List<BbsUserLike> getUserLikeList(List<Long> musicIdList) throws IOException {
        List<BbsUserLike> userLikeList = bbsLikeCacheService.getUserLikeList();
        boolean flag = true;
        for (Long musicId:musicIdList) {
            for (BbsUserLike userLike:userLikeList){
                if(musicId == userLike.getMusicId()){
                    flag = false;
                }
            }
        }
        if(flag)
            userLikeList = new ArrayList<>();
        //如果用户点赞未命中缓存，则从数据库中查询
        if (userLikeList.size() == 0 || userLikeList == null) {
            //获取用户点赞数据
            userLikeList = bbsLikeDao.selectUserLikeByMusicIds(musicIdList);
            //将数据存入缓存
            for (BbsUserLike userLike : userLikeList) {
                bbsLikeCacheService.setUserLike(userLike.getMusicId(), userLike.getUserId(), userLike.getIsLike());
            }
        }
        return userLikeList;
    }

    @Override
    public List<BbsMusicOperation> getLikeOperationList(List<Long> musicIdList) throws IOException {
        List<BbsMusicOperation> likedCountList = new ArrayList<>();
        for (Long musicId:musicIdList) {
            BbsMusicOperation bbsMusicOperation = new BbsMusicOperation();
            Integer likedCount = (Integer) bbsLikeCacheService.getLikedCount(musicId);
            if(likedCount == null){
                bbsMusicOperation = bbsLikeDao.selectLikedCountById(musicId);
                if (bbsMusicOperation!=null && bbsMusicOperation.getLikeCount() != null) {
                    bbsLikeCacheService.setLikedCount(
                            bbsMusicOperation.getMusicId(),
                            bbsMusicOperation.getLikeCount());
                }else {
                    bbsMusicOperation = new BbsMusicOperation();
                }
            }else {
                bbsMusicOperation.setMusicId(musicId);
                bbsMusicOperation.setLikeCount(likedCount);
            }
            likedCountList.add(bbsMusicOperation);
        }
//        //如果用户点赞未命中缓存，则从数据库中查询
//        if (likedCountList.size() == 0 || likedCountList == null) {
//            //获取点赞数量数据
//            likedCountList = bbsLikeDao.selectLikedCountByMusicIds(musicIdList);
//            for (BbsMusicOperation likeOperation : likedCountList) {
//                //这里做判断是因为，点赞，播放，评论数量在一张表
//                if (likeOperation.getLikeCount() != null)
//                    bbsLikeCacheService.setLikedCount(likeOperation.getMusicId(), likeOperation.getLikeCount());
//            }
//        }
        return likedCountList;
    }

    @Override
    public void userLikeToDatabaseTimer() throws IOException {
        List<BbsUserLike> userLikeList = bbsLikeCacheService.getUserLikeList();
        for(BbsUserLike userLike:userLikeList){
            if(userLike.getMusicId()!=null&&userLike.getUserId()!=null){
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
            }else{
                LOGGER.error("点赞id或用户id为空");
            }
            //存到数据库后从 Redis 中删除
            bbsLikeCacheService.delUserLike(userLike.getMusicId(),userLike.getUserId());
        }
    }

    @Override
    public void likedCountToDatabaseTimer() throws IOException {
        List<BbsMusicOperation> likedCountList = bbsLikeCacheService.getLikedCountList();
        if(likedCountList == null)
            LOGGER.error("点赞数量此时为空");
        for(BbsMusicOperation musicOperation:likedCountList){
            if(musicOperation.getMusicId() != null){
                BbsMusicOperation musicOperationDao = bbsLikeDao.selectLikedCountById(musicOperation.getMusicId());
                if(musicOperationDao == null){
                    //不存在，直接存入
                    bbsLikeDao.insertLikedCount(musicOperation);
                } else {
                    //已存在，更新数量
                    if(musicOperationDao.getLikeCount() == null)
                        musicOperationDao.setLikeCount(0);
                    Integer likeCount = musicOperation.getLikeCount();
                    musicOperationDao.setLikeCount(likeCount);
                    bbsLikeDao.updateLikedCount(musicOperationDao);
                }
            }
            //从 Redis 中删除
            bbsLikeCacheService.delLikedCount(musicOperation.getMusicId());
        }
    }

    @Override
//    @PostConstruct
    public void getLikeDataFromDao() {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ 热刷新开始");
        List<Long> list = new ArrayList<>();
        //获取点赞数量数据(经过排序后)
        List<BbsMusicOperation> musicOperationList = bbsLikeDao.selectLikedCountByMusicIds(list);
        List<BbsUserLike> userLikeList = null;
        for(BbsMusicOperation musicOperation:musicOperationList){
            list.add(musicOperation.getMusicId());
            //存入redis缓存中
            bbsLikeCacheService.setLikedCount(musicOperation.getMusicId(),musicOperation.getLikeCount());
        }
        if(list.size() != 0 || !list.isEmpty()){
            //获取用户点赞数据
            userLikeList = bbsLikeDao.selectUserLikeByMusicIds(list);
        }
        for(BbsUserLike userLike:userLikeList){
            //存入redis缓存中
            bbsLikeCacheService.setUserLike(userLike.getMusicId(),userLike.getUserId(),userLike.getIsLike());
        }
    }
}
