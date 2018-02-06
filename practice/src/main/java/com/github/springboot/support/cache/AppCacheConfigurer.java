package com.github.springboot.support.cache;

import org.springframework.cache.Cache;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.CacheErrorHandler;


public class AppCacheConfigurer extends CachingConfigurerSupport {


    // TODO
    class MoonCacheErrorHandler implements CacheErrorHandler{

        @Override
        public void handleCacheGetError(RuntimeException exception, Cache cache, Object key) {

        }

        @Override
        public void handleCachePutError(RuntimeException exception, Cache cache, Object key, Object value) {

        }

        @Override
        public void handleCacheEvictError(RuntimeException exception, Cache cache, Object key) {

        }

        @Override
        public void handleCacheClearError(RuntimeException exception, Cache cache) {

        }
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return super.errorHandler();
    }
}
