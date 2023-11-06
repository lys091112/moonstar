package io.crescent.multi.datasource.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = DynamicDataSourceProperties.PREFIX)
@Data
public class DynamicDataSourceProperties {

  public static final String PREFIX = "spring.datasource.dynamic";

}
