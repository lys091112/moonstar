package io.crescent.retrofit.service;

import io.crescent.retrofit.BaseMockServer;
import io.crescent.retrofit.endpoint.AlertEndPoint;
import java.io.IOException;
import java.util.Map;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class AlertServiceTest extends BaseMockServer {

    @Autowired
    private AlertEndPoint alertEndPoint;

    @Test
    public void getUserInfo() throws IOException {
        Map<String, Object> infos = alertEndPoint.getAlertInfo().execute().body();
        System.out.println(infos);
    }


    @Override
    public String identify() {
        return "alert";
    }
}
