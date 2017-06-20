package com.github.springboot.config;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @ConfigurationProperties(prefix="user") 自动读取application.properties（是spring-boot默认查找的文件）文件中的user.*的属性
 * 在没有使用@ConfigurationProperties的情况下，可以使用@Value("${user.id}")来一个个指定属性的值
 */
@Component
@ConfigurationProperties(prefix = "user")
@Data
public class UserDemo {
    //    @Value(value = "${user.id}")
    private int    id;
    private String userName;
    private String password;

    @Override
    public String toString() {
        return "userName: " + userName + ", password: " + password;
    }
}
