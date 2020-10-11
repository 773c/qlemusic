package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.common.exception.Asserts;
import com.cjj.qlemusic.common.util.FileUploadUtil;
import com.cjj.qlemusic.portal.dao.BbsMusicDao;
import com.cjj.qlemusic.portal.entity.BbsMusic;
import com.cjj.qlemusic.portal.service.BbsLikeCacheService;
import com.cjj.qlemusic.portal.service.BbsMusicService;
import com.cjj.qlemusic.security.service.OssService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

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
    private OssService ossService;
    @Autowired
    private BbsLikeCacheService bbsLikeCacheService;

    @Override
    public List<BbsMusic> getRecommendList() {
        //1
        return bbsMusicDao.selectRecommendList();
    }

    @Override
    public List<BbsMusic> getMyMusicList(Long id) {

        List<BbsMusic> myMusicList = bbsMusicDao.selectMyMusicList(id);
        return myMusicList;
    }

    @Override
    public List<BbsMusic> getMusicByCollectId(Integer pageNum,Integer pageSize,Long userId, Long collectId) {
        PageHelper.startPage(pageNum,pageSize);
        return bbsMusicDao.selectMusicByCollectId(userId,collectId);
    }

    @Override
    public int release(BbsMusic bbsMusic, Long userId, MultipartFile[] file) throws IOException {
        int count;
        String audioName = null;
        String imgName = null;
        InputStream audioIs = null;
        InputStream imgIs = null;
        if(file.length<2){
            Asserts.fail("文件不存在");
        }
        for(MultipartFile f:file){
            String filename = f.getOriginalFilename();
            boolean fileType = FileUploadUtil.isAudioType(filename);
            if(fileType){
                audioName = filename;
                audioIs = f.getInputStream();
            }
            else {
                imgName = filename;
                imgIs  = f.getInputStream();
            }
        }
        //更改文件名称
        audioName = FileUploadUtil.getThirtyTwoBitFileName(userId.toString(),audioName);
        imgName = FileUploadUtil.getThirtyTwoBitFileName(userId.toString(),imgName);
        //上传到OSS
        String ossFileApiImgPath = ossService.uploadOss(imgName, imgIs);
        String ossFileApiAudioPath = ossService.uploadOss(audioName, audioIs);
        //添加到数据库
        bbsMusic.setAudioAvatarUrl(host+ossFileApiImgPath);
        bbsMusic.setAudioUrl(host+ossFileApiAudioPath);
        bbsMusic.setReleaseTime(new Date());
        bbsMusicDao.insertBbsMusic(bbsMusic,userId);
        count = 1;
        return count;
    }
}
