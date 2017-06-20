package com.github.springboot.test;

import java.beans.PropertyDescriptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

/**
 * 会为每个spring加载的类执行这个操作，那么必然造成加载速度较慢
 */
public class MyBeanPostProcessorDouble extends InstantiationAwareBeanPostProcessorAdapter {

  public MyBeanPostProcessorDouble() {
    super();
    System.out.println("这是---> NewMyBeanPostProcessor实现类构造器！！");
  }

  // 接口方法、实例化Bean之前调用
  @Override
  public Object postProcessBeforeInstantiation(Class beanClass, String beanName)
      throws BeansException {
    System.out.println("---> New before beanName " + beanName);
//    System.out.println("调用postProcessBeforeInstantiation方法");
    return null;
  }

  // 接口方法、实例化Bean之后调用
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName)
      throws BeansException {
    System.out.println("---> New after beanName " + beanName);
//    System.out.println("调用postProcessAfterInitialization方法");
    return bean;
  }

  // 接口方法、设置某个属性时调用
  @Override
  public PropertyValues postProcessPropertyValues(PropertyValues pvs,
      PropertyDescriptor[] pds, Object bean, String beanName)
      throws BeansException {
    System.out.println("---> New post Name " + beanName);
//    System.out.println("InstantiationAwareBeanPostProcessor调用postProcessPropertyValues方法");
    return pvs;
  }
}
