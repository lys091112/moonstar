package com.github.springboot.test;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

//@Configurable what's this

@Configuration
@ConditionalOnProperty(value = "practice.test.open", havingValue = "true" )
@Import(MyServiceBeanRegister.class)
@ComponentScan("com.github")
public class CommonTestAutoConfig {

  private static final String test = "autoconfig";

  @Bean
  public AwareBean awareBean() {
    return new AwareBean();
  }

  @Bean
  public MyAwareService myAwareService() {
    return new MyAwareService();
  }

  @Bean
  public InitializingBean createInnitializingBean() {
    return new InnitialzingBeanTest();
  }

  //这两个的执行先后顺序极可能用order区分开
//  @Bean
//  public MyBeanPostProcessorDouble myBeanPostProcessorDouble() {
//    return new MyBeanPostProcessorDouble();
//  }
//  @Bean
//  public MyBeanPostProcessor myBeanPostProcessor() {
//    return new MyBeanPostProcessor();
//  }

//  @Bean
//  public MyServiceBeanRegister myServiceBeanRegister() {
//    return new MyServiceBeanRegister();
//  }
}
