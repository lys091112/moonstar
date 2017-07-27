package com.xianyue.retrofit;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;

/**
 * 用来创建retorfit service
 */
public class RetrofitServiceCreateProcessor extends
    InstantiationAwareBeanPostProcessorAdapter implements BeanFactoryAware, PriorityOrdered {

  static final String BEAN_NAME = "retrofitServiceCreateProcessor";

  private BeanFactory beanFactory;


  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  //TODO 返回制定的service
  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
      throws BeansException {
    if(beanClass.isAnnotationPresent(RetrofitService.class)) {
      return super.postProcessBeforeInstantiation(beanClass, beanName);
    }
    return null;
  }

  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }
}
