package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.portal.dao.UmsUserVisitDao;
import com.cjj.qlemusic.portal.service.UmsUserVisitCacheService;
import com.cjj.qlemusic.portal.service.UmsUserVisitService;
import com.cjj.qlemusic.portal.entity.UmsUserOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

/**
 * 用户访问量Service实现类
 */
@Service
public class UmsUserVisitServiceImpl implements UmsUserVisitService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UmsUserVisitServiceImpl.class);

    @Autowired
    private UmsUserVisitDao umsUserVisitDao;
    @Autowired
    private UmsUserVisitCacheService umsUserVisitCacheService;

    @Override
    public int userVisit(Long id) {
        int count;
        //将数据在缓存中自增
        umsUserVisitCacheService.incrementVisitedCount(id);
        count = 1;
        return count;
    }

    @Override
    public Integer getVisitCount(Long id) {
        Integer vistCount = umsUserVisitCacheService.getVisitedCount(id);
        if(vistCount == null){
            //缓存为空，从数据库拿
            UmsUserOperation umsUserOperation = umsUserVisitDao.selectUserVisitedCountById(id);
            System.out.println(umsUserOperation);
            if(umsUserOperation != null)
                vistCount = umsUserOperation.getVisitCount();
            else
                vistCount = 0;
            //存入缓存
            umsUserVisitCacheService.setVisitedCount(id,vistCount);
        }
        return vistCount;
    }

    @Override
    public void visitedCountToDatabaseTimer() throws IOException {
        List<UmsUserOperation> visitedCountList = umsUserVisitCacheService.getVisitedCountList();
        if(visitedCountList == null)
            LOGGER.error("访问数量此时为空");
        for (UmsUserOperation visitedOperation:visitedCountList ){
            if(visitedOperation.getUserId() != null){
                UmsUserOperation userOperationDao = umsUserVisitDao.selectUserVisitedCountById(visitedOperation.getUserId());
                if(userOperationDao == null){
                    //不存在，直接存入
                    umsUserVisitDao.insertVisitedCount(visitedOperation);
                } else {
                    //已存在，更新数量
                    Integer visitCount = userOperationDao.getVisitCount();
                    umsUserVisitDao.updateVisitedCount(visitedOperation);
                }
            }
            //从 Redis 中删除
            umsUserVisitCacheService.delVisitedCount(visitedOperation.getUserId());
        }
    }
}
