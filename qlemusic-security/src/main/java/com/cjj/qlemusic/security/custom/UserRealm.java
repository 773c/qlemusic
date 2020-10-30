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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用于用户登录时的校验
 */
public class UserRealm extends AuthorizingRealm {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtTokenUtil.class);

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
        LOGGER.info("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ UserRealm（授权中）");
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
        LOGGER.info("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ UserRealm（认证中）");
        String identity = (String) authenticationToken.getPrincipal();
        UmsUser umsUser = null;
        UmsAdmin umsAdmin = null;
        SimpleAuthenticationInfo authenticationInfo = null;
        //判断是用户登录还是管理员
        if(!identity.contains("admin")) {
            //用户
            umsUser = umsUserService.getUserByIdentity(identity);
            System.out.println(umsUser);
            authenticationInfo = getAuthenticationInfo(umsUser.getIdentity(),umsUser.getCredential(),umsUser.getSalt());
        } else{
            //管理员
            umsAdmin = umsAdminService.getAdminByAccount(identity);
            authenticationInfo = getAuthenticationInfo(umsAdmin.getAccount(),umsAdmin.getPassword(),umsAdmin.getSalt());
        }
        //判断是否存在
        if(umsUser == null && umsAdmin == null){
            throw new UnknownAccountException("账号未注册");
        }
        return authenticationInfo;
    }

    /**
     * 获取认证信息
     * @param identity
     * @param credential
     * @param salt
     * @return
     */
    public SimpleAuthenticationInfo getAuthenticationInfo(String identity,String credential,String salt){
        return new SimpleAuthenticationInfo(
                identity,
                credential,
                new SimpleByteSourceUtil(salt.getBytes()),
                getName()
        );
    }
}
