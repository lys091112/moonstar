package com.github.springboot.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController //以json形式返回
@ControllerAdvice(annotations = RestController.class)
@Slf4j
public class ControllerExceptionAdvice {

  @ExceptionHandler(Exception.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  public ExceptionResponse defaultException(HttpServletRequest request, Exception e) {
      log.error("Fetch an error! Host:{}, Url:{}, Excepte: {}", request.getRemoteHost(),
        request.getRequestURI(), e);
      return new ExceptionResponse(ExceptionCode.DEFAULT.getCode(), "", e.getMessage());
  }

    @ExceptionHandler(TestException.class)
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ExceptionResponse ownException(HttpServletRequest request, Exception e) {
        log.error("own exception! Host:{}, Url:{}, Excepte: {}", request.getRemoteHost(),
                request.getRequestURI(), e);
        TestException ex = (TestException) e;
        return new ExceptionResponse(ex.getExceptionCode().getCode(), ex.getTraceId(), e.getMessage());
    }

}
