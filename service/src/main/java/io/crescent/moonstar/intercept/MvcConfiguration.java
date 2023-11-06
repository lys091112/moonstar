package io.crescent.moonstar.intercept;

import io.crescent.moonstar.intercept.filter.WhitelistFilter;
import io.crescent.moonstar.intercept.intercepter.WhiteListInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class MvcConfiguration extends WebMvcConfigurationSupport {

  @Override
  public void addInterceptors(InterceptorRegistry registry) {
    registry.addInterceptor(new WhiteListInterceptor()).addPathPatterns("/*").order(1);
    // 这里可以配置拦截器启用的 path 的顺序，在有多个拦截器存在时，任一拦截器返回 false 都会使后续的请求方法不再执行
  }

  @Bean
  public FilterRegistrationBean someFilterRegistration() {
    FilterRegistrationBean registration = new FilterRegistrationBean();
    registration.setFilter(new WhitelistFilter());
    registration.addUrlPatterns("/*");
    registration.setName("whitelistFilter");
    // 设置过滤器被调用的顺序
    registration.setOrder(1);
    return registration;
  }
}