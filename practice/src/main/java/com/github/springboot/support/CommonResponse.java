package com.github.springboot.support;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class CommonResponse {

    private boolean result;
    private String message;

    public static CommonResponse build(boolean result, String message) {
        return new CommonResponse().setResult(result).setMessage(message);
    }
}
