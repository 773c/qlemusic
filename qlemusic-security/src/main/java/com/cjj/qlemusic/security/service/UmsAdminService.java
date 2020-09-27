package com.cjj.qlemusic.security.service;


import com.cjj.qlemusic.security.entity.UmsAdmin;
import com.cjj.qlemusic.security.entity.UmsAdminLogin;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

/**
 * 管理员用户操作类
 */
public interface UmsAdminService {
    @Transactional
    void addTest(UmsAdmin umsAdmin);

    /**
     * 登录
     * @param umsAdminLogin
     * @return
     */
    String login(UmsAdminLogin umsAdminLogin);

    /**
     * 根据账号查询管理员用户
     * @param account
     * @return
     */
    UmsAdmin getAdminByAccount(String account);

    /**
     * 根据账号获取相应角色
     * @param account
     * @return
     */
    Set<String> getRoles(String account);

    /**
     * 根据账号获取相应权限
     * @param account
     * @return
     */
    Set<String> getPermissions(String account);
}
