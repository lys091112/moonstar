package com.github.springboot.exception;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController //以json形式返回
@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ControllerExceptionAdvice {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
  public Object defaultException(HttpServletRequest request, Exception e) throws Exception {
    System.out.println("---------------------------------->exception---------------");
    log.error("catch an error! Host:{}, Url:{}, Error: {}", request.getRemoteHost(),
        request.getRequestURI(), e.getMessage());
    return e.getMessage();
  }

}
