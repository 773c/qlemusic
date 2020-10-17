package com.cjj.qlemusic.portal.service.impl;

import com.cjj.qlemusic.portal.entity.BbsMusicOperation;
import com.cjj.qlemusic.portal.service.BbsPlayCacheService;
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
 * 播放量缓存Service实现类
 */
@Service
public class BbsPlayCacheServiceImpl implements BbsPlayCacheService {
    @Value("${redis.database}")
    private String database;
    @Value("${redis.key.playedCount}")
    private String playedCountKey;

    @Autowired
    private RedisService redisService;


    @Override
    public void incrementPlayedCount(Long musicId) {
        redisService.hincrement(database+playedCountKey,musicId.toString(),1);
    }

    @Override
    public void setPlayedCount(Long musicId, Integer playCount) {
        redisService.hset(database+playedCountKey,musicId.toString(),playCount);
    }

    @Override
    public void delPlayedCount(Long musicId) {
        redisService.hdel(database+playedCountKey,musicId.toString());
    }

    @Override
    public List<BbsMusicOperation> getPlayedCountList() throws IOException {
        Cursor<Map.Entry<Object, Object>> cursor = redisService.hscan(database+playedCountKey, ScanOptions.NONE);
        List<BbsMusicOperation> list = new ArrayList<>();
        while (cursor.hasNext()){
            Map.Entry<Object,Object> entry = cursor.next();
            String key = (String) entry.getKey();
            Integer playCount = (Integer) entry.getValue();

            //封装到BbsMusicOperation实体类
            BbsMusicOperation bbsMusicOperation = new BbsMusicOperation();
            bbsMusicOperation.setPlayCount(playCount);
            bbsMusicOperation.setMusicId(Long.parseLong(key));
            list.add(bbsMusicOperation);
        }
        cursor.close();
        return list;
    }

}
