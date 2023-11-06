package io.crescent.moonstar.configuration;

import com.sun.net.httpserver.HttpServer;
import io.micrometer.prometheus.PrometheusConfig;
import io.micrometer.prometheus.PrometheusMeterRegistry;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author crescent
 * <p>
 * 参考链接：https://micrometer.io/docs/registry/prometheus
 */
@ConditionalOnProperty(prefix = "spring.prometheus", name = "enable", havingValue = "true")
@Configuration
public class PrometheusConfiguration {


  // http://localhost:8080/prometheus
  @Bean
  public PrometheusMeterRegistry prometheusMeterRegistry() {
    PrometheusMeterRegistry prometheusRegistry = new PrometheusMeterRegistry(PrometheusConfig.DEFAULT);

    try {
      HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
      server.createContext("/prometheus", httpExchange -> {
        String response = prometheusRegistry.scrape();
        httpExchange.sendResponseHeaders(200, response.getBytes().length);
        try (OutputStream os = httpExchange.getResponseBody()) {
          os.write(response.getBytes());
        }
      });

      new Thread(server::start).start();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    return prometheusRegistry;
  }

}
