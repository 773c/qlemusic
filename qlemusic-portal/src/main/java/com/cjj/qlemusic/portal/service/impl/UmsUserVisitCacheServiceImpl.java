package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.portal.service.UmsUserVisitCacheService;
import com.cjj.qlemusic.portal.entity.UmsUserOperation;
import com.cjj.qlemusic.security.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户访问量缓存Service实现类
 */
@Service
public class UmsUserVisitCacheServiceImpl implements UmsUserVisitCacheService {
    @Value("${redis.database}")
    private String database;
    @Value("${redis.key.userVisitCount}")
    private String userVisitCountKey;

    @Autowired
    private RedisService redisService;

    @Override
    public void incrementVisitedCount(Long userId) {
        redisService.hincrement(database+userVisitCountKey,userId.toString(),1);
    }

    @Override
    public void setVisitedCount(Long userId,Integer visitCount) {
        redisService.hset(database+userVisitCountKey,userId.toString(),visitCount);
    }

    @Override
    public Integer getVisitedCount(Long userId) {
        return (Integer) redisService.hget(database+userVisitCountKey,userId.toString());
    }

    @Override
    public List<UmsUserOperation> getVisitedCountList() throws IOException {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.hscan(database+userVisitCountKey, ScanOptions.NONE);
        List<UmsUserOperation> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            Integer visitCount = (Integer) entry.getValue();

            //封装到BbsUserLike实体类
            UmsUserOperation umsUserOperation = new UmsUserOperation();
            umsUserOperation.setUserId(Long.parseLong(key));
            umsUserOperation.setVisitCount(visitCount);
            list.add(umsUserOperation);
        }
        cursor.close();
        return list;
    }

    @Override
    public void delVisitedCount(Long userId) {
        redisService.hdel(database+userVisitCountKey,userId.toString());
    }
}
