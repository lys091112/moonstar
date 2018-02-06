package com.xianyue.retrofit.intercepter;

import lombok.extern.slf4j.Slf4j;
import okhttp3.logging.HttpLoggingInterceptor.Logger;

@Slf4j
public class HttpLogger implements Logger {

    @Override
    public void log(String message) {
        log.info("Retrofit Request: {}", message);
    }
}
