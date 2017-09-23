package com.github.springboot.support.exception;

public class PrivilegeCheckException extends BaseException {

  private PrivilegeCheckException(ExceptionCode exceptionCode, String message) {
    super(exceptionCode, message);
  }

  public PrivilegeCheckException(String message) {
    this(ExceptionCode.Privilege, message);
  }

}
