package com.github.springboot.exception;

import org.springframework.dao.DataAccessException;

/**
 * 用于测试事务异常
 */
public class TransException extends DataAccessException {

    public TransException(String msg) {
        super(msg);
    }
}
