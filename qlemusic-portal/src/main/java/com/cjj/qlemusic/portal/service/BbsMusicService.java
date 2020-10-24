package com.cjj.qlemusic.portal.service;

import com.cjj.qlemusic.portal.entity.BbsMusic;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 音乐片段Service
 */
public interface BbsMusicService {
    /**
     * 获取推荐的音乐片段
     * @param pageNum
     * @param pageSize
     * @return
     * @throws IOException
     */
    List<BbsMusic> getRecommendList(Integer pageNum,Integer pageSize) throws IOException;

    /**
     * 获取我的音乐片段
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param category
     * @return
     */
    List<BbsMusic> getMyMusicList(Integer pageNum,Integer pageSize,Long userId,String category);

    /**
     * 获取相应收藏夹的内容
     * @param pageNum
     * @param pageSize
     * @param userId
     * @param collectId
     * @return
     */
    List<BbsMusic> getMusicByCollectId(Integer pageNum,Integer pageSize,Long userId, Long collectId);

    /**
     * 发布
     * @param bbsMusic
     * @param userId
     * @param file
     */
    @Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
    int release(BbsMusic bbsMusic,Long userId, MultipartFile[] file) throws IOException;

    /**
     * 获取我的热门音乐
     * @param userId
     * @return
     */
    List<BbsMusic> getHotMusic(Long userId);
}
