package com.cjj.qlemusic.security.config;

import com.cjj.qlemusic.security.custom.ChainDefinitionList;
import com.cjj.qlemusic.security.custom.JwtUserRealm;
import com.cjj.qlemusic.security.custom.UserRealm;
import com.cjj.qlemusic.security.filter.JwtAuthenticationTokenFilter;
import com.cjj.qlemusic.security.util.JwtTokenUtil;
import org.apache.shiro.authc.Authenticator;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.*;

/**
 * JwtShiroConfig配置类
 */
@Configuration
public class JwtShiroConfig {
    @Value("${shiro.loginUrl}")
    private String loginUrl;
    @Value("${shiro.unauthorizedUrl}")
    private String unauthorizedUrl;
    @Value("${shiro.filterKey.url}")
    private String url;
    @Value("${shiro.filterKey.permission}")
    private String permission;
    @Value("${shiro.credentialsMatcher.algorithm}")
    private String algorithm;
    @Value("${shiro.credentialsMatcher.iteration}")
    private Integer iteration;
    @Value("${shiro.credentialsMatcher.hexEncoded}")
    private boolean hexEncoded;
    @Value("${shiro.realm.cachingEnabled}")
    private boolean cachingEnabled;
    @Value("${shiro.realm.authenticationCachingEnabled}")
    private boolean authenticationCachingEnabled;
    @Value("${shiro.realm.authorizationCachingEnabled}")
    private boolean authorizationCachingEnabled;
    @Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private Integer port;
//    @Value("${spring.redis.password}")
//    private String password;
    @Value("${spring.redis.timeout}")
    private Integer TIMEOUT;

    /**
     * shiroFilter过滤器
     * @return
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilter() {
        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ JwtShiroFilter过滤器执行了");
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        //注册securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager());
        //未认证跳转api
        shiroFilterFactoryBean.setLoginUrl(loginUrl);
        //未授权跳转的api
        shiroFilterFactoryBean.setUnauthorizedUrl(unauthorizedUrl);
        //设置过滤器
        Map<String, Filter> filterMap = new HashMap<>();
        filterMap.put("jwtFilter", new JwtAuthenticationTokenFilter());
        shiroFilterFactoryBean.setFilters(filterMap);
        //设置认证权限
        shiroFilterFactoryBean.setFilterChainDefinitionMap(getChainDefinitionMap());
        return shiroFilterFactoryBean;
    }

    /**
     * securityManager安全管理器
     * @return
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        //注入realm,按顺序来读取
        securityManager.setRealms(Arrays.asList(userRealm(),jwtUserRealm()));
        //注入缓存
//        securityManager.setCacheManager(cacheManager());
        //注入subjectDAO，禁用session存储功能
        securityManager.setSubjectDAO(subjectDAO());
        return securityManager;
    }

    /**
     * 获取动态权限
     * @return
     */
    @Bean
    public Map<String, String> getChainDefinitionMap() {
        //新建一个LinkedHashMap来存储数据
        Map<String, String> chainDefinitionMap = new LinkedHashMap<>();
        //遍历lists集合
        for (Map<String, String> map : getChainDefinitionList().getLists()) {
            chainDefinitionMap.put(map.get(url), map.get(permission));
        }
        System.out.println(chainDefinitionMap);
        return chainDefinitionMap;
    }

//    /**
//     * redis连接管理器
//     */
//    @Bean
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        //设置主机端口号，新版本
//        redisManager.setHost(host+":"+port);
//        //旧版本
//        //redisManager.setPort(PORT);
//        //设置密码,如果没有密码不用设置，不然会报错
//        //redisManager.setPassword("");
//        //设置连接超时时间
//        redisManager.setTimeout(TIMEOUT);
//        return redisManager;
//    }


//    /**
//     * Redis集群使用RedisClusterManager，单个Redis使用RedisManager
//     * redis认证授权缓存管理器（不用再去数据库查询数据做认证和授权）
//     */
//    @Bean
//    public CacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        //注入redis管理器
//        redisCacheManager.setRedisManager(redisManager());
//        //设置缓存过期时间
//        redisCacheManager.setExpire(60);
//        return redisCacheManager;
//    }

    /**
     * 获取自定义ChainDefinitionList
     * @return
     */
    @Bean
    public ChainDefinitionList getChainDefinitionList() {
        return new ChainDefinitionList();
    }

    /**
     * 用于登录验证的身份验证器
     */
    @Bean
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        //注入凭证匹配器
        userRealm.setCredentialsMatcher(credentialsMatcher());
        //是否开启缓存
        userRealm.setCachingEnabled(cachingEnabled);
        //认证缓存
        userRealm.setAuthenticationCachingEnabled(authenticationCachingEnabled);
        userRealm.setAuthenticationCacheName("authenticationCache");
        //授权缓存
        userRealm.setAuthorizationCachingEnabled(authorizationCachingEnabled);
        userRealm.setAuthorizationCacheName("authorizationCache");
        return userRealm;
    }

    /**
     * 密码验证凭证器
     */
    @Bean
    public HashedCredentialsMatcher credentialsMatcher() {
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
        //加密方式
        credentialsMatcher.setHashAlgorithmName(algorithm);
        //叠加次数
        credentialsMatcher.setHashIterations(iteration);
        //存储散列后的密码是否为16进制
        credentialsMatcher.setStoredCredentialsHexEncoded(hexEncoded);
        return credentialsMatcher;
    }

    /**
     * JWT身份验证器
     */
    @Bean
    public JwtUserRealm jwtUserRealm() {
//        JwtUserRealm jwtUserRealm = new JwtUserRealm();
//        //注入凭证匹配器
//        jwtUserRealm.setCredentialsMatcher(credentialsMatcher());
        return new JwtUserRealm();
    }

    /**
     * 开启注解支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        //注入安全管理器
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }

    /**
     * 注入JwtTokenUtil
     */
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }

    /**
     * 关闭ShiroDAO功能
     */
    public DefaultSubjectDAO subjectDAO(){
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        DefaultSessionStorageEvaluator defaultSessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        //关闭session存储功能（包括HttpSession中）
        defaultSessionStorageEvaluator.setSessionStorageEnabled(false);
        subjectDAO.setSessionStorageEvaluator(defaultSessionStorageEvaluator);
        return subjectDAO;
    }

}
