package io.crescent.moonstar.intercept.aspect;

import io.crescent.moonstar.intercept.annotation.MethodMetrics;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import java.lang.reflect.Method;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ClassUtils;
import org.springframework.util.StringUtils;

@Aspect
public class PrometheusAnnotationAspect {

  @Autowired
  private MeterRegistry meterRegistry;

  @Pointcut("@annotation(io.crescent.moonstar.intercept.annotation.MethodMetrics)")
  public void pointcut() {
  }

  @Around(value = "pointcut()")
  public Object process(ProceedingJoinPoint joinPoint) throws Throwable {
    Method targetMethod = ((MethodSignature) joinPoint.getSignature()).getMethod();
    Method currentMethod = ClassUtils.getUserClass(joinPoint.getTarget().getClass()).getDeclaredMethod(targetMethod.getName(), targetMethod.getParameterTypes());
    if (currentMethod.isAnnotationPresent(MethodMetrics.class)) {
      MethodMetrics methodMetrics = currentMethod.getAnnotation(MethodMetrics.class);
      return processMetric(joinPoint, currentMethod, methodMetrics);
    } else {
      return joinPoint.proceed();
    }
  }

  /**
   * 侵入的使用方式： meterRegistry.counter("sms.send","vendor","aliyun").increment();
   *
   * @param joinPoint
   * @param currentMethod
   * @param methodMetrics
   * @return
   */
  private Object processMetric(ProceedingJoinPoint joinPoint, Method currentMethod, MethodMetrics methodMetrics) {
    String name = methodMetrics.name();
    if (!StringUtils.hasText(name)) {
      name = currentMethod.getName();
    }
    String desc = methodMetrics.desc();
    if (!StringUtils.hasText(desc)) {
      desc = currentMethod.getName();
    }
    //不需要记录时间
    if (methodMetrics.withoutDuration()) {
      Counter counter = Counter.builder(name).tags(methodMetrics.tags()).description(desc).register(meterRegistry);
      try {
        return joinPoint.proceed();
      } catch (Throwable e) {
        throw new IllegalStateException(e);
      } finally {
        counter.increment();
      }
    }
    //需要记录时间（默认）
    Timer timer = Timer.builder(name).tags(methodMetrics.tags()).description(desc).register(meterRegistry);
    return timer.record(() -> {
      try {
        return joinPoint.proceed();
      } catch (Throwable e) {
        throw new IllegalStateException(e);
      }
    });
  }
}