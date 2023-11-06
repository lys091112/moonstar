package io.crescent.moonstar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author liuhongjun
 * @note
 * @since 2019-04-28
 */
@SpringBootApplication(scanBasePackages = "io.crescent.moonstar")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
