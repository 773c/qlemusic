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
    @Value("${redis.key.userLike}")
    private String userLikeKey;
    @Value("${redis.key.likedCount}")
    private String likedCountKey;

    @Autowired
    private RedisService redisService;

    @Override
    public void setUserLike(Long likedId, Long userId,boolean isLike) {
        System.out.println(userLikeKey+userId+"::"+likedId+isLike);
        redisService.hset(userLikeKey,userId+"::"+likedId,isLike);
    }

    @Override
    public Object getUserLike(Long likedId, Long userId) {
        return redisService.hget(userLikeKey,userId+"::"+likedId);
    }

    @Override
    public void delUserLike(Long likedId, Long userId) {
        redisService.hdel(userLikeKey,userId+"::"+likedId);
    }

    @Override
    public void incrementLikedCount(Long likedId) {
        redisService.hincrement(likedCountKey,likedId.toString(),1);
    }

    @Override
    public void decrementLikedCount(Long likedId) {
        redisService.hincrement(likedCountKey,likedId.toString(),-1);
    }

    @Override
    public Object getLikedCount(Long likedId) {
        return redisService.hget(likedCountKey,likedId.toString());
    }

    @Override
    public void delLikedCount(Long likedId) {
        redisService.hdel(likedCountKey,likedId.toString());
    }

    @Override
    public List<BbsUserLike> getUserLikeList() throws IOException {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.hscan(userLikeKey, ScanOptions.NONE);
        List<BbsUserLike> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            System.out.println("用户点赞表："+key);
            String[] ids = key.split("::");
            String userId = ids[0];
            String likedId = ids[1];
            boolean isLike = (boolean) entry.getValue();
            System.out.println("用户id："+userId + "，音乐id：" + likedId + "，是否点赞：" + isLike);

            //封装到BbsUserLike实体类
            BbsUserLike bbsUserLike = new BbsUserLike();
            bbsUserLike.setUserId(Long.parseLong(userId));
            bbsUserLike.setMusicId(Long.parseLong(likedId));
            bbsUserLike.setIsLike(isLike);
            list.add(bbsUserLike);

            //存到 list 后从 Redis 中删除
            delUserLike(Long.parseLong(likedId),Long.parseLong(userId));
        }
        cursor.close();
        return list;
    }

    @Override
    public List<BbsMusicOperation> getLikedCountList() throws IOException {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.hscan(likedCountKey, ScanOptions.NONE);
        List<BbsMusicOperation> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            Integer likeCount = (Integer) entry.getValue();
            System.out.println("音乐id："+key+"，点赞数："+likeCount);

            //封装到BbsMusicOperation实体类
            BbsMusicOperation bbsMusicOperation = new BbsMusicOperation();
            bbsMusicOperation.setLikeCount(likeCount);
            bbsMusicOperation.setMusicId(Long.parseLong(key));
            list.add(bbsMusicOperation);

            //从 Redis 中删除
            delLikedCount(Long.parseLong(key));
        }
        cursor.close();
        return list;
    }
}
