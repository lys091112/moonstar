package com.github.springboot.util;

import com.github.springboot.config.JedisClusterConfig;
import com.github.springboot.config.RedisPropertiesConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Xianyue
 */
@Component
public class RedisTemplate {
    private static Logger logger = LoggerFactory.getLogger(RedisTemplate.class);

    @Autowired
    private RedisPropertiesConfig redisPropertiesConfig;

    @Autowired
    private JedisClusterConfig jedisClusterConfig;

    private String PREFIX_SPLITE = ":";

    public void set(String prefix, String key, String value) {
        jedisClusterConfig.getJedisCluster().set(prefix + PREFIX_SPLITE + key, value);
        if (logger.isDebugEnabled()) {
            logger.debug("redis set key:{}, value:{}", key, value);
        }
    }

    public Optional<String> get(String prefix, String key) {
        String res = jedisClusterConfig.getJedisCluster().get(prefix + PREFIX_SPLITE + key);
        return null == res ? Optional.empty() : Optional.of(res);

    }
}
