package com.github.springboot.support.interceptor.permission;

import com.alibaba.fastjson.JSON;
import com.github.springboot.support.CommonResponse;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 对其调用的方法做权限校验
 */
@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
        Object handler) throws Exception {

        if (handler.getClass().isAssignableFrom(HandlerMethod.class)) {
            HandlerMethod method = (HandlerMethod) handler;
            Permission permission = method.getMethodAnnotation(Permission.class);
            if (permission == null || permission.canExecute()) {
                return true;
            }

            log.info("can't not execute this method. methodName={}", method.getMethod().getName());
            sendResponse(httpServletResponse);
            return false;
        }
        return true;
    }

    private void sendResponse(HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setStatus(HttpStatus.METHOD_NOT_ALLOWED.value());
        CommonResponse message = CommonResponse.build(false, "Permission Denied");
        httpServletResponse.getWriter().print(JSON.toJSONString(message));
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
        Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest,
        HttpServletResponse httpServletResponse,
        Object o, Exception e) throws Exception {

    }
}
