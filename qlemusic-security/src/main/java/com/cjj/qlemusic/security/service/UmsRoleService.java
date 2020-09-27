package com.cjj.qlemusic.security.service;

import com.cjj.qlemusic.security.entity.UmsMenu;
import com.cjj.qlemusic.security.entity.UmsRole;

import java.util.List;
import java.util.Set;

/**
 * 后台管理角色Service
 */
public interface UmsRoleService {
    /**
     * 根据管理员ID获取菜单
     * @param adminId
     */
     List<UmsMenu> getMenuList(Long adminId);

    /**
     * 根据管理员ID获取角色
     * @param adminId
     * @return
     */
     Set<String> getRoleByAdminId(Long adminId);
}
