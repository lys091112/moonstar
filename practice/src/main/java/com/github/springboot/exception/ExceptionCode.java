package com.github.springboot.exception;

import lombok.Getter;

/**
 * �����쳣���͵��쳣��
 */
@Getter
public enum ExceptionCode {

    /**
     * ���Զ����쳣
     */
    DEFAULT(10002, "Ĭ���쳣"),

    /**
     * ģ���쳣ʱʹ�õ��Զ����쳣
     */
    TEST(10001, "�Զ�������쳣");

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
