package io.crescent.retrofit.service;

import io.crescent.retrofit.BaseMockServer;
import io.crescent.retrofit.endpoint.GitHubEndPoint;
import java.io.IOException;
import java.util.Map;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class GitHubServiceTest extends BaseMockServer {

    @Autowired
    private GitHubEndPoint gitHubEndPoint;

    @Test
    public void getUserInfo() throws IOException {
        Map<String, Object> infos = gitHubEndPoint.getUserInfo().execute().body();
        System.out.println(infos);
    }


    @Override
    public String identify() {
        return "git";
    }
}
