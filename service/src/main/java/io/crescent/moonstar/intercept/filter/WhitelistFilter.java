package io.crescent.moonstar.intercept.filter;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import java.io.IOException;

/**
 * @author crescent
 *
 * Spring Boot3.0已经为所有依赖项从 Java EE 迁移到 Jakarta EE API，导致 servlet 包名的修改
 */
public class WhitelistFilter implements Filter {

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    // 初始化后被调用一次
  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
    // 判断是否需要拦截
    chain.doFilter(request, response);
  }

  @Override
  public void destroy() {
    // 被销毁时调用一次
  }
}