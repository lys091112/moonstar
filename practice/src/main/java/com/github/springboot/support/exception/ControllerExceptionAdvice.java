package com.github.springboot.support.exception;

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

  @ExceptionHandler(Throwable.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  public ExceptionResponse defaultException(HttpServletRequest request, Exception e) {
      log.error("Fetch an error! Host:{}, Url:{}, Except:{}", request.getRemoteHost(),
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

  @ExceptionHandler(PrivilegeCheckException.class)
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  public ExceptionResponse privilegeCheckException(HttpServletRequest request, Exception e) {
    log.error("own exception! Host:{}, Url:{}, Excepte: {}", request.getRemoteHost(),
        request.getRequestURI(), e);
    PrivilegeCheckException ex = (PrivilegeCheckException) e;
    return new ExceptionResponse(ex.getExceptionCode().getCode(), ex.getTraceId(), e.getMessage());
  }
}
