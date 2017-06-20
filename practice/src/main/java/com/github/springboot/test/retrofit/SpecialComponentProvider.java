package com.github.springboot.test.retrofit;


import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;

/**
 *  用于处理自定义扫描
 */
public class SpecialComponentProvider extends ClassPathScanningCandidateComponentProvider {

  public SpecialComponentProvider() {
    super(false);
    addIncludeFilter(new AnnotationTypeFilter(RetrofitService.class));
  }

  @Override
  protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
    System.out.println("------------> " + beanDefinition.getMetadata().getClassName());
    return beanDefinition.getMetadata().isInterface();
  }

  public static SpecialComponentProvider getInstance() {
    return new SpecialComponentProvider();
  }

}
