package com.cjj.qlemusic.admin.service;

import com.cjj.qlemusic.admin.dto.AmsAuthor;

import java.util.List;

/**
 * 后台管理歌手Service
 */
public interface AmsAuthorService {
    /**
     * 获取所有歌手信息
     */
    List<AmsAuthor> getAll(Integer pageNum,Integer pageSize);
}
