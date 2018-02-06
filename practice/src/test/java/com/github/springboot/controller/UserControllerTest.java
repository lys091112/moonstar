package com.github.springboot.controller;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.github.springboot.support.exception.TestException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {

    }

    @Test
    public void getUserInfo() throws Exception {
        String url = "/api/v1/getUserInfo";
        Runnable r1 = () -> {
            try {
                mockMvc.perform(
                    MockMvcRequestBuilders.get(url).param("userName", "ooosdf")
                        .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                    .andReturn();
            } catch (Exception e) {
                throw new TestException("test");
            }
        };
        Runnable r2 = () -> {
            try {
                mockMvc.perform(MockMvcRequestBuilders.get(url).param("userName", "ooosdf")
                    .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                    .andReturn();
            } catch (Exception e) {
                throw new TestException("test");
            }
        };
        Runnable r3 = () -> {
            try {
                mockMvc.perform(MockMvcRequestBuilders.get(url).param("userName", "ooosdf")
                    .accept(MediaType.APPLICATION_JSON_UTF8)).andExpect(status().isOk())
                    .andReturn();
            } catch (Exception e) {
                throw new TestException("test");
            }
        };

        ExecutorService executor = Executors.newFixedThreadPool(3);
        executor.submit(r1);
        executor.submit(r2);
        executor.submit(r3);
        executor.awaitTermination(3, TimeUnit.SECONDS);
    }


    @After
    public void clean() {

    }

}