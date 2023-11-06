package io.crescent.moonstar.intercept.intercepter;

import io.crescent.moonstar.intercept.annotation.WhiteList;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class WhiteListInterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    WhiteList whitelist = ((HandlerMethod) handler).getMethodAnnotation(WhiteList.class);
    // whitelist.values(); 通过 request 获取请求参数，通过 whitelist 变量获取注解参数
    return true;
  }

  @Override
  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    // 方法在Controller方法执行结束后执行
  }

  @Override
  public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    // 在view视图渲染完成后执行
  }
}
