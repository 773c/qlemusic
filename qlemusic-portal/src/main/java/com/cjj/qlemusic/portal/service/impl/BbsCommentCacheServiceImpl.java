package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsReplyuserComment;
import com.cjj.qlemusic.portal.entity.BbsUserComment;
import com.cjj.qlemusic.portal.service.BbsCommentCacheService;
import com.cjj.qlemusic.security.entity.UmsUser;
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
    @Value("${redis.key.userCommentedCount}")
    private String userCommentedCountKey;
    @Value("${redis.key.userInfoToComment}")
    private String userInfoToCommentKey;

    @Autowired
    private RedisService redisService;

    @Override
    public void setUserComment(BbsUserComment bbsUserComment) {
        //使用list对评论进行时间排序
        redisService.lpush(database+userCommentKey,bbsUserComment);
//        redisService.hset(
//                database+userCommentKey,
//                bbsUserComment.getUserId()+"::"+bbsUserComment.getMusicId()+"::"+ DateUtil.formatDateTime(bbsUserComment.getCreateTime()),
//                bbsUserComment);
    }

    @Override
    public void delUserComment() {
        redisService.lpop(database+userCommentKey);
//        redisService.hdel(
//                database+userCommentKey,
//                bbsUserComment.getUserId()+"::"+bbsUserComment.getMusicId()+"::"+ DateUtil.formatDateTime(bbsUserComment.getCreateTime()));
    }

    @Override
    public void setReplyuserComment(BbsReplyuserComment bbsReplyuserComment) {
        //使用list对评论进行时间排序
        redisService.lpush(database+replyUserCommentKey,bbsReplyuserComment);
//        redisService.hset(
//                database+replyUserCommentKey,
//                bbsReplyuserComment.getUserId()+"::"+bbsReplyuserComment.getReplyuserId()+"::"+bbsReplyuserComment.getCreateTime(),
//                bbsReplyuserComment);
    }

    @Override
    public void delReplyuserComment() {
        redisService.lpop(database+replyUserCommentKey);
//        redisService.hdel(
//                database+replyUserCommentKey,
//                bbsReplyuserComment.getUserId()+"::"+bbsReplyuserComment.getReplyuserId()+"::"+bbsReplyuserComment.getCreateTime());
    }

    @Override
    public void incrementCommentCount(Long commentedId) {
        redisService.hincrement(database+commentedCountKey,commentedId.toString(),1);
    }

    @Override
    public void incrementCommentRow(Long commentedId) {
        redisService.hincrement(database+userCommentedCountKey,commentedId.toString(),1);
    }

    @Override
    public Integer getCommentedRow(Long commentedId) {
        return (Integer) redisService.hget(database+userCommentedCountKey,commentedId.toString());
    }

    @Override
    public void decrementCommentCount(Long commentedId) {
        redisService.hdecrement(database+commentedCountKey,commentedId.toString(),-1);
    }

    @Override
    public void delCommentedCount(Long commentedId) {
        redisService.hdel(database+commentedCountKey,commentedId.toString());
    }

    @Override
    public void delCommentedRow(Long commentedId) {
        redisService.hdel(database+userCommentedCountKey,commentedId.toString());
    }

    @Override
    public Integer getCommentedCount(Long commentedId) {
        return (Integer) redisService.hget(database+commentedCountKey,commentedId.toString());
    }

    @Override
    public void setCommentedCount(Long commentedId,Integer commentCount) {
        redisService.hset(database+commentedCountKey,commentedId.toString(),commentCount);
    }

    @Override
    public void setCommentedRow(Long commentedId, Integer commentRow) {
        redisService.hset(database+userCommentedCountKey,commentedId.toString(),commentRow);
    }

    @Override
    public List<BbsUserComment> getUserCommentList(){
        List<Object> list = redisService.lrange(database + userCommentKey, 0, -1);
        List<BbsUserComment> bbsUserCommentList = new ArrayList<>();
        for (Object o:list){
            bbsUserCommentList.add((BbsUserComment) o);
        }
        return bbsUserCommentList;
    }

    @Override
    public List<BbsReplyuserComment> getReplyuserCommentList(){
        List<Object> list = redisService.lrange(database+replyUserCommentKey,0,-1);
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
    public List<BbsMusicOperation> getUserCommentedCountList() throws IOException {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.hscan(database+userCommentedCountKey, ScanOptions.NONE);
        List<BbsMusicOperation> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            Integer userCommentCount = (Integer) entry.getValue();

            //封装到BbsMusicOperation实体类
            BbsMusicOperation bbsMusicOperation = new BbsMusicOperation();
            bbsMusicOperation.setUserCommentCount(userCommentCount);
            bbsMusicOperation.setMusicId(Long.parseLong(key));
            list.add(bbsMusicOperation);
        }
        cursor.close();
        return list;
    }

    @Override
    public List<UmsUser> getUserList() throws IOException {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.hscan(database+userInfoToCommentKey, ScanOptions.NONE);
        List<UmsUser> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            UmsUser umsUser = (UmsUser) entry.getValue();
            list.add(umsUser);
        }
        cursor.close();
        return list;
    }

    @Override
    public void setUserInfoToComment(UmsUser umsUser) {
        redisService.hset(database+userInfoToCommentKey,umsUser.getId().toString(),umsUser);
    }

    @Override
    public void delUserInfoToComment(Long userId) {
        redisService.hdel(database+userInfoToCommentKey,userId.toString());
    }
}
