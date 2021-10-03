package com.github.moonstar;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuhongjun
 * @note
 * @since 2019-04-28
 */
@SpringBootApplication(scanBasePackages = "com.github.moonstar")
@MapperScan("com.github.moonstar.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
