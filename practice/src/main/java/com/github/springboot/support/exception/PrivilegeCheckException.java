package com.github.springboot.support.exception;

import com.github.springboot.support.exception.bash.BaseException;
import com.github.springboot.support.exception.bash.ExceptionCode;

public class PrivilegeCheckException extends BaseException {

  private PrivilegeCheckException(ExceptionCode exceptionCode, String message) {
    super(exceptionCode, message);
  }

  public PrivilegeCheckException(String message) {
    this(ExceptionCode.Privilege, message);
  }

}
