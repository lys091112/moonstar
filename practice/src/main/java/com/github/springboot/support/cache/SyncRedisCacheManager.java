package com.github.springboot.support.cache;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisOperations;

public class SyncRedisCacheManager extends RedisCacheManager {

    public SyncRedisCacheManager(RedisOperations redisOperations) {
        super(redisOperations);
    }

    @Override
    public Cache getCache(String name) {
        return new SyncRedisCache().setRedisCache((RedisCache) super.getCache(name));
    }


    @Override
    public RedisOperations getRedisOperations() {
        return super.getRedisOperations();
    }

}
