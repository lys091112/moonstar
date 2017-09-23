package com.github.springboot.support.springusetest;

import java.beans.PropertyDescriptor;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;

/**
 * 会为每个spring加载的类执行这个操作，那么必然造成加载速度较慢
 */
public class MyBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter {

  public MyBeanPostProcessor() {
    super();
    System.out.println("这是MyBeanPostProcessor实现类构造器！！");
  }

  /**
   * 接口方法、实例化Bean之前调用
   *
   * @see InstantiationAwareBeanPostProcessorAdapter
   **/

  @Override
  public Object postProcessBeforeInstantiation(Class beanClass, String beanName)
      throws BeansException {
    System.out.println("before beanName " + beanName);
//    System.out.println("调用postProcessBeforeInstantiation方法");
    return null;
  }

  /**
   *
   * 接口方法、实例化Bean之后调用 属于BeanPostProcessor
   * @see org.springframework.beans.factory.config.BeanPostProcessor
   * @see org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory
   */
  @Override
  public Object postProcessAfterInitialization(Object bean, String beanName)
      throws BeansException {
    System.out.println("after beanName " + beanName);
//    if(beanName.equals("userDemoValue")) {
//      System.out.println("-------userdemovalue-------------------------> " + ((UserDemoValue)bean).getUserName());
//    }
//    System.out.println("调用postProcessAfterInitialization方法");
    return bean;
  }

  /**
   *
   * 接口方法、设置某个属性时调用, 此时这个类的属性值还没有被初始化
   **/

  @Override
  public PropertyValues postProcessPropertyValues(PropertyValues pvs,
      PropertyDescriptor[] pds, Object bean, String beanName)
      throws BeansException {
    System.out.println("post Name " + beanName);
    if (beanName.contains("userDemoValue")) {
      System.out.println("-------------------------------->" + pvs.getPropertyValue("userName"));
    }
//    System.out.println("InstantiationAwareBeanPostProcessor调用postProcessPropertyValues方法");
    return pvs;
  }
}
