package com.xianyue.retrofit;

import com.xianyue.retrofit.autoconfig.RetrofitServiceHolder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import retrofit2.Retrofit;

/**
 * 用来创建retorfit service
 */
public class RetrofitServiceCreateProcessor extends InstantiationAwareBeanPostProcessorAdapter implements
    BeanFactoryAware, PriorityOrdered {

  static final String BEAN_NAME = "retrofitServiceCreateProcessor";

  private BeanFactory beanFactory;

  private RetrofitCreateFactory retrofitCreateFactory;

  @Override
  public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
    this.beanFactory = beanFactory;
  }

  @Override
  public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName)
      throws BeansException {
    if(beanClass.isAnnotationPresent(RetrofitService.class)) {
      RetrofitService service = beanClass.getAnnotation(RetrofitService.class);
      String tenant = service.value();
      return buildService(beanClass, tenant);
    }
    return null;
  }

  private Object buildService(Class<?> beanClass, String tenant) {
    if (null == retrofitCreateFactory) {
      // getBean 会保证对类进行初始化
      RetrofitServiceHolder holder = beanFactory.getBean(RetrofitServiceHolder.class);
      retrofitCreateFactory = new RetrofitCreateFactory(holder);
    }
    return retrofitCreateFactory.craeteRetrofitInstance(beanClass, tenant);
  }


  @Override
  public int getOrder() {
    return Ordered.HIGHEST_PRECEDENCE;
  }

  private static class RetrofitCreateFactory {

    private RetrofitServiceHolder holder;

    RetrofitCreateFactory(RetrofitServiceHolder holder) {
      this.holder = holder;
    }

    <T> T craeteRetrofitInstance(Class<T> clazz, String tenant) {
      Retrofit retrofit = holder.getRetrofit(tenant)
          .orElseThrow(() -> new RuntimeException("can't find Retrofit where tenent = " + tenant));
      return retrofit.create(clazz);
    }
  }
}
