package com.github.springboot.exception;

/**
 * �Զ�������쳣
 */
public class TestException extends BaseException {

    public TestException(String message) {
        super(ExceptionCode.TEST, message);
    }
}
