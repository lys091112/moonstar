package com.github.springboot.exception;

public class PrivilegeCheckException extends BaseException {

  private PrivilegeCheckException(ExceptionCode exceptionCode, String message) {
    super(exceptionCode, message);
  }

  public PrivilegeCheckException(String message) {
    this(ExceptionCode.Privilege, message);
  }

}
