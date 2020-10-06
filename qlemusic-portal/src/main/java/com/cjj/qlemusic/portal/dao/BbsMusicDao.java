package com.cjj.qlemusic.portal.dao;

import com.cjj.qlemusic.portal.entity.BbsMusic;
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
     * @param id
     * @return
     */
    List<BbsMusic> selectMyMusicList(Long id);
    /**
     * 获取相应收藏夹的内容
     * @param userId
     * @param collectId
     * @return
     */
    List<BbsMusic> selectMusicByCollectId(@Param("userId") Long userId,@Param("collectId") Long collectId);
}
