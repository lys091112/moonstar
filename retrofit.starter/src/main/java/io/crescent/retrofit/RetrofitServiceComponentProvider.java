package io.crescent.retrofit;

import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 * 针对于固定的注解类型进行扫描，并搜集该注解的类信息
 */
public class RetrofitServiceComponentProvider extends ClassPathScanningCandidateComponentProvider {

  private RetrofitServiceComponentProvider() {
    super(false);
    addIncludeFilter(new AnnotationTypeFilter(RetrofitService.class));
  }

  @Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    return beanDefinition.getMetadata().isInterface();
  }

  private static RetrofitServiceComponentProvider provider = new RetrofitServiceComponentProvider();

  public static RetrofitServiceComponentProvider getInstance() {
    return provider;
  }

}
