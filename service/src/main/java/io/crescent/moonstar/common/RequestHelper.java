package io.crescent.moonstar.common;

import io.crescent.moonstar.util.JacksonUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author liuhongjun
 * @since 2019-06-26
 */
public class RequestHelper {

    public static final Logger LOGGER = LoggerFactory.getLogger(RequestHelper.class);

    public static String getJson(HttpServletRequest request) throws Exception {
        return getJson(request, "utf-8");
    }

    private static String getJson(HttpServletRequest request, String encoding) throws Exception {
        return IOUtils.toString(request.getInputStream(), encoding);
    }

    public static <T> T getFromJson(HttpServletRequest request, Class<T> clazz) throws Exception {
        return getFromJson(request, clazz, "utf-8");
    }

    public static <T> T getFromJson(HttpServletRequest request, Class<T> clazz, String encoding) throws Exception {
        String body = "";
        try {
            body = getJson(request, encoding);
            return JacksonUtil.parseObject(body, clazz);
        } catch (Exception e) {
            LOGGER.error("convert error! body={}, className={}, e={}", body, clazz.getSimpleName(), e);
            throw e;
        }
    }

}
