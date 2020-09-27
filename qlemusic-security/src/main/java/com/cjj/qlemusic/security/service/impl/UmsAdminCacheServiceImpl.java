package com.cjj.qlemusic.security.service.impl;

import com.cjj.qlemusic.security.entity.UmsAdmin;
import com.cjj.qlemusic.security.service.RedisService;
import com.cjj.qlemusic.security.service.UmsAdminCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * UmsAdminCacheService实现类
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {
    @Value("${redis.database}")
    private String database;
    @Value("${redis.key.admin}")
    private String admin;
    @Value("${redis.key.resourceList}")
    private String resourceList;
    @Value("${redis.expire.common}")
    private long expiration;

    @Autowired
    private RedisService redisService;

    @Override
    public void setAdmin(UmsAdmin umsAdmin) {
        String key = database + admin + umsAdmin.getAccount();
        redisService.set(key,umsAdmin,expiration);
    }

    @Override
    public UmsAdmin getAdmin(String account) {
        String key = database + admin + account;
        return (UmsAdmin) redisService.get(key);
    }

    @Override
    public void delAdmin(String account) {
        String key = database + admin + account;
        redisService.del(key);
    }
}
