package com.github.springboot.support.exception.bash;

import com.github.springboot.support.exception.PrivilegeCheckException;
import com.github.springboot.support.exception.TestException;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handle(HttpServletRequest request, MethodArgumentNotValidException exception) {
        Map<String, Object> error = error(exception.getBindingResult().getFieldErrors()
            .stream().map(FieldError::getDefaultMessage).collect(Collectors.toList()));

        log.error("Fetch an error! Host:{}, Url:{}, Except:{}", request.getRemoteHost(),
            request.getRequestURI(), error);
        return error;
    }


    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, Object> handle(HttpServletRequest request, ConstraintViolationException exception) {
        Map<String, Object> error = error(exception.getConstraintViolations()
            .stream().map(ConstraintViolation::getMessage).collect(Collectors.toList()));

        log.error("Fetch an error! Host:{}, Url:{}, Except:{}", request.getRemoteHost(),
            request.getRequestURI(), error);
        return error;
    }

    private Map<String, Object> error(Object message) {
        return Collections.singletonMap("error", message);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
    public ExceptionResponse ownException(HttpServletRequest request, TestException e) {
        log.error("own exception! Host:{}, Url:{}, Excepte: {}", request.getRemoteHost(),
                request.getRequestURI(), e);
        return new ExceptionResponse(e.getExceptionCode().getCode(), e.getTraceId(), e.getMessage());
    }

  @ExceptionHandler
  @ResponseStatus(HttpStatus.EXPECTATION_FAILED)
  public ExceptionResponse privilegeCheckException(HttpServletRequest request, PrivilegeCheckException ex) {
    log.error("own exception! Host:{}, Url:{}, Excepte: {}", request.getRemoteHost(),
        request.getRequestURI(), ex);
    return new ExceptionResponse(ex.getExceptionCode().getCode(), ex.getTraceId(), ex.getMessage());
  }
}
