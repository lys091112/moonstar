package com.github.springboot.util;


import com.alibaba.fastjson.JSON;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 */
public class RequestHelper {
    private final static String defaultEncoding = "UTF-8";

    public static <T> T getJsonFromReqeust(HttpServletRequest request, Class<T> clazz) throws IOException {
        return getJsonFromReqeust(request, clazz, defaultEncoding);
    }

    public static <T> T getJsonFromReqeust(HttpServletRequest request, Class<T> clazz, String encoding) throws IOException {
        String str = IOUtils.toString(request.getInputStream(), encoding);
        return JSON.parseObject(str, clazz);
    }
}
