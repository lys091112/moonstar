package io.crescent.moonstar.dto;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @author liuhongjun
 * @since 2019-06-26
 */
@Data
@Accessors(chain = true)
public class ResultMessage<T> {

    private int code;
    private String message;
    private T data;

    public ResultMessage(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static <T> ResultMessage<T> success(T data) {
        return new ResultMessage<>(200, "", data);
    }

    public static <T> ResultMessage<T> failed(String message) {
        return new ResultMessage<>(500, message, null);
    }

    public static <T> ResultMessage<T> failed(int code, String message, T data) {
        return new ResultMessage<>(code, message, data);
    }
}
