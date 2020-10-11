package com.cjj.qlemusic.security.service;


import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;

import java.util.Map;

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

    /**
     * set集合添加
     * @param key
     * @param value
     */
    void addSet(String key,Object value);

    /**
     * 获取set集合长度
     * @param key
     * @return
     */
    Long getLengthSet(String key);

    /**
     * hash存入数据
     * @param keys
     * @param key
     * @param value
     */
    void hset(String keys,Object key,Object value);

    /**
     * hash获取数据
     * @param keys
     * @param key
     * @return
     */
    Object hget(String keys,Object key);

    /**
     * hash删除数据
     * @param keys
     * @param key
     */
    void hdel(String keys,Object key);

    /**
     * 自增加1
     * @param key
     * @param likedId
     * @param count
     */
    void hincrement(String key,Object likedId,long count);

    /**
     * 自减1
     * @param key
     * @param likedId
     * @param count
     */
    void hdecrement(String key,Object likedId,long count);

    /**
     * 根据key获取所有数据
     * @param key
     * @param scanOptions
     * @return
     */
    Cursor<Map.Entry<Object, Object>> hscan(String key, ScanOptions scanOptions);

}
