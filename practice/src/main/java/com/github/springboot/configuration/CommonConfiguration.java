package com.github.springboot.configuration;

import static org.springframework.context.ConfigurableApplicationContext.CONVERSION_SERVICE_BEAN_NAME;

import com.github.springboot.InitialAfterLoadFinish;
import com.github.springboot.support.editor.DateConversionService;
import com.google.common.collect.Sets;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ConversionServiceFactoryBean;

@Configuration
public class CommonConfiguration {

    @Bean
    @ConditionalOnMissingBean
    public InitialAfterLoadFinish initialAfterLoadFinish() {
        return new InitialAfterLoadFinish();
    }

    // 类型转化器， 可自定义类型转化，如DateConversionService
    @Bean(CONVERSION_SERVICE_BEAN_NAME)
    public ConversionServiceFactoryBean conversionServiceFactoryBean() {
        ConversionServiceFactoryBean bean = new ConversionServiceFactoryBean();
        bean.setConverters(Sets.newHashSet(new DateConversionService()));
        return bean;
    }

}
