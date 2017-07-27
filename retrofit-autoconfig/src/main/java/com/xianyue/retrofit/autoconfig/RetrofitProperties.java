package com.xianyue.retrofit.autoconfig;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * retorfit properties
 */
@ConfigurationProperties(prefix = "retrofit")
@Getter
public class RetrofitProperties {

  public List<Endpoint> endpoints = new ArrayList<>();
  private Long connectionTimeout;

  @Getter
  public static class Endpoint {

    private String idetify;
    private String baseUrl;
  }
}
