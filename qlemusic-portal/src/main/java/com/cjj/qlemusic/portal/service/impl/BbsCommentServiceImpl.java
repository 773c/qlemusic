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
    public void userComment(BbsUserComment bbsUserComment) {
        bbsUserComment.setCreateTime(new Date());
        bbsUserComment.setAvailable(true);
        System.out.println(bbsUserComment);
        //更新行数
        bbsCommentCacheService.incrementCommentRow(bbsUserComment.getMusicId());
        //获取行数
        Integer commentedRow = bbsCommentCacheService.getCommentedRow(bbsUserComment.getMusicId());
        if (commentedRow == null) {
            throw new RuntimeException("评论失败");
        }
        bbsUserComment.setRowId(commentedRow);
        //将用户评论存入缓存
        bbsCommentCacheService.setUserComment(bbsUserComment);
        //将用户信息存入缓存
        bbsCommentCacheService.setUserInfoToComment(bbsUserComment.getUmsUser());
        //更新评论数
        bbsCommentCacheService.incrementCommentCount(bbsUserComment.getMusicId());
    }

    @Override
    public void replyuserComment(BbsReplyuserComment bbsReplyuserComment) {
        bbsReplyuserComment.setCreateTime(new Date());
        bbsReplyuserComment.setAvailable(true);
        System.out.println(bbsReplyuserComment);
        //将回复评论存入缓存中
        bbsCommentCacheService.setReplyuserComment(bbsReplyuserComment);
        //将用户信息存入缓存
        bbsCommentCacheService.setUserInfoToComment(bbsReplyuserComment.getUmsUser());
        //更新评论数
        bbsCommentCacheService.incrementCommentCount(bbsReplyuserComment.getMusicId());
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
        //只要有评论、回复、数量缓存没有命中就从数据库查找
        //有用户评论不一定有回复，但有回复一定有用户评论，但一定都有数量
        //第一种可能：缓存和数据库什么数据都没有
        //第二种可能：缓存中只有用户评论和数量时，数据库中没有数据（第一次肯定是先有用户评论）
        //第三种可能：缓存中没有数据，从有数据的数据库中获取
        //第四种可能：缓存中只有用户评论和数量时，数据库也只有用户评论和数量数据
        if ((userCommentList.size() == 0 || userCommentList.isEmpty()) &&
                (replyuserCommentList.size() == 0 || replyuserCommentList.isEmpty())) {
            System.out.println("没有命中缓存");
            userCommentList = bbsCommentDao.selectUserCommentByMusicIds(musicIdList);
            replyuserCommentList = bbsCommentDao.selectReplyuserCommentByMusicIds(musicIdList);
            //将用户id存入Set中
            for (BbsUserComment userComment:userCommentList)
                userIdSet.add(userComment.getUserId());
            for (BbsReplyuserComment replyuserComment:replyuserCommentList)
                userIdSet.add(replyuserComment.getUserId());
            userList = bbsCommentDao.selectUserByIds(userIdSet);
            //将数据存入缓存
            for (BbsUserComment userComment : userCommentList)
                bbsCommentCacheService.setUserComment(userComment);
            for (BbsReplyuserComment replyuserComment : replyuserCommentList)
                bbsCommentCacheService.setReplyuserComment(replyuserComment);
            for (UmsUser user:userList)
                bbsCommentCacheService.setUserInfoToComment(user);
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
                        userComment.getRowId() == replyuserComment.getRowId())
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
    public void userCommentToDatabaseTimer() throws IOException {
        List<BbsUserComment> userCommentList = bbsCommentCacheService.getUserCommentList();
        List<UmsUser> userList = bbsCommentCacheService.getUserList();
        //清除评论的用户信息
        for(UmsUser user:userList){
            bbsCommentCacheService.delUserInfoToComment(user.getId());
        }
        for (BbsUserComment bbsUserComment : userCommentList) {
            BbsUserComment bbsUserCommentDao = bbsCommentDao.selectUserCommentByUMT(bbsUserComment);
            if (bbsUserCommentDao == null) {
                //直接存入
                bbsCommentDao.insertUserComment(bbsUserComment);
            }
            //存到数据库后从 Redis 中删除
            bbsCommentCacheService.delUserComment();
        }
    }

    @Override
    public void replyuserCommentToDatabaseTimer() {
        List<BbsReplyuserComment> replyuserCommentList = bbsCommentCacheService.getReplyuserCommentList();
        for (BbsReplyuserComment replyuserComment : replyuserCommentList) {
            BbsReplyuserComment bbsReplyuserCommentDao = bbsCommentDao.selectReplyuserCommentByBRMT(replyuserComment);
            if (bbsReplyuserCommentDao == null) {
                bbsCommentDao.insertReplyuserComment(replyuserComment);
            }
            //存到数据库后从 Redis 中删除
            bbsCommentCacheService.delReplyuserComment();
        }
    }

    @Override
    public void commentedCountToDatabaseTimer() throws IOException {
        List<BbsMusicOperation> commentedCountList = bbsCommentCacheService.getCommentedCountList();
        List<BbsMusicOperation> userCommentedCountList = bbsCommentCacheService.getUserCommentedCountList();
        if (commentedCountList == null || userCommentedCountList == null)
            LOGGER.error("评论更新数据库之缓存评论数量为空");
        for (BbsMusicOperation musicOperation : commentedCountList) {
            for (BbsMusicOperation userCommentedCount : userCommentedCountList) {
                if (musicOperation.getMusicId() == userCommentedCount.getMusicId()) {
                    musicOperation.setUserCommentCount(userCommentedCount.getUserCommentCount());
                    break;
                }
            }
        }
        for (BbsMusicOperation musicOperation : commentedCountList) {
            if (musicOperation.getMusicId() != null) {
                BbsMusicOperation musicOperationDao = bbsCommentDao.selectCommentedCountById(musicOperation.getMusicId());
                LOGGER.info("评论更新数据库之查询评论数量是否存在：{}", musicOperationDao);
                if (musicOperationDao == null) {
                    //不存在，直接插入
                    bbsCommentDao.insertCommentedCount(musicOperation);
                } else {
                    //已存在，直接更新
                    if (musicOperationDao.getCommentCount() == null)
                        musicOperationDao.setCommentCount(0);
                    musicOperationDao.setCommentCount(musicOperation.getCommentCount());
                    musicOperationDao.setUserCommentCount(musicOperation.getUserCommentCount());
                    bbsCommentDao.updateCommentedCount(musicOperationDao);
                }
            } else {
                LOGGER.error("评论数量未知错误");
            }
            //存到数据库后从 Redis 中删除
            bbsCommentCacheService.delCommentedCount(musicOperation.getMusicId());
            bbsCommentCacheService.delCommentedRow(musicOperation.getMusicId());
        }
    }


}
