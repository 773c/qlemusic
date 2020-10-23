package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.common.exception.Asserts;
import com.cjj.qlemusic.common.util.FileUploadUtil;
import com.cjj.qlemusic.portal.dao.BbsCommentDao;
import com.cjj.qlemusic.portal.dao.BbsLikeDao;
import com.cjj.qlemusic.portal.dao.BbsMusicDao;
import com.cjj.qlemusic.portal.dao.BbsPlayDao;
import com.cjj.qlemusic.portal.entity.*;
import com.cjj.qlemusic.portal.service.*;
import com.cjj.qlemusic.security.service.OssService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * 音乐片段Service实现类
 */
@Service
public class BbsMusicServiceImpl implements BbsMusicService {
    @Value("${redis.database}")
    private String database;
    @Value("${redis.key.prefixId}")
    private String prefixId;
    @Value("${aliyun.oss.host}")
    private String host;

    @Autowired
    private BbsMusicDao bbsMusicDao;
    @Autowired
    private BbsLikeService bbsLikeService;
    @Autowired
    private BbsPlayService bbsPlayService;
    @Autowired
    private BbsCommentService bbsCommentService;
    @Autowired
    private OssService ossService;

    @Override
    public List<BbsMusic> getRecommendList(Integer pageNum, Integer pageSize) throws IOException {
        PageHelper.startPage(pageNum, pageSize);
        List<BbsMusic> bbsMusicList = bbsMusicDao.selectRecommendList();
        List<Long> musicIdList = new ArrayList<>();
        //如果获取音乐为空
        if (bbsMusicList.size() == 0 || bbsMusicList == null)
            throw new NullPointerException("推荐音乐数据为空");
        //将获取到的音乐id存入list（比如推荐），根据id来从数据库获取相应热点点赞、播放量、评论数据
        for (BbsMusic bbsMusic : bbsMusicList) {
            musicIdList.add(bbsMusic.getId());
        }
        //根据音乐id集合获取评论信息
        List<BbsUserComment> userCommentList = bbsCommentService.getCommentByMusicIds(musicIdList);
        List<BbsMusicOperation> commentedCountList = bbsCommentService.getCommentOperationList(musicIdList);
        System.out.println("commentedCountList"+commentedCountList);
        List<BbsUserLike> userLikeList = bbsLikeService.getUserLikeList(musicIdList);
        List<BbsMusicOperation> likedCountList = bbsLikeService.getLikeOperationList(musicIdList);
        List<BbsMusicOperation> playedCountList = bbsPlayService.getPlayOperationList(musicIdList);
        //遍历所有音乐
        List<BbsUserLike> bbsUserLikeList = null;
        BbsMusicOperation bbsMusicOperation = null;
        List<BbsUserComment> bbsUserCommentList = null;
        for (BbsMusic bbsMusic : bbsMusicList) {
            bbsUserLikeList = new ArrayList<>();
            bbsUserCommentList = new ArrayList<>();
            bbsMusicOperation = new BbsMusicOperation();
            //遍历用户点赞
            for (BbsUserLike userLike : userLikeList) {
                if (bbsMusic.getId() == userLike.getMusicId()) {
                    bbsUserLikeList.add(userLike);
                }
            }
            bbsMusic.setBbsUserLikeList(bbsUserLikeList);
            //遍历用户评论
            for (BbsUserComment userComment : userCommentList) {
                if (bbsMusic.getId() == userComment.getMusicId()) {
                    bbsUserCommentList.add(userComment);
                }
            }
            bbsMusic.setBbsUserCommentList(bbsUserCommentList);
            //遍历点赞数量
            for (BbsMusicOperation likeOperation : likedCountList) {
                if (bbsMusic.getId() == likeOperation.getMusicId()) {
                    bbsMusicOperation.setMusicId(bbsMusic.getId());
                    bbsMusicOperation.setLikeCount(likeOperation.getLikeCount());
                    break;
                } else
                    bbsMusicOperation.setMusicId(bbsMusic.getId());
            }
            //遍历播放数量
            for (BbsMusicOperation playOperation : playedCountList) {
                if (bbsMusic.getId() == playOperation.getMusicId()) {
                    bbsMusicOperation.setMusicId(bbsMusic.getId());
                    bbsMusicOperation.setPlayCount(playOperation.getPlayCount());
                    break;
                }
            }
            //遍历评论数量
            for (BbsMusicOperation commentOperation : commentedCountList) {
                if (bbsMusic.getId() == commentOperation.getMusicId()) {
                    bbsMusicOperation.setMusicId(bbsMusic.getId());
                    bbsMusicOperation.setCommentCount(commentOperation.getCommentCount());
                    break;
                }
            }
            //将处理好的点赞、查看、评论数量存入
            bbsMusic.setBbsMusicOperation(bbsMusicOperation);
        }
        return bbsMusicList;
    }

    @Override
    public List<BbsMusic> getMyMusicList(Integer pageNum, Integer pageSize, Long userId, String category) {
        PageHelper.startPage(pageNum, pageSize);
        List<BbsMusic> myMusicList = bbsMusicDao.selectMyMusicList(userId, category);
        for (BbsMusic myMusic : myMusicList) {
            if (myMusic.getBbsMusicOperation() == null)
                myMusic.setBbsMusicOperation(new BbsMusicOperation());
        }
        return myMusicList;
    }

    @Override
    public List<BbsMusic> getMusicByCollectId(Integer pageNum, Integer pageSize, Long userId, Long collectId) {
        PageHelper.startPage(pageNum, pageSize);
        return bbsMusicDao.selectMusicByCollectId(userId, collectId);
    }

    @Override
    public int release(BbsMusic bbsMusic, Long userId, MultipartFile[] file) throws IOException {
        int count;
        String audioName = null;
        String imgName = null;
        InputStream audioIs = null;
        InputStream imgIs = null;
        if (file.length < 2) {
            Asserts.fail("文件不存在");
        }
        for (MultipartFile f : file) {
            String filename = f.getOriginalFilename();
            boolean fileType = FileUploadUtil.isAudioType(filename);
            if (fileType) {
                audioName = filename;
                audioIs = f.getInputStream();
            } else {
                imgName = filename;
                imgIs = f.getInputStream();
            }
        }
        //更改文件名称
        audioName = FileUploadUtil.getThirtyTwoBitFileName(userId.toString(), audioName);
        imgName = FileUploadUtil.getThirtyTwoBitFileName(userId.toString(), imgName);
        //上传到OSS
        String ossFileApiImgPath = ossService.uploadOss(imgName, imgIs);
        String ossFileApiAudioPath = ossService.uploadOss(audioName, audioIs);
        //添加到数据库
        bbsMusic.setAudioAvatarUrl(host + ossFileApiImgPath);
        bbsMusic.setAudioUrl(host + ossFileApiAudioPath);
        bbsMusic.setReleaseTime(new Date());
        bbsMusicDao.insertBbsMusic(bbsMusic, userId);
        count = 1;
        return count;
    }

    @Override
    public List<BbsMusic> getHotMusic(Long userId) {
        PageHelper.startPage(1, 5);
        return bbsMusicDao.selectHotMusic(userId);
    }
}
