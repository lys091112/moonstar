package com.github.springboot.exception;

import lombok.Getter;

/**
 * 定义异常类型的异常码
 */
@Getter
public enum ExceptionCode {

    /**
     * 非自定义异常
     */
    DEFAULT(10002, "默认异常"),

    /**
     * 模拟异常时使用的自定义异常
     */
    TEST(10001, "自定义测试异常");

    private int code;

    private String description;

    ExceptionCode(int code, String description) {
        this.code = code;
        this.description = description;
    }

    @Override
    public String toString() {
        return "ExceptionCode{" +
                "code=" + code +
                ", description='" + description + '\'' +
                '}';
    }
}
