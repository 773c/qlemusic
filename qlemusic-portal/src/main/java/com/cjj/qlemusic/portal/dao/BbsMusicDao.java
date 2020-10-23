package com.cjj.qlemusic.portal.dao;

import com.cjj.qlemusic.portal.entity.BbsMusic;
import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.entity.BbsUserLike;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 音乐片段Dao
 */
public interface BbsMusicDao {
    /**
     * 获取推荐的音乐片段
     * @return
     */
    List<BbsMusic> selectRecommendList();

    /**
     * 获取我的音乐片段
     * @param userId
     * @param category
     * @return
     */
    List<BbsMusic> selectMyMusicList(@Param("userId") Long userId,@Param("category") String category);
    /**
     * 获取相应收藏夹的内容
     * @param userId
     * @param collectId
     * @return
     */
    List<BbsMusic> selectMusicByCollectId(@Param("userId") Long userId,@Param("collectId") Long collectId);

    /**
     * 发布
     * @param bbsMusic
     * @param userId
     */
    void insertBbsMusic(@Param("bbsMusic") BbsMusic bbsMusic,@Param("userId") Long userId);

    /**
     * 获取我的热门音乐
     * @param userId
     * @return
     */
    List<BbsMusic> selectHotMusic(Long userId);
}
