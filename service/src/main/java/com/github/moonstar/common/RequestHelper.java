package com.github.moonstar.common;

import com.alibaba.fastjson.JSON;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuhongjun
 * @since 2019-06-26
 */
@UtilityClass
public class RequestHelper {

    public static final Logger LOGGER = LoggerFactory.getLogger(RequestHelper.class);

    public String getJson(HttpServletRequest request) throws IOException {
        return getJson(request, "utf-8");
    }

    public String getJson(HttpServletRequest request, String encoding) throws IOException {
        return IOUtils.toString(request.getInputStream(), encoding);
    }

    public <T> T getFromJson(HttpServletRequest request, Class<T> clazz) throws IOException {
        return getFromJson(request, clazz, "utf-8");
    }

    public <T> T getFromJson(HttpServletRequest request, Class<T> clazz, String encoding) throws IOException {
        String body = "";
        try {
            body = getJson(request, encoding);
            return JSON.parseObject(body, clazz);
        } catch (IOException e) {
            LOGGER.error("convert error! body={}, className={}, e={}", body, clazz.getSimpleName(), e);
            throw e;
        }
    }

}
