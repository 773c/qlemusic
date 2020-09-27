package com.cjj.qlemusic.security.filter;

import cn.hutool.http.HttpStatus;
import com.cjj.qlemusic.security.custom.JwtToken;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import com.cjj.qlemusic.security.util.SpringContextUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JwtToken登录授权过滤器
 * isAccessAllowed()用于token的认证，返回false则执行onAccessDenied()（token是否有效）
 * onAccessDenied()用于token的认证失败后进行重新登录再进行访问，登录成功即可访问，反之（token为null或过期时）
 */
public class JwtAuthenticationTokenFilter extends AuthenticatingFilter {
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    /**
     * 使用我们自己定义的Token类，提交给shiro。
     * 这个方法返回null的话会直接抛出异常，进入isAccessAllowed()的异常处理逻辑。
     */
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ JwtAuthenticationTokenFilter（createToken() 创建JwtToken）");
        //获取jwtTokenUtil
        if (jwtTokenUtil == null) {
            jwtTokenUtil = (JwtTokenUtil) SpringContextUtil.getBean("jwtTokenUtil");
        }
        //获取约定的tokenHeader
        String tokenHeader = SpringContextUtil.getApplicationContext()
                .getBean(Environment.class).getProperty("jwt.tokenHeader");
        //获取负载头
        String tokenHead = SpringContextUtil.getApplicationContext()
                .getBean(Environment.class).getProperty("jwt.tokenHead");
                HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {
            //获取前端Header传来的token信息
            String token = request.getHeader(tokenHeader).substring(tokenHead.length()+1);
            LOGGER.info("请求的Header中token: {}", token);
            //判断token不为空和token没有过期则执行
            if (!StringUtils.isEmpty(token) && !jwtTokenUtil.isTokenExpired(token))
                return new JwtToken(token);
        } catch (NullPointerException e) {
            LOGGER.error("token为null");
        }
        return null;
    }

    /**
     * 1. 返回true，shiro就直接允许访问url
     * 2. 返回false，shiro才会根据onAccessDenied的方法的返回值决定是否允许访问url
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object o) {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ JwtAuthenticationTokenFilter（isAccessAllowed() token认证中）");
        boolean allowed = false;
        try {
            //调用父类的executeLogin，token不为null则进行subject.login()
            allowed = executeLogin(request, response);
            //createToken()相当于在这里面
        } catch (IllegalStateException e) {
            LOGGER.error("createToken 返回为null");
        } catch (Exception e) {
            LOGGER.error("Error occurs when login", e);
        }
        LOGGER.info("isAccessAllowed:{}", allowed);
        return allowed;
    }

    /**
     * 返回结果为true表明登录通过
     */
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ JwtAuthenticationTokenFilter（onAccessDenied() token认证失败，重新登录）");
        HttpServletResponse response = WebUtils.toHttp(servletResponse);
        //响应状态为403
        response.setStatus(HttpStatus.HTTP_FORBIDDEN);
        return false;
    }


    /**
     * 如果Shiro Login认证成功，会进入该方法，等同于用户名密码登录成功，我们这里还判断了是否要刷新Token
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        LOGGER.info("onLoginSuccess 认证成功");
        //可以做token的刷新

        //测试。。。。
        PrincipalCollection principalCollection = subject.getPrincipals();
        System.out.println("认证成功的Realm有:" + principalCollection.getRealmNames());
        return true;
    }

    /**
     * 如果调用shiro的subject.login()认证失败（抛异常），会回调这个方法，这里我们什么都不做，因为逻辑放到了onAccessDenied()中。
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        LOGGER.error("shiro认证失败, token:{}, error:{}", token.toString(), e.getMessage());
        return false;
    }
}
