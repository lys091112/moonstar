package com.github.springboot.support.exception;

import lombok.Getter;

import java.util.UUID;

@Getter
class BaseException extends RuntimeException {

    /**
     * uuid of the exception, unique
     */
    private String traceId;

    /**
     * exception exceptionCode
     */
    private ExceptionCode exceptionCode;

    private String message;

    public BaseException(ExceptionCode exceptionCode, String message) {
        traceId = UUID.randomUUID().toString();
        this.exceptionCode = exceptionCode;
        this.message = message;
    }
}
