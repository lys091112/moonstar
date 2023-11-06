package io.crescent.multi.datasource.aop;

import java.io.Serial;
import java.lang.annotation.Annotation;
import org.aopalliance.aop.Advice;
import org.aopalliance.intercept.MethodInterceptor;
import org.springframework.aop.ClassFilter;
import org.springframework.aop.MethodMatcher;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.aop.support.annotation.AnnotationMethodMatcher;

/**
 * @author crescent
 */
public class DynamicDataSourceAdvisor extends AbstractPointcutAdvisor {

  @Serial
  private static final long serialVersionUID = -8525136668064882498L;
  private Advice advice;
  private Pointcut pointcut;

  public DynamicDataSourceAdvisor(MethodInterceptor advice, Class<? extends Annotation> annotationType) {
    this.advice = advice;
    this.pointcut = buildPointcut(annotationType);
  }


  @Override
  public Pointcut getPointcut() {
    return pointcut;
  }

  @Override
  public Advice getAdvice() {
    return advice;
  }

  private Pointcut buildPointcut(Class<? extends Annotation> annotationType) {
    Pointcut cpc = new AnnotationMatchingPointcut(annotationType, true);
    Pointcut mpc = new AnnotationMethodPointcut(annotationType);
    return new ComposablePointcut(cpc).union(mpc);
  }

  @Override
  public String toString() {
    return "DynamicDataSourceAdvisor{" + "advice=" + advice + ", pointcut=" + pointcut + '}';
  }

  private class AnnotationMethodPointcut implements Pointcut {

    private Class<? extends Annotation> annotation;

    public AnnotationMethodPointcut(Class<? extends Annotation> annotation) {
      this.annotation = annotation;
    }

    @Override
    public ClassFilter getClassFilter() {
      return ClassFilter.TRUE;
    }

    @Override
    public MethodMatcher getMethodMatcher() {
      return new AnnotationMethodMatcher(annotation, true);
    }
  }
}
