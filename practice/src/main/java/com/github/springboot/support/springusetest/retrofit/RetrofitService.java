package com.github.springboot.support.springusetest.retrofit;

import com.github.springboot.support.springusetest.MyServiceBeanRegister;
import org.springframework.context.annotation.Import;

@Import(MyServiceBeanRegister.class)
public @interface RetrofitService {
}
