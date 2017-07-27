package com.github.springboot.test.retrofit;

import com.github.springboot.test.MyServiceBeanRegister;
import org.springframework.context.annotation.Import;

@Import(MyServiceBeanRegister.class)
public @interface RetrofitService {
}
