package com.xianyue.retrofit;

import com.google.common.collect.Sets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

import java.util.Set;

import static com.xianyue.retrofit.RetrofitServiceCreateProcessor.BEAN_NAME;

/**
 * 注册postprocessor， 并获取定义的可扫描路径(通过componentScan)
 */
public class RetrofitServiceBeanRegister implements ImportBeanDefinitionRegistrar {

    private static final Logger log = LoggerFactory.getLogger(RetrofitServiceBeanRegister.class);

  @Override
  public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata,
      BeanDefinitionRegistry registry) {

    if (!registry.containsBeanDefinition(BEAN_NAME)) {
      registry.registerBeanDefinition(BEAN_NAME,
          new RootBeanDefinition(RetrofitServiceCreateProcessor.class));
    }

    doRetrofitRegister(registry, importingClassMetadata);
  }

  //用来对执行的包进行扫描，并进行注册
  private void doRetrofitRegister(BeanDefinitionRegistry registry, AnnotationMetadata importingClassMetadata) {
    RetrofitServiceComponentProvider provider = RetrofitServiceComponentProvider.getInstance();

    Set<String> packagesToScans = getScanPackages(registry, importingClassMetadata);

    for (String packageToScan: packagesToScans ) {
      Set<BeanDefinition> candidateComponents = provider.findCandidateComponents(packageToScan);
      if(candidateComponents.isEmpty()) {
        return ;
      }

      // register bean
      for (BeanDefinition bean: candidateComponents) {
        String beanName = generateBeanName(bean);
        log.debug("generate retrofit class. className ={}", bean.getBeanClassName());
        registry.registerBeanDefinition(beanName, bean);
      }
    }

  }

  private Set<String> getScanPackages(BeanDefinitionRegistry registry,
      AnnotationMetadata importingClassMetadata) {

    AnnotationAttributes annotationAttributes = AnnotationAttributes
        .fromMap(importingClassMetadata.getAnnotationAttributes(RetrofitCompnontScan.class.getName()));
    String[] values = annotationAttributes.getStringArray("value");

    if (null == values || values.length == 0) {
      return Sets.newHashSet(ClassUtils.getPackageName(importingClassMetadata.getClassName()));
    }
    return Sets.newHashSet(values);
  }

  private String generateBeanName(BeanDefinition bean) {

    try {
      Class<?> clazz = Class.forName(bean.getBeanClassName());
      RetrofitService service = clazz.getAnnotation(RetrofitService.class);
      if (null != service.name() && StringUtils.hasText(service.name())) {
        return service.name();
      }

      Qualifier qualifier = clazz.getAnnotation(Qualifier.class);
      if (null != qualifier) {
        return qualifier.value();
      }

      return clazz.getSimpleName();
    } catch (ClassNotFoundException e) {
      throw new RuntimeException("can't find class=" + bean.getBeanClassName() + "exception=" + e);
    }
  }
}
