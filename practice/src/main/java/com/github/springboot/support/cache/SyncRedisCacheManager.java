package com.github.springboot.support.cache;

import org.springframework.cache.Cache;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.core.RedisOperations;

public class SyncRedisCacheManager extends RedisCacheManager {

    public SyncRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration configuration) {
        super(cacheWriter, configuration);
    }

    @Override
    public Cache getCache(String name) {
        return new SyncRedisCache().setRedisCache((RedisCache) super.getCache(name));
    }

}
