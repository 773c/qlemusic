//package com.cjj.qlemusic.security.config;
//
//import com.cjj.qlemusic.security.custom.*;
//import com.cjj.qlemusic.security.util.JwtTokenUtil;
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.cache.CacheManager;
//import org.apache.shiro.cache.ehcache.EhCacheManager;
//import org.apache.shiro.session.mgt.SessionManager;
//import org.apache.shiro.session.mgt.eis.SessionDAO;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.crazycake.shiro.RedisCacheManager;
//import org.crazycake.shiro.RedisManager;
//import org.crazycake.shiro.RedisSessionDAO;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//
//@Configuration
//public class ShiroConfig {
//    @Value("${shiro.loginUrl}")
//    private String loginUrl;
//    @Value("${shiro.credentialsMatcher.algorithm}")
//    private String algorithm;
//    @Value("${shiro.credentialsMatcher.iteration}")
//    private Integer iteration;
//    @Value("${shiro.credentialsMatcher.hexEncoded}")
//    private boolean hexEncoded;
//    @Value("${shiro.realm.cachingEnabled}")
//    private boolean cachingEnabled;
//    @Value("${shiro.realm.authenticationCachingEnabled}")
//    private boolean authenticationCachingEnabled;
//    @Value("${shiro.realm.authorizationCachingEnabled}")
//    private boolean authorizationCachingEnabled;
//    @Value("${spring.redis.host}")
//    private String host;
//    @Value("${spring.redis.port}")
//    private Integer port;
//    //    @Value("${spring.redis.password}")
////    private String password;
//    @Value("${spring.redis.timeout}")
//    private Integer timeout;
//
//    /**
//     * 创建shiroFilterFactoryBean过滤器
//     */
//    @Bean
//    public ShiroFilterFactoryBean shiroFilter() {
//        System.out.println("❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥❥ shiroFilter过滤器执行了");
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        //注册securityManager
//        shiroFilterFactoryBean.setSecurityManager(securityManager());
//        //未认证会跳转到该地址进行登录
//        shiroFilterFactoryBean.setLoginUrl(loginUrl);
//        //自定义认证权限
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(getChainDefinitionMap());
//        return shiroFilterFactoryBean;
//    }
//
//    /**
//     * 获取动态权限
//     *
//     * @return
//     */
//    @Bean
//    public Map<String, String> getChainDefinitionMap() {
//        //新建一个LinkedHashMap来存储数据
//        Map<String, String> chainDefinitionMap = new LinkedHashMap<>();
//        //遍历lists集合
//        for (Map<String, String> map : getChainDefinitionList().getLists()) {
//            chainDefinitionMap.put(map.get("url"), map.get("permission"));
//        }
//        return chainDefinitionMap;
//    }
//
//    /**
//     * 获取自定义ChainDefinitionList
//     *
//     * @return
//     */
//    @Bean
//    public ChainDefinitionList getChainDefinitionList() {
//        return new ChainDefinitionList();
//    }
//
//    /**
//     * 创建DefaultWebSecurityManager安全管理器
//     */
//    @Bean
//    public DefaultWebSecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        //注入Realm
//        securityManager.setRealm(realm());
//        //注入缓存管理器并且realm开启缓存，根据设置的过期时间不用反复的访问数据库
//        securityManager.setCacheManager(cacheManager());
//        //注入session管理器
//        securityManager.setSessionManager(sessionManager());
//        return securityManager;
//    }
//
//    /**
//     * 身份验证器
//     *
//     * @return
//     */
//    @Bean
//    public UserRealm realm() {
//        UserRealm userRealm = new UserRealm();
//        //注入凭证匹配器
//        userRealm.setCredentialsMatcher(credentialsMatcher());
//        //是否开启缓存
//        userRealm.setCachingEnabled(cachingEnabled);
//        //认证缓存
//        userRealm.setAuthenticationCachingEnabled(authenticationCachingEnabled);
//        userRealm.setAuthenticationCacheName("authenticationCache");
//        //授权缓存
//        userRealm.setAuthorizationCachingEnabled(authorizationCachingEnabled);
//        userRealm.setAuthorizationCacheName("authorizationCache");
//        return userRealm;
//    }
//
//
//    /**
//     * 密码验证凭证器
//     */
//    @Bean
//    public HashedCredentialsMatcher credentialsMatcher() {
//        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher();
//        //加密方式
//        credentialsMatcher.setHashAlgorithmName(algorithm);
//        //叠加次数
//        credentialsMatcher.setHashIterations(iteration);
//        //存储散列后的密码是否为16进制
//        credentialsMatcher.setStoredCredentialsHexEncoded(hexEncoded);
//        return credentialsMatcher;
//    }
//
//    /**
//     * redis连接管理器
//     */
//    @Bean
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        //设置主机端口号，新版本
//        redisManager.setHost(host + ":" + port);
//        //旧版本
//        //redisManager.setPort(PORT);
//        //设置密码,如果没有密码不用设置，不然会报错
//        //redisManager.setPassword("");
//        //设置连接超时时间
//        redisManager.setTimeout(timeout);
//        return redisManager;
//    }
//
//
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
//
////    /**
////     * ehcache认证授权缓存管理器（不用再去数据库查询数据做认证和授权）
////     * @return
////     */
////    @Bean
////    public CacheManager cacheManager(){
////        EhCacheManager ehCacheManager = new EhCacheManager();
////        //缓存的配置
////        ehCacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
////        return ehCacheManager;
////    }
//
//    /**
//     * Session管理器
//     *
//     * @return
//     */
//    @Bean
//    public SessionManager sessionManager() {
//        MySessionManager mySessionManager = new MySessionManager();
//        //注入sessionDAO
//        mySessionManager.setSessionDAO(sessionDAO());
//        return mySessionManager;
//    }
//
//    /**
//     * redisSessionDAO
//     */
//    @Bean
//    public SessionDAO sessionDAO() {
//        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
//        //注入redis管理器
//        redisSessionDAO.setRedisManager(redisManager());
//        return redisSessionDAO;
//    }
//
//    /**
//     * 开启注解支持
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor() {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        //注入安全管理器
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
//        return authorizationAttributeSourceAdvisor;
//    }
//
//    @Bean
//    public JwtTokenUtil jwtTokenUtil() {
//        return new JwtTokenUtil();
//    }
//
//}
