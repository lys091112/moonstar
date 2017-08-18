package com.github.springboot.exception;

/**
 * 自定义测试异常
 */
public class TestException extends BaseException {

    public TestException(String message) {
        super(ExceptionCode.TEST, message);
    }
}
