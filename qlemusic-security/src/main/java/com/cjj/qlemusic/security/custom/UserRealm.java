package com.cjj.qlemusic.security.custom;

import com.cjj.qlemusic.security.entity.UmsAdmin;
import com.cjj.qlemusic.security.entity.UmsUser;
import com.cjj.qlemusic.security.service.UmsAdminService;
import com.cjj.qlemusic.security.service.UmsUserService;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import com.cjj.qlemusic.security.util.SimpleByteSourceUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用于用户登录时的校验
 */
public class UserRealm extends AuthorizingRealm {
    @Autowired
    private UmsAdminService umsAdminService;
    @Autowired
    private UmsUserService umsUserService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ UserRealm（授权中）");
        //获取身份
        String token = (String) principalCollection.getPrimaryPrincipal();
        //获取账号
        String account = jwtTokenUtil.getAccountFromToken(token);
        //校验授权
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        System.out.println(umsAdminService.getRoles(account));
        System.out.println(umsAdminService.getPermissions(account));
        authorizationInfo.setRoles(umsAdminService.getRoles(account));
        authorizationInfo.setStringPermissions(umsAdminService.getPermissions(account));
        return authorizationInfo;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ UserRealm（认证中）");
        String account = (String) authenticationToken.getPrincipal();
        System.out.println(account);
        UmsUser umsUser = null;
        UmsAdmin umsAdmin = null;
        SimpleAuthenticationInfo authenticationInfo = null;
        if(account.length() == 11) {
            umsUser = umsUserService.getUserByTelephone(account);
            authenticationInfo = new SimpleAuthenticationInfo(
                    umsUser.getTelephone(),
                    umsUser.getPassword(),
                    new SimpleByteSourceUtil(umsUser.getSalt().getBytes()),
                    getName()
            );
            return authenticationInfo;
        }
        else
            //从数据库获取管理员用户信息
            umsAdmin = umsAdminService.getAdminByAccount(account);
        //判断是否存在
        if(umsAdmin == null){
            System.out.println("账号未注册");
            throw new UnknownAccountException("账号未注册");
        }
        System.out.println("用户："+umsAdmin.getName()+"，密码："+umsAdmin.getPassword());
        authenticationInfo = new SimpleAuthenticationInfo(
                umsAdmin.getAccount(),
                umsAdmin.getPassword(),
                new SimpleByteSourceUtil(umsAdmin.getSalt().getBytes()),
                getName()
        );
        return authenticationInfo;
    }

}
