package io.crescent.moonstar.configuration;

import com.zaxxer.hikari.HikariConfig;
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
//@MapperScan("io.crescent.moonstar.mapper")
public class MybatisConfiguration {

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Qualifier(value = "hikari")
    public DataSource getDataSource(HikariConfig hikariConfig) throws Exception {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("hikari") DataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
}
