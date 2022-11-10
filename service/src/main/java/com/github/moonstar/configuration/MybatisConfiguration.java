package com.github.moonstar.configuration;

import com.zaxxer.hikari.HikariDataSource;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * @author Xianyue
 */

@Configuration
public class MybatisConfiguration {

    @Bean
    @Qualifier(value = "hikari")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource getDataSource() throws Exception {
        return new HikariDataSource();
    }

    @Bean
    public DataSourceTransactionManager transactionManager() throws Exception {
        return new DataSourceTransactionManager(getDataSource());
    }
}
