package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.portal.dao.BbsMusicDao;
import com.cjj.qlemusic.portal.entity.BbsMusic;
import com.cjj.qlemusic.portal.service.BbsMusicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 音乐片段Service实现类
 */
@Service
public class BbsMusicServiceImpl implements BbsMusicService {
    @Autowired
    private BbsMusicDao bbsMusicDao;
    @Override
    public List<BbsMusic> getRecommendList() {
        return bbsMusicDao.selectRecommendList();
    }
}
