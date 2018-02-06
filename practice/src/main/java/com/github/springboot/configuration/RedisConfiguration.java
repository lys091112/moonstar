package com.github.springboot.configuration;

import static java.time.Duration.ofMinutes;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.springboot.support.cache.AppCacheConfigurer;
import com.github.springboot.support.cache.SyncRedisCacheManager;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.cache.CacheAutoConfiguration;
import org.springframework.boot.autoconfigure.cache.CacheProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;

/**
 * {@code EnableCacheing} 开启注解形式的redis缓存使用
 * AbstractCacheResolver.resolveCaches 一旦注册一个cacheName,那么必须把所有的cachename都进行注册，不然会产生异常
 */
@EnableCaching
@AutoConfigureAfter({CacheAutoConfiguration.class})
@Configuration
@EnableConfigurationProperties(CacheProperties.class)
public class RedisConfiguration {

    private static final int DEFAULT_TIME_OUT_MINS = 50; //默认的超时时间

    private final CacheProperties cacheProperties;

    @Autowired
    public RedisConfiguration(CacheProperties cacheProperties) {
        this.cacheProperties = cacheProperties;
    }

    @Bean
    @ConditionalOnClass(SyncRedisCacheManager.class)
    public CacheManager cacheManager(@Qualifier("ownStringRedisTemplate") RedisTemplate<String, String> redisTemplate) {
        SyncRedisCacheManager cacheManager = new SyncRedisCacheManager(redisTemplate);
        cacheManager.setUsePrefix(true);
        cacheManager.setDefaultExpiration(ofMinutes(DEFAULT_TIME_OUT_MINS).getSeconds());
        if (null != cacheProperties) {
            cacheManager.setCacheNames(cacheProperties.getCacheNames());
            Map<String, Long> expires = new HashMap<>();
            expires.put("foo", 300L);
            expires.put("foo2", 300L);
            cacheManager.setExpires(expires);
        }
        return cacheManager;
    }

    //如果不制定名称，那么会使用方法名来做为初始化的标识
    @Bean(name = "ownStringRedisTemplate")
    @Qualifier("ownStringRedisTemplate")
    public RedisTemplate<String, String> stringRedisTemplate(RedisConnectionFactory factory)
        throws UnknownHostException {
        StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(factory);
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        template.setValueSerializer(jackson2JsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(CachingConfigurerSupport.class)
    public AppCacheConfigurer appCacheConfigurer() {
        return new AppCacheConfigurer();
    }
}
