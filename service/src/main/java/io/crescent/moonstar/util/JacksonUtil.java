package io.crescent.moonstar.util;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author crescent
 */
public class JacksonUtil {

  public static final Logger LOGGER = LoggerFactory.getLogger(JacksonUtil.class);

  private static JacksonUtil instance = new JacksonUtil();

  private ObjectMapper mapper;

  private JacksonUtil() {
    mapper = new ObjectMapper();
    // 如果json中有新增的字段并且是实体类类中不存在的，不报错
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    //格式化输出
    mapper.configure(SerializationFeature.INDENT_OUTPUT, false);
    //忽略POJO中属性为空的字段
    mapper.setSerializationInclusion(Include.NON_NULL);
    //修改日期格式
    //        mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
  }

  public static void setMapper(Object mapper) {
    Preconditions.checkNotNull(mapper, "objectMapper should not be null");
    Preconditions.checkArgument(mapper instanceof ObjectMapper, "mapper should be an instance of ObjectMapper");
    instance.mapper = (ObjectMapper) mapper;
  }

  public static String toJSONString(Object object) {
    try {
      return instance.mapper.writeValueAsString(object);
    } catch (JsonProcessingException e) {
      LOGGER.error("parse to Json error! e=[{}]", e);
      return "";
    }
  }

  public static ObjectNode parseObject(String json) throws Exception {
    try {
      return instance.mapper.readValue(json, ObjectNode.class);
    } catch (JsonProcessingException e) {
      LOGGER.error("parse to Object error! json = [{}],  e=[{}]", json, e);
      throw e;
    }
  }

  @Deprecated
  public static <T> List<T> parseArray(String text, Class<T> clazz) {
    try {
      return instance.mapper.readValue(text, new TypeReference<List<T>>() {
      });
    } catch (JsonProcessingException e) {
      LOGGER.error("parse to array error! json = [{}], class=[{}],   e=[{}]", text, clazz.getName(), e);
      return Collections.emptyList();
    }
  }

  public static <T> T parseObject(String text, Class<T> clazz) throws Exception {
    try {
      return instance.mapper.readValue(text, clazz);
    } catch (JsonProcessingException e) {
      LOGGER.error("parse to Object error! json = [{}],  class=[{}],  e=[{}]", text, clazz.getName(), e);
      throw e;
    }
  }

  public static <T> T parseObject(String text, TypeReference<T> type) throws Exception {
    try {

      return instance.mapper.readValue(text, type);
    } catch (JsonProcessingException e) {
      LOGGER.error("parse to Object error! json = [{}],  class=[{}],  e=[{}]", text, type.getType(), e);
      throw e;
    }
  }
}
