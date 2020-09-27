package com.cjj.qlemusic.security.dao;

import com.cjj.qlemusic.security.entity.UmsMenu;
import com.cjj.qlemusic.security.entity.UmsRole;

import java.util.List;
import java.util.Set;

public interface UmsRoleDao {

    /**
     * 根据管理员ID获取菜单
     * @param adminId
     * @return
     */
    List<UmsMenu> selectMenuList(Long adminId);

    /**
     * 根据管理员ID获取角色
     * @param adminId
     * @return
     */
    Set<String> selectRoleByAdminId(Long adminId);
}
