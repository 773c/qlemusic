package com.cjj.qlemusic.security.custom;

import com.cjj.qlemusic.security.entity.UmsAdmin;
import com.cjj.qlemusic.security.service.UmsAdminService;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import com.cjj.qlemusic.security.util.SimpleByteSourceUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 用于jwtToken的Realm
 */
public class JwtUserRealm extends AuthorizingRealm {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    /*
     * 标识这个Realm是专门用来验证JwtToken
     * 不负责验证其他的token（UsernamePasswordToken）
     * */
    @Override
    public boolean supports(AuthenticationToken token) {
        //这个token就是从过滤器中传入的jwtToken
        return token instanceof JwtToken;
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ JwtUserRealm（授权中）");
        return new SimpleAuthorizationInfo();
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ JwtUserRealm（认证中）");
        String token = (String) authenticationToken.getPrincipal();
        //验证token
        if(!jwtTokenUtil.validateToken(token)){
            throw new UnknownAccountException();
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
                token,
                token,
                getName()
        );
        return authenticationInfo;
    }
}
