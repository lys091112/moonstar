package com.github.springboot.support.cache;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.stereotype.Component;


// TODO  添加缓存自动刷新的哨兵功能
@Component
@Aspect
public class SyncRefreshCache {

    @Autowired
    private CacheManager cacheManager;

    public void test(String key) {
        RedisOperations redisOperations = ((SyncRedisCacheManager) cacheManager).getRedisOperations();
        long tt = redisOperations.getExpire(key);
    }

}
