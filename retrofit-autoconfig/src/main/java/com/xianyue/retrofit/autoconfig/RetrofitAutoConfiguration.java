package com.xianyue.retrofit.autoconfig;

import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.concurrent.TimeUnit;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import retrofit2.Converter;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * retrofit 自动加载类
 */
@Configuration
@ConditionalOnClass({OkHttpClient.class, Retrofit.class})
@EnableConfigurationProperties(RetrofitProperties.class)
public class RetrofitAutoConfiguration {

  private final RetrofitProperties retrofitProperties;

  @Autowired
  public RetrofitAutoConfiguration(RetrofitProperties retrofitProperties) {
    this.retrofitProperties = retrofitProperties;
  }

  @Bean
  @ConditionalOnMissingBean(ConnectionPool.class)
  public ConnectionPool connectionPool() {
    return new ConnectionPool(10, 5, TimeUnit.MINUTES);
  }

  // TODO{crescent} 添加日志拦截器， 执行时间监控拦截器
  @Bean
  public OkHttpClient okHttpClient(ConnectionPool connectionPool) {
    OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(
        5, TimeUnit.SECONDS).readTimeout(5, TimeUnit.SECONDS)
        .writeTimeout(5, TimeUnit.SECONDS).connectionPool(connectionPool).build();
    return new OkHttpClient();
  }

  @Bean
  @ConditionalOnBean({Converter.Factory.class, OkHttpClient.class})
  public RetrofitServiceHolder retrofit(List<Factory> factories, OkHttpClient client) {
    Retrofit.Builder builder = new Retrofit.Builder();
    factories.forEach(factory -> builder.addConverterFactory(factory));
    builder.client(client);

    if (CollectionUtils.isEmpty(retrofitProperties.getEndpoints())) {
      throw new RuntimeException("this is no endpoints in the properties files!");
    }
    RetrofitServiceHolder holder = new RetrofitServiceHolder();
    retrofitProperties.getEndpoints().forEach(endPoint ->
        holder.register(endPoint.getIdetify(), builder.baseUrl(endPoint.getBaseUrl()).build()));
    return holder;
  }

  @Configuration
//  @ConditionalOnProperty(value = "jackson.converter", havingValue = "true")
  public static class JsonConverterClass {

    @Bean
    @ConditionalOnMissingBean(ObjectMapper.class)
    public ObjectMapper objectMapper() {
      ObjectMapper objectMapper = new ObjectMapper();
      objectMapper.setSerializationInclusion(Include.NON_NULL);
      return objectMapper;
    }

    @Bean
    @ConditionalOnClass(JacksonConverterFactory.class)
    public JacksonConverterFactory jacksonConverterFactory(ObjectMapper mapper) {
      mapper.configure(ACCEPT_CASE_INSENSITIVE_PROPERTIES, true);
      return JacksonConverterFactory.create(mapper);
    }
  }

}
