package io.crescent.moonstar.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;

/**
 * @author crescent
 */
public class DateUtil {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);
  public static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
  public static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  /**
   * 将时间戳转成字符串,返回格式为：yyyy-MM-dd
   */
  public static String getDateToString(long timestamp) {
    try {
      if (timestamp <= 0) {
        return "";
      }
      Instant instant = Instant.ofEpochMilli(timestamp);
      ZoneId zone = ZoneId.systemDefault();
      return dateFormatter.format(LocalDateTime.ofInstant(instant, zone));
    } catch (Exception e) {
      LOGGER.error("解析时间异常！ timestamp = [{}], error=[{}]", timestamp, e);
      return "";
    }
  }

  /**
   * 将时间戳转成字符串,返回格式为：yyyy-MM-dd HH:mm:ss
   */
  public static String getDateTimeToString(long timestamp) {
    try {
      if (timestamp <= 0) {
        return "";
      }
      Instant instant = Instant.ofEpochMilli(timestamp);
      ZoneId zone = ZoneId.systemDefault();
      return dateTimeFormatter.format(LocalDateTime.ofInstant(instant, zone));
    } catch (Exception e) {
      LOGGER.error("解析时间异常！ timestamp = [{}], error=[{}]", timestamp, e);
      return "";
    }
  }

  // 获取当前时间
  public static String getCurrentDate() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
  }

  public static boolean isValidDate(String s) {
    try {
      LocalDateTime parse = LocalDateTime.parse(s, dateTimeFormatter);
      return s.equals(parse.format(dateTimeFormatter));
    } catch (Exception e) {
      return false;
    }
  }

  // 将字符串转换为时间戳
  public static long getStringToDate(String time) {
    try {
      LocalDateTime localDateTime = LocalDateTime.parse(time, dateTimeFormatter);
      return LocalDateTime.from(localDateTime).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
    }
    return LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
  }

  // 直接获取当前时间戳,单位为秒
  public static String getTimeStamp() {
    return String.valueOf(System.currentTimeMillis() / 1000);
  }
}
