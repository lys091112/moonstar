package io.crescent.moonstar.intercept.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author crescent
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface MethodMetrics {

  /**
   * 业务名称
   */
  String name() default "";

  /**
   * 业务描述
   */
  String desc() default "";

  String[] tags() default {};

  //是否记录时间间隔
  boolean withoutDuration() default false;
}