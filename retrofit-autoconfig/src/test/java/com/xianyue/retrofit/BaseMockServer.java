package com.xianyue.retrofit;

import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.common.SingleRootFileSource;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.standalone.JsonFileMappingsSource;
import com.xianyue.retrofit.autoconfig.RetrofitProperties;
import com.xianyue.retrofit.autoconfig.RetrofitProperties.Endpoint;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
@Slf4j
public abstract class BaseMockServer {

    @Autowired
    private RetrofitProperties retrofitProperties;

    private volatile WireMockRule wireMockRule;

    @Rule
    public WireMockRule wireServer() {
        if (null == wireMockRule) {
            synchronized (this) {
                if (null == wireMockRule) {
                    WireMockConfiguration config = WireMockConfiguration.options()
                        .bindAddress("127.0.0.1").port(port())
                        .notifier(new ConsoleNotifier(true))
                        .mappingSource(new JsonFileMappingsSource(new SingleRootFileSource(source())));
                    wireMockRule = new WireMockRule(config);
                }
            }
        }
        return wireMockRule;
    }

    private int port() throws IllegalArgumentException {
        Optional<Endpoint> optional = retrofitProperties.getEndpoints().stream()
            .filter(p -> p.getIdentify().equalsIgnoreCase(identify())).findAny();

        if (!optional.isPresent()) {
            throw new IllegalArgumentException("invalid identify! {} " + identify());
        }

        String baseUrl = optional.get().getBaseUrl();
        return Integer.parseInt(baseUrl.substring(baseUrl.lastIndexOf(":") + 1));
    }


    private String source() {
        return "src/test/mockfiles/" + identify();

    }

    public abstract String identify();

}
