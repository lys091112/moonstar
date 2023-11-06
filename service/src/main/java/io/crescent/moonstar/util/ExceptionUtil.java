package io.crescent.moonstar.util;

/**
 * @author crescent
 */
public class ExceptionUtil {

  /**
   * 默认取10层异常日志记录
   *
   * @param e 业务异常
   */
  public static String getExceptionStack(Throwable e) {
    return getExceptionStack(e, 10);
  }

  public static String getExceptionStack(Throwable e, int depth) {
    if (null == e) {
      return "";
    }

    StringBuilder sb = new StringBuilder();
    StackTraceElement[] elements = e.getStackTrace();
    int i = 0;
    for (StackTraceElement element : elements) {
      if (i >= Math.min(elements.length, depth)) {
        break;
      }
      sb.append("\n    ").append(element.getClassName()).append(".").append(element.getMethodName()).append("(").append(element.getFileName()).append(") at line ")
          .append(element.getLineNumber());
      i++;
    }

    return sb.toString();
  }

}
