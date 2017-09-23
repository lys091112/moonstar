package com.github.springboot.support.interceptor;

import com.github.springboot.entity.User;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

public class LocalInterceptor implements HandlerInterceptor {
    private final static String USER_INFO = "userinfo";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession tpm = request.getSession();
        User user = (User) request.getSession(true).getAttribute(USER_INFO);
        if (user == null) {
            user = new User(9, "yue", "");
            request.getSession().setAttribute(USER_INFO, user);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        Cookie cookie = new Cookie("Session-Test", "userolisdfs");
        cookie.setDomain("localhost");
        cookie.setPath("/api/v1");
        response.addCookie(cookie);
        response.getHeaders("Set-Cookie");
    }
}
