package com.github.springboot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Xianyue
 */
@Component
@ConfigurationProperties(prefix = "redis.cache")
@Data
public class RedisPropertiesConfig {
    private boolean enabled; //是否启用redis
    private String clusterNodes;
    private int commandTimeout;
    private int expireSeconds;
}
