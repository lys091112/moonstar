package com.github.springboot.config;

import com.xianyue.retrofit.autoconfig.RetrofitAutoConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.HashMap;
import java.util.Map;

import static java.time.Duration.ofMinutes;

/**
 * {@code EnableCacheing} 开启注解形式的redis缓存使用
 * AbstractCacheResolver.resolveCaches 一旦注册一个cacheName,那么必须把所有的cachename都进行注册，不然会产生异常
 */
@EnableCaching
@AutoConfigureAfter({CacheAutoConfiguration.class, RetrofitAutoConfiguration.class})
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class RedisPropertiesConfig {

    private static final int DEFAULT_TIME_OUT_MINS = 5; //默认的超时时间

    private final CacheProperties cacheProperties;

    @Autowired
    public RedisPropertiesConfig(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @ConditionalOnClass(CacheManager.class)
    public CacheManager cacheManager(@Qualifier("redisTemplate") RedisTemplate<Object, Object> redisTemplate) {
        RedisCacheManager cacheManager = new RedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        cacheManager.setDefaultExpiration(ofMinutes(DEFAULT_TIME_OUT_MINS).getSeconds());
        if (null != cacheProperties) {
            cacheManager.setCacheNames(cacheProperties.getCacheNames());
            Map<String, Long> expires = new HashMap<>();
            expires.put("foo", 30L);
            cacheManager.setExpires(expires);
        }
        return cacheManager;
    }

}
