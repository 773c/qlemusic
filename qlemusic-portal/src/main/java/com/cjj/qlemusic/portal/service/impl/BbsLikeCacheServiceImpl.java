package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsUserLike;
import com.cjj.qlemusic.portal.service.BbsLikeCacheService;
import com.cjj.qlemusic.security.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 点赞缓存Service实现类
 * 首先，需要记录哪个用户点赞了哪个音乐（user_like）
 * 其次，需要记录该音乐被点赞的次数
 */
@Service
public class BbsLikeCacheServiceImpl implements BbsLikeCacheService {
    @Value("${redis.database}")
    private String database;
    @Value("${redis.key.userLike}")
    private String userLikeKey;
    @Value("${redis.key.likedCount}")
    private String likedCountKey;

    @Autowired
    private RedisService redisService;

    @Override
    public void setUserLike(Long likedId, Long userId,boolean isLike) {
        String keys = database+userLikeKey;
        redisService.hset(keys,userId+"::"+likedId,isLike);
    }

    @Override
    public Object getUserLike(Long likedId, Long userId) {
        String keys = database+userLikeKey;
        return redisService.hget(keys,userId+"::"+likedId);
    }

    @Override
    public void delUserLike(Long likedId, Long userId) {
        String keys = database+userLikeKey;
        redisService.hdel(keys,userId+"::"+likedId);
    }

    @Override
    public void setLikedCount(Long musicId, Integer likeCount) {
        String keys = database+likedCountKey;
        redisService.hset(keys,musicId.toString(),likeCount);
    }

    @Override
    public void incrementLikedCount(Long likedId) {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ 音乐ID："+likedId+"被点赞了");
        String keys = database+likedCountKey;
        redisService.hincrement(keys,likedId.toString(),1);
    }

    @Override
    public void decrementLikedCount(Long likedId) {
        String keys = database+likedCountKey;
        redisService.hincrement(keys,likedId.toString(),-1);
    }

    @Override
    public Object getLikedCount(Long likedId) {
        String keys = database+likedCountKey;
        return redisService.hget(keys,likedId.toString());
    }

    @Override
    public void delLikedCount(Long likedId) {
        String keys = database+likedCountKey;
        redisService.hdel(keys,likedId.toString());
    }

    @Override
    public List<BbsUserLike> getUserLikeList() throws IOException {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.hscan(database+userLikeKey, ScanOptions.NONE);
        List<BbsUserLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            String[] ids = key.split("::");
            String userId = ids[0];
            String likedId = ids[1];
            boolean isLike = (boolean) entry.getValue();

            //封装到BbsUserLike实体类
            BbsUserLike bbsUserLike = new BbsUserLike();
            bbsUserLike.setUserId(Long.parseLong(userId));
            bbsUserLike.setMusicId(Long.parseLong(likedId));
            bbsUserLike.setIsLike(isLike);
            list.add(bbsUserLike);
        }
        cursor.close();
        return list;
    }

    @Override
    public List<BbsMusicOperation> getLikedCountList() throws IOException {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.hscan(database+likedCountKey, ScanOptions.NONE);
        List<BbsMusicOperation> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            Integer likeCount = (Integer) entry.getValue();

            //封装到BbsMusicOperation实体类
            BbsMusicOperation bbsMusicOperation = new BbsMusicOperation();
            bbsMusicOperation.setLikeCount(likeCount);
            bbsMusicOperation.setMusicId(Long.parseLong(key));
            list.add(bbsMusicOperation);
        }
        cursor.close();
        return list;
    }
}
