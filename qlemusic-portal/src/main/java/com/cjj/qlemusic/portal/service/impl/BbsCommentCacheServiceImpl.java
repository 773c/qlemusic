package com.cjj.qlemusic.portal.service.impl;

import cn.hutool.core.date.DateUtil;
import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsReplyuserComment;
import com.cjj.qlemusic.portal.entity.BbsUserComment;
import com.cjj.qlemusic.portal.service.BbsCommentCacheService;
import com.cjj.qlemusic.security.entity.UmsUserInfo;
import com.cjj.qlemusic.security.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 评论缓存Service实现类
 */
@Service
public class BbsCommentCacheServiceImpl implements BbsCommentCacheService {
    @Value("${redis.database}")
    private String database;
    @Value("${redis.key.userComment}")
    private String userCommentKey;
    @Value("${redis.key.replyUserComment}")
    private String replyUserCommentKey;
    @Value("${redis.key.commentedCount}")
    private String commentedCountKey;
    @Value("${redis.key.userInfoToComment}")
    private String userInfoToCommentKey;

    @Autowired
    private RedisService redisService;

    @Override
    public void setUserComment(BbsUserComment bbsUserComment) {
//        redisService.lpush(
//                database+userCommentKey+bbsUserComment.getMusicId()+":",
//                bbsUserComment);
        String key = database+userCommentKey+bbsUserComment.getMusicId()+":";
        Double formatCreateTime = Double.parseDouble(
                DateUtil.format(bbsUserComment.getCreateTime(),
                        "yyyyMMdd.HHmmss"));
        redisService.addZset(key, bbsUserComment, formatCreateTime);
    }

    @Override
    public Object getUserComment(Long musicId) {
        String key = database+userCommentKey+musicId+":";
        return redisService.getZsetAll(key,0,-1);
    }

    @Override
    public void delUserCommentByMusic(Long musicId) {
        String key = database + userCommentKey+musicId+":";
        redisService.delZset(key,0,-1);
    }

    @Override
    public void setReplyuserComment(BbsReplyuserComment bbsReplyuserComment) {
//        redisService.lpush(database+replyUserCommentKey,bbsReplyuserComment);
        String key = database+replyUserCommentKey+bbsReplyuserComment.getMusicId()+":";
        Double formatCreateTime = Double.parseDouble(
                DateUtil.format(bbsReplyuserComment.getCreateTime(),
                        "yyyyMMdd.HHmmss"));
        redisService.addZset(key, bbsReplyuserComment, formatCreateTime);
    }

    @Override
    public void delReplyuserCommentByMusic(Long musicId) {
        String key = database + replyUserCommentKey + musicId+":";
        redisService.delZset(key,0,-1);
    }

    @Override
    public void incrementCommentCount(Long commentedId) {
        String key = database+commentedCountKey;
        redisService.hincrement(key,commentedId.toString(),1);
    }

    @Override
    public void decrementCommentCount(Long commentedId) {
        String key = database+commentedCountKey;
        redisService.hdecrement(key,commentedId.toString(),-1);
    }

    @Override
    public void delCommentedCount(Long commentedId) {
        String keys = database+commentedCountKey;
        redisService.hdel(keys,commentedId.toString());
    }

    @Override
    public Integer getCommentedCount(Long commentedId) {
        String keys = database+commentedCountKey;
        return (Integer) redisService.hget(keys,commentedId.toString());
    }

    @Override
    public void setCommentedCount(Long commentedId,Integer commentCount) {
        String keys = database+commentedCountKey;
        redisService.hset(keys,commentedId.toString(),commentCount);
    }

    @Override
    public List<BbsUserComment> getUserCommentList(Long musicId){
        Set<Object> list = (Set<Object>) redisService.getZsetAll(database+userCommentKey+musicId+":",0,-1);
        List<BbsUserComment> bbsUserCommentList = new ArrayList<>();
        for (Object o:list){
            bbsUserCommentList.add((BbsUserComment) o);
        }
        return bbsUserCommentList;
    }

    @Override
    public List<BbsReplyuserComment> getReplyuserCommentList(Long musicId){
        Set<Object> list = (Set<Object>) redisService.getZsetAll(database+replyUserCommentKey+musicId+":",0,-1);
        List<BbsReplyuserComment> bbsReplyuserCommentList = new ArrayList<>();
        for (Object o:list){
            bbsReplyuserCommentList.add((BbsReplyuserComment) o);
        }
        return bbsReplyuserCommentList;
    }

    @Override
    public List<BbsMusicOperation> getCommentedCountList() throws IOException {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.hscan(database+commentedCountKey, ScanOptions.NONE);
        List<BbsMusicOperation> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            Integer commentCount = (Integer) entry.getValue();

            //封装到BbsMusicOperation实体类
            BbsMusicOperation bbsMusicOperation = new BbsMusicOperation();
            bbsMusicOperation.setCommentCount(commentCount);
            bbsMusicOperation.setMusicId(Long.parseLong(key));
            list.add(bbsMusicOperation);
        }
        cursor.close();
        return list;
    }

    @Override
    public List<UmsUserInfo> getUserInfoList() throws IOException {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.hscan(database+userInfoToCommentKey, ScanOptions.NONE);
        List<UmsUserInfo> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            UmsUserInfo umsUserInfo = (UmsUserInfo) entry.getValue();
            list.add(umsUserInfo);
        }
        cursor.close();
        return list;
    }

    @Override
    public void setUserInfoToComment(UmsUserInfo umsUserInfo) {
        String keys = database+userInfoToCommentKey;
        redisService.hset(keys, umsUserInfo.getId().toString(), umsUserInfo);
    }

    @Override
    public void delUserInfoToComment(Long userId) {
        String keys = database+userInfoToCommentKey;
        redisService.hdel(keys,userId.toString());
    }

}
