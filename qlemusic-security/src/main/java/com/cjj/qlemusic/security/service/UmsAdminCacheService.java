package com.cjj.qlemusic.security.service;

import com.cjj.qlemusic.security.entity.UmsAdmin;

/**
 * 管理员用户缓存操作类
 */
public interface UmsAdminCacheService {
    /**
     * 缓存管理员用户
     * @param admin
     */
    void setAdmin(UmsAdmin admin);

    /**
     * 根据账号获取缓存
     * @param account
     * @return
     */
    UmsAdmin getAdmin(String account);

    /**
     * 根据账号删除缓存
     * @param account
     */
    void delAdmin(String account);
}
