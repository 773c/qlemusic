package com.cjj.qlemusic.security.dao;

import com.cjj.qlemusic.security.entity.UmsAdmin;
import com.cjj.qlemusic.security.entity.UmsAdminLogin;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;
import java.util.Set;

/**
 * 后台管理员用户管理Dao
 */
public interface UmsAdminDao {
    void addTest(UmsAdmin umsAdmin);
    /**
     * 查询所有
     * @return
     */
    List<UmsAdmin> selectAll();

    /**
     * 根据账号查询管理员用户
     * @param account
     * @return
     */
    UmsAdmin selectAdminByAccount(String account);

    /**
     * 验证账号密码是否正确
     * @param umsAdminLogin
     * @return
     */
    UmsAdminLogin selectAccountAndPassword(UmsAdminLogin umsAdminLogin);

    /**
     * 根据账号查询相应角色
     * @param account
     * @return
     */
    Set<String> selectRoles(String account);

    /**
     * 根据账号查询相应权限
     * @param account
     * @return
     */
    Set<String> selectPermissions(String account);


}
