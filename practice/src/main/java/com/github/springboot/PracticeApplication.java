package com.github.springboot;

import io.prometheus.client.spring.boot.EnablePrometheusEndpoint;
import io.prometheus.client.spring.boot.EnableSpringBootMetricsCollector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author Xianyue
 * @Configuration：该注解指明该类由spring容器管理
 * @EnableAutoConfiguration:spring boot的注解一般只用于主类， 是无xml配置启动的关键部分,明确指定了扫描包的路径为其修饰的主类的包（这也就是为什么主类要放在根包路径下的原因）
 * @ComponentScan：该注解指定扫描包（如果主类不是位于根路径下，这里需要指定扫描路径）,类似于spring的包扫描注解,扫描路径由@EnableAutoConfiguration指定了
 */
@SpringBootApplication
// @SpringBootApplication(scanBasePackages = {"com.github.springboot"})
//same as @Configuration+@EnableAutoConfiguration+@ComponentScan
@EnableAspectJAutoProxy
@EnablePrometheusEndpoint
@EnableSpringBootMetricsCollector
public class PracticeApplication {

  public static void main(String[] args) {
    SpringApplication.run(PracticeApplication.class, args);
  }
}
