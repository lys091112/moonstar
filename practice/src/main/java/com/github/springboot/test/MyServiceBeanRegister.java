package com.github.springboot.test;

import com.github.springboot.config.UserDemo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

@DependsOn("userDemo")
public class MyServiceBeanRegister implements ImportBeanDefinitionRegistrar{

  private static final String BEAN_NAME = "mysqlServiceBeanRegister";

  @Autowired
  UserDemo userDemo;

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
