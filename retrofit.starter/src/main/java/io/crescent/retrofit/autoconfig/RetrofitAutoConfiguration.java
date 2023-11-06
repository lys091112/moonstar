package io.crescent.retrofit.autoconfig;

import static com.fasterxml.jackson.databind.MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.crescent.retrofit.intercepter.HttpLogger;
import java.util.List;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
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
@Slf4j
@Configuration
@ConditionalOnClass({OkHttpClient.class, Retrofit.class})
@EnableConfigurationProperties(RetrofitProperties.class)
public class RetrofitAutoConfiguration {

  final static long DEFAULT_TIME_OUT = 5000;

  private final RetrofitProperties retrofitProperties;

  @Autowired
  public RetrofitAutoConfiguration(RetrofitProperties retrofitProperties) {
    this.retrofitProperties = retrofitProperties;
  }

  @Bean
  @ConditionalOnMissingBean(ConnectionPool.class)
  public ConnectionPool connectionPool() {
    return new ConnectionPool(retrofitProperties.getMaxIdleConnection(), retrofitProperties.getKeepAliveDuration(),
        TimeUnit.MINUTES);
  }

  // TODO{crescent} 执行时间监控拦截器
  @Bean
  public OkHttpClient okHttpClient(ConnectionPool connectionPool) {
    Long connectionTimeout = checkAndGetValue(retrofitProperties.getConnectionTimeout(), "connectionTimeout");
    Long readTimeout = checkAndGetValue(retrofitProperties.getReadTimeout(), "readTimeout");
    Long writeTimeout = checkAndGetValue(retrofitProperties.getWriteTimeout(), "writeTimeout");
    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLogger());
    loggingInterceptor.setLevel(getLogLevel(retrofitProperties.getLogLevel()));

    return new OkHttpClient.Builder().connectTimeout(
        connectionTimeout, TimeUnit.MILLISECONDS)
        .readTimeout(readTimeout, TimeUnit.MILLISECONDS)
        .writeTimeout(writeTimeout, TimeUnit.MILLISECONDS)
        .connectionPool(connectionPool)
        .addInterceptor(loggingInterceptor).build();
  }

  private Level getLogLevel(String logLevel) {
    try {
      return Level.valueOf(logLevel);
    } catch (IllegalArgumentException e) {
      log.error("invalid logLevel in properties files! logLevel:{}", logLevel);
      return Level.NONE;
    }

  }

  private Long checkAndGetValue(Long connectionTimeout, String paramName) {
    if (null == connectionTimeout) {
      log.warn("{} is null in the properties! so use default value {} milliseconds", paramName,
          DEFAULT_TIME_OUT);
      return DEFAULT_TIME_OUT;
    }
    return connectionTimeout;
  }

  @Bean
  @ConditionalOnBean({Converter.Factory.class, OkHttpClient.class})
  public RetrofitServiceHolder retrofit(List<Factory> factories, OkHttpClient client) {
    Retrofit.Builder builder = new Retrofit.Builder();
    factories.forEach(builder::addConverterFactory);
    builder.client(client);

    if (CollectionUtils.isEmpty(retrofitProperties.getEndpoints())) {
      throw new RuntimeException("this is no endpoints in the properties files!");
    }
    RetrofitServiceHolder holder = new RetrofitServiceHolder();
    retrofitProperties.getEndpoints().forEach(endPoint ->
        holder.register(endPoint.getIdentify(), builder.baseUrl(endPoint.getBaseUrl()).build()));
    return holder;
  }

  @Configuration
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
