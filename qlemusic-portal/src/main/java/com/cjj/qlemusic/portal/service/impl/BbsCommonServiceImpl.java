package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.portal.dao.BbsCommentDao;
import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.service.BbsCommentCacheService;
import com.cjj.qlemusic.portal.service.BbsCommentService;
import com.cjj.qlemusic.portal.service.BbsCommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BbsCommonServiceImpl implements BbsCommonService {
    @Autowired
    private BbsCommentCacheService bbsCommentCacheService;
    @Autowired
    private BbsCommentDao bbsCommentDao;

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
                //存入数据库
                bbsCommentDao.insertCommentedCount(bbsMusicOperationDao);
            }else {
                if(bbsMusicOperationDao.getCommentCount() == null)
                    bbsMusicOperationDao.setCommentCount(1);
                else
                    bbsMusicOperationDao.setCommentCount(bbsMusicOperationDao.getCommentCount()+1);
                //更新数据库
                bbsCommentDao.updateCommentedCount(bbsMusicOperationDao);
            }
            bbsCommentCacheService.setCommentedCount(bbsMusicOperationDao.getMusicId(),bbsMusicOperationDao.getCommentCount());
        }
    }
}
