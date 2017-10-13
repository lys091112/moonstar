package com.github.springboot.support.exception;

import com.github.springboot.support.exception.bash.BaseException;
import com.github.springboot.support.exception.bash.ExceptionCode;

public class TestException extends BaseException {

    public TestException(String message) {
        super(ExceptionCode.TEST, message);
    }
}
