package io.crescent.multi.datasource.annotation;

import io.crescent.multi.datasource.DataSourceType;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

/**
 * @author crescent
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface DS {

  /**
   * @return the database to switch
   */
  String value();

  /**
   * @return 数据源类型
   */
  DataSourceType type() default DataSourceType.NORMAL;
}