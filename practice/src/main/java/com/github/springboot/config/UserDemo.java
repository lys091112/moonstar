package com.github.springboot.config;


import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 * @ConfigurationProperties(prefix="user") 自动读取application.properties（是spring-boot默认查找的文件）文件中的user.*的属性
 * 在没有使用@ConfigurationProperties的情况下，可以使用@Value("${user.id}")来一个个指定属性的值
 */
@Component
@ConfigurationProperties(prefix = "user")
@Data
@Accessors(chain = true)
public class UserDemo implements Serializable {


    private static final long serialVersionUID = 2396093311392271629L;
    private int    id;
    private String userName;
    private String password;

    @Override
    public String toString() {
        return "userName: " + userName + ", password: " + password;
    }
}
