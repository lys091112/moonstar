package com.github.moonstar.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liuhongjun
 * @since 2019-06-26
 */
@Data
@Accessors(chain = true)
public class ResultView<T> {

    private int code;
    private String message;
    private T data;

    public static <T> ResultView<T> success(T data) {
        return new ResultView<T>().setCode(200).setData(data);
    }

    public static <T> ResultView<T> failed(int code, String message, T data) {
        return new ResultView<T>().setCode(code).setMessage(message).setData(data);
    }
}
