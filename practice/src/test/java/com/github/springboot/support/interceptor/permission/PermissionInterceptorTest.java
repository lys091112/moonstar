package com.github.springboot.support.interceptor.permission;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class PermissionInterceptorTest {

    @Autowired
    private MockMvc mockMvc;

    private static final String GET_USER_INFO_NORMAL = "/api/v1/normal";
    private static final String GET_USER_INFO_PERMISSION_DENY = "/api/v1/permissiondeny";

    @Test
    public void normalRequest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(GET_USER_INFO_NORMAL))
            .andExpect(MockMvcResultMatchers.status().isOk())
            .andExpect(MockMvcResultMatchers.content().string("normal"));
    }

    @Test
    public void permission_deny() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get(GET_USER_INFO_PERMISSION_DENY))
            .andExpect(MockMvcResultMatchers.status().is(405));

    }

}