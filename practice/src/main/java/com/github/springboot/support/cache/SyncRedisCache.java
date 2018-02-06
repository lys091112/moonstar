package com.github.springboot.support.cache;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.cache.RedisCache;

@Slf4j
public class SyncRedisCache implements org.springframework.cache.Cache {

    private RedisCache redisCache;

    private static ConcurrentHashMap<String, Object> cacheNameLock = new ConcurrentHashMap<>();

    public SyncRedisCache setRedisCache(RedisCache redisCache) {
        this.redisCache = redisCache;
        return this;
    }

    @Override
    public String getName() {
        return redisCache.getName();
    }

    @Override
    public Object getNativeCache() {
        return redisCache.getNativeCache();
    }

    @Override
    public ValueWrapper get(Object key) {
        return redisCache.get(key);
    }

    @Override
    public <T> T get(Object key, Class<T> type) {
        return redisCache.get(key, type);
    }

    @Override
    public <T> T get(final Object key, final Callable<T> valueLoader) {

        ValueWrapper val = get(key);
        if (val != null) {
            return (T) val.get();
        }

        // 最大的弊端就是针对于同一个cacheName，都增加了一把锁，影响了效率
        synchronized (monitorObj()) {
            return redisCache.get(key, valueLoader);
        }
    }


    //获取某个cacheName的监控对象
    private Object monitorObj() {
        String cacheName = getName();
        if (!cacheNameLock.containsKey(cacheName)) {
            Object obj = new Object();
            cacheNameLock.putIfAbsent(cacheName, obj); // 原子性保证只会初始化一次
        }
        return cacheNameLock.get(cacheName);
    }

    @Override
    public void put(Object key, Object value) {
        redisCache.put(key, value);
    }

    @Override
    public ValueWrapper putIfAbsent(Object key, Object value) {
        return redisCache.putIfAbsent(key, value);
    }

    @Override
    public void evict(Object key) {
        redisCache.evict(key);
    }

    @Override
    public void clear() {
        redisCache.clear();
    }
}
