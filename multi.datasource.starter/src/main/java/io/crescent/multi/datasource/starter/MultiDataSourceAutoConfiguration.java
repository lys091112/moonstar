package io.crescent.multi.datasource.starter;

import io.crescent.multi.datasource.DataSourceClassResolver;
import io.crescent.multi.datasource.DynamicDataSource;
import io.crescent.multi.datasource.annotation.DS;
import io.crescent.multi.datasource.aop.DynamicDataSourceAdvisor;
import io.crescent.multi.datasource.aop.DynamicDataSourceInterceptor;
import org.springframework.aop.Advisor;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Role;

@Configuration
@EnableConfigurationProperties(DynamicDataSourceProperties.class)
@AutoConfigureAfter(DynamicDataSource.class)
public class MultiDataSourceAutoConfiguration {

  private final DynamicDataSourceProperties properties;

  public MultiDataSourceAutoConfiguration(DynamicDataSourceProperties properties) {
    this.properties = properties;
  }

  @Bean
  @Role(BeanDefinition.ROLE_INFRASTRUCTURE)
  @ConditionalOnProperty(prefix = DynamicDataSourceProperties.PREFIX + ".aop", name = "enabled", havingValue = "true", matchIfMissing = true)
  public Advisor dynamicDataSourceAdvisor() {
    DataSourceClassResolver resolver = new DataSourceClassResolver();
    DynamicDataSourceInterceptor interceptor = new DynamicDataSourceInterceptor(resolver);
    return new DynamicDataSourceAdvisor(interceptor, DS.class);
  }

}
