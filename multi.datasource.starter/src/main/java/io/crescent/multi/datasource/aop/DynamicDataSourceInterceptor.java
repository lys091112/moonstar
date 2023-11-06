package io.crescent.multi.datasource.aop;

import io.crescent.multi.datasource.DataSourceHolder;
import io.crescent.multi.datasource.DataSourceClassResolver;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class DynamicDataSourceInterceptor implements MethodInterceptor {

  private final DataSourceClassResolver dataSourceClassResolver;

  public DynamicDataSourceInterceptor(DataSourceClassResolver dataSourceClassResolver) {
    this.dataSourceClassResolver = dataSourceClassResolver;
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    String key = dataSourceClassResolver.findKey(invocation.getMethod(), invocation.getThis());
    try {
      DataSourceHolder.set(key);
      return invocation.proceed();
    } finally {
      DataSourceHolder.remove();
    }
  }
}
