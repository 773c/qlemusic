package com.cjj.qlemusic.security.service;



/**
 * redis操作Service
 */
public interface RedisService {
    /**
     * 存入数据
     * @param key
     * @param value
     */
    void set(String key,Object value);

    /**
     * 存入有时效的数据
     * @param key
     * @param value
     * @param expiration
     */
    void set(String key,Object value,long expiration);

    /**
     * 根据key获取数据
     * @param key
     * @return
     */
    Object get(String key);

    /**
     * 根据key删除数据
     * @param key
     */
    void del(String key);

    /**
     * 设置过期时间
     * @param key
     * @param expiration
     */
    void expiration(String key,long expiration);

}
