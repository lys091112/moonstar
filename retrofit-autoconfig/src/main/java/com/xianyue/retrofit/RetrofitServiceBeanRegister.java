package com.xianyue.retrofit;

import static com.xianyue.retrofit.RetrofitServiceCreateProcessor.BEAN_NAME;

import com.google.common.collect.Sets;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.websocket.ClientEndpointConfig;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.ClassUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

/**
 * 注册postprocessor， 并获取定义的可扫描路径(通过componentScan)
 */
public class RetrofitServiceBeanRegister implements ImportBeanDefinitionRegistrar {

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
  private void doRetrofitRegister(BeanDefinitionRegistry registry,
      AnnotationMetadata importingClassMetadata) {
    RetrofitServiceProvider provider = RetrofitServiceProvider.getInstance();

    Set<String> packagesToScans = getScanPackages(registry, importingClassMetadata);

    for (String packageToScan: packagesToScans ) {
      Set<BeanDefinition> candidateComponents = provider.findCandidateComponents(packageToScan);
      if(candidateComponents.isEmpty()) {
        return ;
      }

      //get bean name

      // register bean
      for (BeanDefinition bean: candidateComponents) {
        registry.registerBeanDefinition(bean.getBeanClassName(), bean);
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
}
