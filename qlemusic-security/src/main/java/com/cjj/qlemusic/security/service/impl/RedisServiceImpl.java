package com.cjj.qlemusic.security.service.impl;

import com.cjj.qlemusic.security.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * redis操作实现类
 */
@Service
public class RedisServiceImpl implements RedisService{
    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void set(String key, Object value, long expiration) {
        redisTemplate.opsForValue().set(key, value, expiration, TimeUnit.SECONDS);
    }

    @Override
    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    @Override
    public void del(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public void expiration(String key, long expiration) {
        redisTemplate.expire(key,expiration,TimeUnit.SECONDS);
    }

    @Override
    public void addSet(String key,Object value) {
        redisTemplate.opsForSet().add(key,value);
    }

    @Override
    public Long getLengthSet(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    @Override
    public void hset(String keys, Object key, Object value) {
        redisTemplate.opsForHash().put(keys,key,value);
    }

    @Override
    public Object hget(String keys, Object key) {
        return redisTemplate.opsForHash().get(keys,key);
    }

    @Override
    public void hdel(String keys, Object key) {
        redisTemplate.opsForHash().delete(keys,key);
    }

    @Override
    public void hincrement(String key,Object id,long count) {
        redisTemplate.opsForHash().increment(key,id,count);
    }

    @Override
    public void hdecrement(String key, Object id, long count) {
        redisTemplate.opsForHash().increment(key,id,count);
    }

    @Override
    public Cursor<Map.Entry<Object, Object>> hscan(String key,ScanOptions scanOptions) {
        return redisTemplate.opsForHash().scan(key, scanOptions);
    }

    @Override
    public void lpush(String key, Object value) {
        redisTemplate.opsForList().leftPush(key,value);
    }

    @Override
    public List<Object> lrange(String key, long start, long end) {
        return redisTemplate.opsForList().range(key,start,end);
    }

    @Override
    public void lpop(String key) {
        redisTemplate.opsForList().leftPop(key);
    }

    @Override
    public Long llength(String key) {
        return redisTemplate.opsForList().size(key);
    }

    @Override
    public void addZset(String key, Object value, double score) {
        redisTemplate.opsForZSet().add(key,value,score);
    }

    @Override
    public Object getZsetAll(String key,long start,long end) {
        return redisTemplate.opsForZSet().reverseRange(key,start,end);
    }

    @Override
    public void delZset(String key,long start,long end) {
        redisTemplate.opsForZSet().removeRange(key,start,end);
    }


}
