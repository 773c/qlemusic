package com.cjj.qlemusic.security.service;

import com.cjj.qlemusic.security.entity.UmsUser;

/**
 * 用户缓存Service
 */
public interface UmsUserCacheService {
    /**
     * 存储用户缓存
     * @param umsUser
     */
    void setUser(UmsUser umsUser);

    /**
     * 获取用户缓存
     * @param identity
     */
    UmsUser getUser(String identity);

    /**
     * 删除用户缓存
     * @param identity
     */
    void delUser(String identity);


    /**
     * 设置手机验证码
     * @param telephone
     * @param verify
     */
    void setVerify(String telephone,String verify);

    /**
     * 获取手机验证码
     * @param telephone
     */
    String getVerify(String telephone);

    /**
     * 删除手机验证码
     * @param telephone
     */
    void delVerify(String telephone);

}
