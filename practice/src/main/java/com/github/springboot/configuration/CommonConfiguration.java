package com.github.springboot.configuration;

import com.github.springboot.InitialAfterLoadFinish;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CommonConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InitialAfterLoadFinish initialAfterLoadFinish() {
        return new InitialAfterLoadFinish();
    }

}
