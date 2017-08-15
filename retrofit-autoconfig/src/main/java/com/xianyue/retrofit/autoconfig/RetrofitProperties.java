package com.xianyue.retrofit.autoconfig;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * retorfit properties
 */
@Data
@ConfigurationProperties(prefix = "retrofit")
public class RetrofitProperties {

  public List<Endpoint> endpoints = new ArrayList<>();
  private Long connectionTimeout;
  private Long readTimeout;
  private Long wirteTimeout;

  private Integer maxIdleConnection = 5;
  private Integer keepAliveDuration = 5;

  @Data
  public static class Endpoint {

    private String idetify;
    private String baseUrl;
  }
}
