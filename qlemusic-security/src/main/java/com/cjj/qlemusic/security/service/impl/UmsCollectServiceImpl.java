package com.cjj.qlemusic.security.service.impl;

import com.cjj.qlemusic.common.exception.Asserts;
import com.cjj.qlemusic.security.dao.UmsCollectDao;
import com.cjj.qlemusic.security.entity.UmsCollect;
import com.cjj.qlemusic.security.service.UmsCollectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 收藏管理Service实现类
 */
@Service
public class UmsCollectServiceImpl implements UmsCollectService {
    @Autowired
    private UmsCollectDao collectDao;

    @Override
    public Long createCollect(Long userId,UmsCollect umsCollect) {
        int count;
        //判断收藏夹是否已存在
        UmsCollect isExist = collectDao.selectIsExistCollect(umsCollect.getName());
        if(isExist != null){
            Asserts.fail("收藏夹已存在");
        }
        //将收藏夹添加到数据库中
        collectDao.createCollect(umsCollect);
        //将用户和收藏夹的联系添加到数据库中
        collectDao.insertUserAndCollect(userId, umsCollect.getId());
        count = 1;
        if(count == 1)
            return umsCollect.getId();
        else
            return null;
    }

    @Override
    public List<UmsCollect> getCollectList(Long id) {
        return collectDao.selectCollectList(id);
    }

    @Override
    public int addCollect(Long collectId, Long musicId) {
        int count;
        collectDao.insertCollect(collectId,musicId);
        count = 1;
        return count;
    }

    @Override
    public int deleteCollectContent(Long collectMusicId) {
        int count;
        collectDao.deleteCollectContent(collectMusicId);
        count = 1;
        return count;
    }

    @Override
    public int deleteCollect(Long id) {
        int count;
        collectDao.deleteCollect(id);
        count = 1;
        return count;
    }

    @Override
    public int deleteCollectAndMove(Long id,Long moveId) {
        int count;
        //移动收藏夹内容
        collectDao.updateMoveCollectContent(id,moveId);
        //删除收藏夹
        collectDao.deleteCollect(id);
        count = 1;
        return count;
    }

    @Override
    public int deleteCollectAndContent(Long id) {
        int count;
        //删除收藏夹内容
        collectDao.deleteCollectAndContentByCollectId(id);
        //删除收藏夹
        collectDao.deleteCollect(id);
        count = 1;
        return count;
    }

    @Override
    public int batchMoveContent(List<Long> collectMusicIds, Long id) {
        int count;
        //批量移动收藏夹内容
        collectDao.updateBatchMoveContent(collectMusicIds,id);
        count = 1;
        return count;
    }
}
