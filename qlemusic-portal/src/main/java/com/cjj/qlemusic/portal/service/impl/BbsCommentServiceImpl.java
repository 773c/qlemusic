package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.portal.dao.BbsCommentDao;
import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsReplyuserComment;
import com.cjj.qlemusic.portal.entity.BbsUserComment;
import com.cjj.qlemusic.portal.service.BbsCommentCacheService;
import com.cjj.qlemusic.portal.service.BbsCommentService;
import com.cjj.qlemusic.security.dao.UmsUserDao;
import com.cjj.qlemusic.security.entity.UmsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * 评论管理Service实现类
 */
@Service
public class BbsCommentServiceImpl implements BbsCommentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(BbsCommentServiceImpl.class);

    @Autowired
    private BbsCommentDao bbsCommentDao;
    @Autowired
    private BbsCommentCacheService bbsCommentCacheService;
    @Autowired
    private UmsUserDao umsUserDao;

    @Override
    public int userComment(BbsUserComment bbsUserComment) {
        int count;
        bbsUserComment.setCreateTime(new Date());
        bbsUserComment.setAvailable(true);
        System.out.println(bbsUserComment);
        //存入数据库
        bbsCommentDao.insertUserComment(bbsUserComment);
        //清除缓存中的用户评论
        bbsCommentCacheService.delUserCommentAll();
        //将用户信息存入缓存
        bbsCommentCacheService.setUserInfoToComment(bbsUserComment.getUmsUser());
        //更新评论数
        setCommentedCount(bbsUserComment.getMusicId());
        count = 1;
        return count;
    }

    @Override
    public int replyuserComment(BbsReplyuserComment bbsReplyuserComment) {
        int count;
        bbsReplyuserComment.setCreateTime(new Date());
        bbsReplyuserComment.setAvailable(true);
        System.out.println(bbsReplyuserComment);
        bbsCommentDao.insertReplyuserComment(bbsReplyuserComment);
        //清除缓存中的回复评论
        bbsCommentCacheService.delReplyuserCommentAll();
        //将用户信息存入缓存
        bbsCommentCacheService.setUserInfoToComment(bbsReplyuserComment.getUmsUser());
        //更新评论数
        setCommentedCount(bbsReplyuserComment.getMusicId());
        count = 1;
        return count;
    }

    @Override
    public List<UmsUser> getUserByComment(List<Long> userIds) {
        return umsUserDao.selectUserByIds(userIds);
    }

    @Override
    public List<BbsUserComment> getCommentByMusicIds(List<Long> musicIdList) throws IOException {
        int count;
        List<BbsUserComment> userCommentList = bbsCommentCacheService.getUserCommentList();
        List<BbsReplyuserComment> replyuserCommentList = bbsCommentCacheService.getReplyuserCommentList();
        List<UmsUser> userList = bbsCommentCacheService.getUserList();
        Set<Long> userIdSet = new HashSet<>();//将用户id存入Set中
        //如果用户评论没命中缓存，则从数据库查询
        if(userCommentList.size() == 0 || userCommentList == null){
            userCommentList = bbsCommentDao.selectUserCommentByMusicIds(musicIdList);
            //将用户id存入Set中
            for (BbsUserComment userComment:userCommentList)
                userIdSet.add(userComment.getUserId());
            //将数据存入缓存
            for (BbsUserComment userComment : userCommentList)
                bbsCommentCacheService.setUserComment(userComment);
        }
        //如果回复评论没命中缓存，则从数据库查询
        if(replyuserCommentList.size() == 0 || replyuserCommentList == null){
            replyuserCommentList = bbsCommentDao.selectReplyuserCommentByMusicIds(musicIdList);
            for (BbsReplyuserComment replyuserComment:replyuserCommentList)
                userIdSet.add(replyuserComment.getUserId());
            for (BbsReplyuserComment replyuserComment : replyuserCommentList)
                bbsCommentCacheService.setReplyuserComment(replyuserComment);
        }
        //如果用户评论用户信息缓存不命中，则从数据库查询
        if(userList.size() == 0 || userList == null){
            if(userIdSet.size() > 0){
                userList = bbsCommentDao.selectUserByIds(userIdSet);
                //将用户评论的用户信息存入缓存
                for (UmsUser user:userList)
                    bbsCommentCacheService.setUserInfoToComment(user);
            }
        }
        //将用户信息存入回复评论中
        for (BbsReplyuserComment replyuserComment : replyuserCommentList) {
            for (UmsUser user : userList) {
                if (replyuserComment.getUserId() == user.getId())
                    replyuserComment.setUmsUser(user);
            }
        }
        //将回复评论存入用户评论中
        List<BbsReplyuserComment> bbsReplyuserCommentList = null;
        for (BbsUserComment userComment : userCommentList) {
            bbsReplyuserCommentList = new ArrayList<>();
            //将用户信息存入用户评论
            for(UmsUser user:userList){
                if(userComment.getUserId() == user.getId())
                    userComment.setUmsUser(user);
            }
            for (BbsReplyuserComment replyuserComment : replyuserCommentList) {
                if (userComment.getMusicId() == replyuserComment.getMusicId() &&
                        userComment.getId() == replyuserComment.getRowId())
                    bbsReplyuserCommentList.add(replyuserComment);
            }
            userComment.setBbsReplyuserCommentList(bbsReplyuserCommentList);
        }
        count = 1;
        if(count == 1)
            return userCommentList;
        else
            return null;
    }

    @Override
    public void setCommentedCount(Long musicId) {
        Integer commentedCount = bbsCommentCacheService.getCommentedCount(musicId);
        if(commentedCount != null){
            //缓存不为空
            bbsCommentCacheService.incrementCommentCount(musicId);
        }else {
            //缓存为空，查询数据库
            BbsMusicOperation bbsMusicOperationDao = bbsCommentDao.selectCommentedCountById(musicId);
            if(bbsMusicOperationDao == null){
                bbsMusicOperationDao = new BbsMusicOperation();
                bbsMusicOperationDao.setCommentCount(1);
                bbsMusicOperationDao.setMusicId(musicId);
                bbsCommentDao.insertCommentedCount(bbsMusicOperationDao);
            }else {
                bbsMusicOperationDao.setCommentCount(bbsMusicOperationDao.getCommentCount()+1);
                bbsCommentDao.updateCommentedCount(bbsMusicOperationDao);
            }
            bbsCommentCacheService.setCommentedCount(bbsMusicOperationDao.getMusicId(),bbsMusicOperationDao.getCommentCount());
        }
    }

    @Override
    public List<BbsMusicOperation> getCommentOperationList(List<Long> musicIdList) throws IOException {
        List<BbsMusicOperation> commentedCountList = new ArrayList<>();
        for (Long musicId:musicIdList){
            BbsMusicOperation bbsMusicOperation = new BbsMusicOperation();
            Integer commentedCount = bbsCommentCacheService.getCommentedCount(musicId);
            if(commentedCount == null){
                bbsMusicOperation = bbsCommentDao.selectCommentedCountById(musicId);
                if (bbsMusicOperation!=null && bbsMusicOperation.getCommentCount() != null) {
                    bbsCommentCacheService.setCommentedCount(
                            bbsMusicOperation.getMusicId(),
                            bbsMusicOperation.getCommentCount());
                }else {
                    bbsMusicOperation = new BbsMusicOperation();
                }
            }else {
                bbsMusicOperation.setMusicId(musicId);
                bbsMusicOperation.setCommentCount(commentedCount);
            }
            commentedCountList.add(bbsMusicOperation);
        }
        //如果评论数量未命中缓存，则从数据库中查询
//        if (commentedCountList.size() == 0 || commentedCountList == null) {
//            //获取评论数量
//            commentedCountList = bbsCommentDao.selectCommentedCountByMusicIds(musicIdList);
//            //将数据存入缓存
//            for (BbsMusicOperation commentOperation : commentedCountList) {
//                //这里做判断是因为，点赞，播放，评论数量在一张表
//                if (commentOperation.getCommentCount() != null) {
//                    bbsCommentCacheService.setCommentedCount(
//                            commentOperation.getMusicId(),
//                            commentOperation.getCommentCount());
//                }
//            }
//        }
        return commentedCountList;
    }

    @Override
    public void commentedCountToDatabaseTimer() throws IOException {
        List<BbsMusicOperation> commentedCountList = bbsCommentCacheService.getCommentedCountList();
        if (commentedCountList == null)
            LOGGER.error("评论更新数据库之缓存评论数量为空");
        for (BbsMusicOperation musicOperation : commentedCountList) {
            if (musicOperation.getMusicId() != null) {
                BbsMusicOperation musicOperationDao = bbsCommentDao.selectCommentedCountById(musicOperation.getMusicId());
                if (musicOperationDao == null) {
                    //不存在，直接插入
                    bbsCommentDao.insertCommentedCount(musicOperation);
                } else {
                    //已存在，直接更新
                    if (musicOperationDao.getCommentCount() == null)
                        musicOperationDao.setCommentCount(0);
                    musicOperationDao.setCommentCount(musicOperation.getCommentCount());
                    bbsCommentDao.updateCommentedCount(musicOperationDao);
                }
            } else {
                LOGGER.error("评论数量未知错误");
            }
            //存到数据库后从 Redis 中删除
            bbsCommentCacheService.delCommentedCount(musicOperation.getMusicId());
        }
    }


}
