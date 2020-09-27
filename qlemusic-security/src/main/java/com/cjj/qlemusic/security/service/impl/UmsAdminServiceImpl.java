package com.cjj.qlemusic.security.service.impl;

import com.cjj.qlemusic.security.dao.UmsAdminDao;
import com.cjj.qlemusic.security.entity.UmsAdmin;
import com.cjj.qlemusic.security.entity.UmsAdminLogin;
import com.cjj.qlemusic.security.service.UmsAdminCacheService;
import com.cjj.qlemusic.security.service.UmsAdminService;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;


/**
 * UmsAdminService实现类
 */
@Service
public class UmsAdminServiceImpl implements UmsAdminService {
    @Autowired
    private UmsAdminDao umsAdminDao;
    @Autowired
    private UmsAdminCacheService umsAdminCacheService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public void addTest(UmsAdmin umsAdmin) {
        System.out.println("添加中。。。");
        umsAdminDao.addTest(umsAdmin);
        int i=1/0;
    }

    @Override
    public String login(UmsAdminLogin umsAdminLogin) {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken authenticationToken = new UsernamePasswordToken(umsAdminLogin.getAccount(), umsAdminLogin.getPassword());
        //会到自定义的realm中认证用户
        subject.login(authenticationToken);
        //Shiro认证通过后会将user信息放到subject内，生成token并返回
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtTokenUtil.CLAIM_ACCOUNT, subject.getPrincipal());
        String token = jwtTokenUtil.generateToken(claims);
        return token;
    }

    @Override
    public UmsAdmin getAdminByAccount(String account) {
        UmsAdmin umsAdmin = umsAdminCacheService.getAdmin(account);
        if(umsAdmin!=null) return umsAdmin;
        System.out.println("redis缓存中不存在，正在访问数据库根据账号查询是否存在管理员。。。。");
        umsAdmin = umsAdminDao.selectAdminByAccount(account);
        umsAdminCacheService.setAdmin(umsAdmin);
        return umsAdmin;
    }

    @Override
    public Set<String> getRoles(String account) {
        return umsAdminDao.selectRoles(account);
    }

    @Override
    public Set<String> getPermissions(String account) {
        return umsAdminDao.selectPermissions(account);
    }
}
