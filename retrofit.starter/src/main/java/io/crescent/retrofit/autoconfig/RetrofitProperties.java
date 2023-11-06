package io.crescent.retrofit.autoconfig;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * retrofit properties
 */
@Data
@ConfigurationProperties(prefix = "retrofit")
public class RetrofitProperties {

  public List<Endpoint> endpoints = new ArrayList<>();
  private Long connectionTimeout;
  private Long readTimeout;
  private Long writeTimeout;
  private String logLevel;

  private Integer maxIdleConnection = 5;
  private Integer keepAliveDuration = 5;

  @Data
  public static class Endpoint {

    private String identify;
    private String baseUrl;
  }
}
