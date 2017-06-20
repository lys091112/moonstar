package com.github.springboot.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * {@link Configuration} and {@link Value} to auto construct entity
 *
 * see other way {@link UserDemo}
 */
@Data
@Configuration
public class UserDemoValue {
    @Value(value = "${user.id}")
    private int    id;
    @Value(value = "${user.userName}")
    private String userName;
    @Value(value = "${user.password}")
    private String password;

    @PostConstruct
    public void init() {
//        System.out.println("-------------------> ");
//        System.out.println(this);
//        System.out.println("------------------------------->>");
    }

    @Override
    public String toString() {
        return "userId" + id + ", userName: " + userName + ", password: " + password;
    }
}
