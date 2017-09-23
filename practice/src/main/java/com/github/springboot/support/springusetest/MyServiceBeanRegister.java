package com.github.springboot.support.springusetest;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyServiceBeanRegister implements ImportBeanDefinitionRegistrar{

  private static final String BEAN_NAME = "mysqlServiceBeanRegister";

  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
      BeanDefinitionRegistry registry) {

    if(registry.containsBeanDefinition(BEAN_NAME)) {
      System.out.println("----------------------->>>>>myservicebeanRegister has been exist");
    }

    registry.registerBeanDefinition(BEAN_NAME, new RootBeanDefinition(MyServiceBeanRegister.class));
    System.out.println("------------------->>> myservicebeanRegister been created");

//    AnnotationAttributes.fromMap(importingClassMetadata.getAnnotationAttributes(getAnnotationAttributes))
  }
}
