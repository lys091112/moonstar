package com.github.springboot.aop;

import com.github.springboot.exception.PrivilegeCheckException;
import com.google.common.collect.ImmutableSet;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 一个简单的访问权限验证demo， 可以在此基础上进行扩展
 */
@Slf4j
@Aspect
@Component
public class UserPermissionAscept {

  private Set<String> allowMethods = ImmutableSet.of("GET", "POST", "PUT", "DELETE");


  @Pointcut("execution(* com.github.springboot.controller.UserController.*(..))")
  public void methodPointCut() {
  }

  @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
  public void annatationPointCut() {
  }


  @Before("methodPointCut()")
  public void before(JoinPoint point) {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
        .getRequest();
    if (allowMethods.contains(request.getMethod())) {
      log.info("this is a allow method request");
      return;
    }
    throw new PrivilegeCheckException("only can use get method");
  }

  @Around("methodPointCut() && annatationPointCut()")
  public void checkPermission(ProceedingJoinPoint point) throws Throwable {

    try {
      point.proceed();
    } catch (Throwable ex) {
      // do nothing
      throw ex;
    }
    log.info("target: {} ", point.getTarget());
  }
}
