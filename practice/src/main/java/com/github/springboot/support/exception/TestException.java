package com.github.springboot.support.exception;

public class TestException extends BaseException {

    public TestException(String message) {
        super(ExceptionCode.TEST, message);
    }
}
