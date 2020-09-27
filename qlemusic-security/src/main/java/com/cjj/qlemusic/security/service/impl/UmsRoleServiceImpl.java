package com.cjj.qlemusic.security.service.impl;

import com.cjj.qlemusic.security.dao.UmsRoleDao;
import com.cjj.qlemusic.security.entity.UmsMenu;
import com.cjj.qlemusic.security.entity.UmsRole;
import com.cjj.qlemusic.security.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * 后台管理角色Service实现类
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {
    @Autowired
    private UmsRoleDao umsRoleDao;

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return umsRoleDao.selectMenuList(adminId);
    }

    @Override
    public Set<String> getRoleByAdminId(Long adminId) {
        return umsRoleDao.selectRoleByAdminId(adminId);
    }
}
