package com.github.springboot.support.exception;

import lombok.Getter;

/**
 */
@Getter
public enum ExceptionCode {

    /**
     * 默认异常
     */
    DEFAULT(10002, "默认异常"),

    /**
     * 用于自定义测试异常
     */
    TEST(10001, "自定义测试异常"),

    /**
     *  权限检测异常
     */
    Privilege(10002, "权限不足");

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
