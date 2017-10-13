package com.github.springboot.support.exception.bash;

import lombok.Getter;

import java.io.Serializable;

/**
 * 通过traceId, 可以对日志文件进行grep，方便异常排查
 */
@Getter
class ExceptionResponse implements Serializable {
    private int code;
    private String traceId;
    private String message;

    ExceptionResponse(int code, String traceId, String message) {
        this.code = code;
        this.traceId = traceId;
        this.message = message;
    }
}
