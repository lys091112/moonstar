package com.xianyue.retrofit;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 针对于固定的注解类型进行扫描，并搜集该注解的类信息
 */
public class RetrofitServiceProvider extends ClassPathScanningCandidateComponentProvider {

  private RetrofitServiceProvider() {
    super(false);
    addIncludeFilter(new AnnotationTypeFilter(RetrofitService.class));
  }

  @Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    return beanDefinition.getMetadata().isInterface();
  }

  private static RetrofitServiceProvider provider = new RetrofitServiceProvider();

  public static RetrofitServiceProvider getInstance() {
    return provider;
  }

}
