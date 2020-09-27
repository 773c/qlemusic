package com.cjj.qlemusic.admin.dao;

import com.cjj.qlemusic.admin.dto.AmsAuthor;

import java.util.List;

/**
 * 后台歌手管理Dao
 */
public interface AmsAuthorDao {
    /**
     * 查询所有歌手
     */
    List<AmsAuthor> selectAll();
}
